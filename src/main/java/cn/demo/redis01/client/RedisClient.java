package cn.demo.redis01.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * redis操作工具类
 * (基于RedisTemplate)
 */
@Component
public class RedisClient {
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //获取redis数据
    public String getData(String key){
        String name=redisTemplate.opsForValue().get(key);
        return name;
    }

    public void setData(String key,String val){
        redisTemplate.opsForValue().set(key, val);
    }

   //获取热门商品列表
   public List<String> getTopList(int topRange){
       List<String> res = new ArrayList<>();



       for (int i = 0; i < topRange; i++) {
           res.add(getData(String.valueOf(i)));
       }
       return res;
   }

   //获取1小时内接入量数据
    public String getMeter(){
        return getData("meter");
    }
}
