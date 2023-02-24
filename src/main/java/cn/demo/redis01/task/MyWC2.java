package cn.demo.redis01.task;

import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

//Flink与NetCat实时监测学生成绩信息
public class MyWC2 {
    public static void main(String[] args) {
        System.out.println("11111");

        StreamExecutionEnvironment env=StreamExecutionEnvironment.getExecutionEnvironment();
//        env.setStreamTimeCharacteristic(TimeCharacteristic.ProcessingTime);
        //192.168.183.1
        DataStream<String> dss0=env.socketTextStream("192.168.183.1",9000);
        //netcat : nc -l -p 9000
        SingleOutputStreamOperator<Tuple2<String,Double>> ds_split=dss0.map(new MapFunction<String,Tuple2<String, Double>>() {
            @Override
            public Tuple2<String, Double> map(String s) throws Exception{
                String[] arr1=s.split(";");
                return new Tuple2<>(arr1[0],Double.parseDouble(arr1[1]));
            }
        });

        KeyedStream<Tuple2<String, Double>, Tuple> ds_keyed=ds_split.keyBy(0);
        WindowedStream<Tuple2<String, Double>, Tuple, TimeWindow> ws_tw=ds_keyed.timeWindow(Time.seconds(10));
        SingleOutputStreamOperator<Tuple2<String,Double>> ds_maxby=ws_tw.maxBy(0);
        ds_maxby.print();

        System.out.println("22222");
        try {
            env.execute("Student grade statistic...");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("33333");
    }
}
