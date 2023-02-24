package cn.demo.redis01.task;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.tuple.Tuple;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.util.Collector;

//Flink实现单词统计（WordCount）
public class WordCountFlink {
    public static void main(String[] args) {
        String[] words=new String[]{
                "Hello , I want to make friends with you ~",
                "Pardon ! What are you saying ?",
                "hello,i will go to school to make shool you."
        };

        StreamExecutionEnvironment env=StreamExecutionEnvironment.getExecutionEnvironment();
        DataStreamSource<String> ds_words=env.fromElements(words);

        SingleOutputStreamOperator<Tuple2<String,Integer>> ds_splitied= ds_words.flatMap(new FlatMapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public void flatMap(String sentence, Collector<Tuple2<String, Integer>> collector) throws Exception {
                // 正则表达式：\\d,\\s
                //正则表达式：空格
                String[] arr1=sentence.split(" ");
                for(String w: arr1){
                    Tuple2<String,Integer> tt=new Tuple2<>(w.toLowerCase(),1);
                    collector.collect(tt);
                }
            }
        });

        SingleOutputStreamOperator<Tuple2<String,Integer>> ds_filtered=ds_splitied.filter(new FilterFunction<Tuple2<String, Integer>>() {
            @Override
            public boolean filter(Tuple2<String, Integer> maybeWord) throws Exception {
                int code=(int)maybeWord.f0.charAt(0);
                boolean isChar=(code>=65&&code<=90)||(code>=97&&code<=122);
                boolean isWord=maybeWord.f0.length()>1||(isChar?true:false);
                return isWord;
            }
        });

        KeyedStream <Tuple2<String,Integer>, Tuple> ks_keyBy=ds_filtered.keyBy(0);
        SingleOutputStreamOperator ds_reduced=ks_keyBy.reduce(new ReduceFunction<Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> reduce(Tuple2<String, Integer> toward1, Tuple2<String, Integer> toward2) throws Exception {
                return new Tuple2<>(toward1.f0,toward1.f1+toward2.f1);
            }
        });

        ds_reduced.writeAsText("E:\\wordcount_flink.txt");
        try {
            env.execute("WordCount...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
