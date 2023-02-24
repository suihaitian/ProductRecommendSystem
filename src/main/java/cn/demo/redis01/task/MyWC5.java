package cn.demo.redis01.task;

import cn.demo.redis01.entity.WordEntity;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

//Flink与NetCat-->增量聚合函数-->实时监测学生成绩信息
//输出形式为：(输入单词名，次数)   （<Tuple2<String,Integer>>） 1> (ou,5)
//增加全量聚合
//输出形式为：yu--1
public class MyWC5 {
    public static void main(String[] args) {
        System.out.println("11111");

        StreamExecutionEnvironment env=StreamExecutionEnvironment.getExecutionEnvironment();
        //192.168.183.1
        //netcat : nc -l -p 9000
        DataStream<String> dss0=env.socketTextStream("192.168.183.1",9000);
        SingleOutputStreamOperator<WordEntity> dss1=dss0.map(new MapFunction<String, WordEntity>() {
            @Override
            public WordEntity map(String s) throws Exception {
                return new WordEntity(s);
            }
        });
        KeyedStream <WordEntity,Tuple> ds_keyed = dss1.keyBy("word");
        WindowedStream<WordEntity,Tuple,TimeWindow> ws_tw=ds_keyed.timeWindow(Time.seconds(30));

        //增量聚合函数
        SingleOutputStreamOperator<Tuple2<String,Integer>> ds_agg=ws_tw.aggregate(new AggregateFunction<WordEntity, Integer, Integer>() {
            @Override
            public Integer createAccumulator() {
                return 0;
            }

            @Override
            public Integer add(WordEntity w, Integer acc) {
                return acc + 1;
            }

            @Override
            public Integer getResult(Integer acc) {
                return acc;
            }

            @Override
            public Integer merge(Integer acc1, Integer acc2) {
                return acc1 + acc2;
            }
        }, new WindowFunction<Integer, Tuple2<String, Integer>, Tuple, TimeWindow>() {
            @Override
            public void apply(Tuple key, TimeWindow timeWindow, Iterable<Integer> iterable, Collector<Tuple2<String, Integer>> collector) throws Exception {
                Integer i=iterable.iterator().next();
                collector.collect(new Tuple2<>(key.getField(0),i));
            }
        });

        //全量聚合
        ds_agg.process(new ProcessFunction<Tuple2<String, Integer>, Tuple2<String,Integer>>() {
            @Override
            public void processElement(Tuple2<String, Integer> word, Context context, Collector<Tuple2<String, Integer>> collector) throws Exception {
                System.out.println(String.format("%s--%d",word.f0,word.f1));
                collector.collect(word);
            }
        });

        ds_agg.print();
        ds_agg.writeAsText("E:\\mywc5.txt");

        System.out.println("22222");
        try {
            env.execute("Student grade statistic...");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("33333");
    }
}
