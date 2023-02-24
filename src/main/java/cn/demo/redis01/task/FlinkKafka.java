package cn.demo.redis01.task;

import cn.demo.redis01.util.Property;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.*;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer;

import org.apache.flink.streaming.connectors.redis.RedisSink;
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommand;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisCommandDescription;
import org.apache.flink.streaming.connectors.redis.common.mapper.RedisMapper;


import java.util.Properties;

public class FlinkKafka {
    public static void main(String[] args) throws Exception{
        StreamExecutionEnvironment env=StreamExecutionEnvironment.getExecutionEnvironment();
        Properties properties=Property.getKafkaProperties("user-visit");
        DataStream<String> dss0=env.addSource(new FlinkKafkaConsumer<String>("mymall-recommend",new SimpleStringSchema(),properties));

        SingleOutputStreamOperator<Tuple2<String,Integer>> ds_split=dss0.map(new MapFunction<String,Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String,Integer> map(String s) throws Exception{
                String[] arr1=s.split(";");
                String productId=arr1[1];
                int actionType=Integer.parseInt(arr1[2]);
                return new Tuple2<>(productId,actionType==1?1:2);
            }
        });
        KeyedStream<Tuple2<String,Integer>, Tuple> ds_keyed=ds_split.keyBy(0);
        WindowedStream<Tuple2<String, Integer>, Tuple, TimeWindow> ws_tw=ds_keyed.timeWindow(Time.seconds(120));
        SingleOutputStreamOperator<Tuple2<String,Integer>> ds_sum=ws_tw.sum(1);

        FlinkJedisPoolConfig redis_config=new FlinkJedisPoolConfig.Builder().setHost("localhost").build();
        ds_sum.addSink(new RedisSink<>(redis_config, new RedisMapper<Tuple2<String, Integer>>() {
            @Override
            public RedisCommandDescription getCommandDescription() {
                return new RedisCommandDescription(RedisCommand.SET,null);
            }

            @Override
            public String getKeyFromData(Tuple2<String, Integer> stringIntegerTuple2) {
                return stringIntegerTuple2.f0;
            }

            @Override
            public String getValueFromData(Tuple2<String, Integer> stringIntegerTuple2) {
                return String.valueOf(stringIntegerTuple2.f1);
            }
        }));
        ds_sum.print();
        ds_sum.writeAsText("E:\\flinkKafka.txt");

        try {
            env.execute("hot products statistic...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
