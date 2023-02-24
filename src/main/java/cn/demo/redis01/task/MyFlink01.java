package cn.demo.redis01.task;

import cn.demo.redis01.entity.ClickEntity;
import cn.demo.redis01.map.MyMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

//Flink模拟用户浏览产品
public class MyFlink01 {
    public static void main(String[] args) throws  Exception{
        ClickEntity click1=new ClickEntity(101L,201L);
        ClickEntity click2=new ClickEntity(102L,202L);
        ClickEntity click3=new ClickEntity(103L,203L);
        ClickEntity click4=new ClickEntity(103L,203L);
        ClickEntity click5=new ClickEntity(103L,206L);
        ClickEntity click6=new ClickEntity(101L,207L);
        ClickEntity click7=new ClickEntity(102L,208L);
        ClickEntity click8=new ClickEntity(102L,203L);
        ClickEntity click9=new ClickEntity(102L,201L);
        ClickEntity click10=new ClickEntity(105L,202L);
        ClickEntity click11=new ClickEntity(106L,209L);

        StreamExecutionEnvironment env=StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<ClickEntity> ds_clicks=env.fromElements(click1,click2,click3,click4,click5,click6,click7,click8,click9,click10,click11);
        //对每一次进来的事件分解获取到userId,并对这个userId计一次点击
        //结果需要返回一个结构，这个结果包含userId以及一次点击; 1
        //Tuple2<Long,Integer> : <userId,1>
        //本质上ds2就是一个DataStream
        SingleOutputStreamOperator<Tuple2<Long,Integer>> dataStream2=ds_clicks.map(new MyMapFunction());
        KeyedStream<Tuple2<Long,Integer>, Tuple> keyedStream=dataStream2.keyBy(0);

        System.out.println("11111");
        keyedStream.print();
        //WindowedStream<Tuple2<Long,Integer>,Tuple,TimeWindow> windowedStream=ks.window(EventTimeSessionWindows.with);
        SingleOutputStreamOperator<Tuple2<Long,Integer>> dataStream3= keyedStream.reduce(new ReduceFunction<Tuple2<Long,Integer>>(){
            @Override
            public Tuple2<Long,Integer> reduce (Tuple2<Long,Integer> clickMap1,Tuple2<Long,Integer> clickMap2)throws Exception{
                return Tuple2.of(clickMap1.f0,clickMap1.f1+clickMap2.f1);
            }
        });

        dataStream3.writeAsText("E:\\flink01.txt");
        try {
            env.execute("click statistic...");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //dataStream3.addSink(new PrintSinkFunction<>());
        System.out.println("33333");
        dataStream3.print();

        System.out.println("44444");
        dataStream3.print();

        System.out.println("555555");
        dataStream3.print();
    }
}
