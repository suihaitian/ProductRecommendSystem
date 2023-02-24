package cn.demo.redis01.map;

import cn.demo.redis01.entity.ClickEntity;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.java.tuple.Tuple2;

public class MyMapFunction implements MapFunction<ClickEntity, Tuple2<Long,Integer>> {
    @Override
    public Tuple2<Long,Integer> map(ClickEntity click) throws Exception{
        return new Tuple2<>(click.getUserId(),1);
    }
}
