package cn.demo.redis01.task;

import cn.demo.redis01.entity.InterestEntity;
import cn.demo.redis01.entity.UserEntity;
import java.util.*;

public class AlgTask {
    public static void main(String[] args) {
        //兴趣分类：color[red,yellow,blue,white]
        //         cat[shoe,clothes,lip-sticks,hat,fake-hair]
        //         city[hengyang,chnagsha,wuhan,huangshi,beijing]
        //         kuanshi[1,2,3,4]
        //         size[37,38,39,40,41,42]
        //         liangzedu:[1,2,3,4,5]
        //1.在演讲浏览的哪个userId的兴趣向量{color:red, cat:shoe, city:hengyang, kuanshi:2,size:39}
        //2.从Hbase建好的模拟用户组拿出3个用户的兴趣列表
        /*
        create user_interest {NAME=>"interest"}
        user_201:(写3个)
        put uesr_interest '201' 'interest:color','red'
        put uesr_interest '201' 'interest:cat','fake-hair'
        put uesr_interest '201' 'interest:kuanshi','3'
        put uesr_interest '201' 'interest:liangzedu','4'
        put uesr_interest '201' 'interest:city','beijing'
        */
        InterestEntity interestEntity0=new InterestEntity("shoe","red","hengyang",2,39,-1,0,0);
        UserEntity user0=new UserEntity(999,interestEntity0);

        InterestEntity interestEntity201=new InterestEntity("fair-hair","red",null,2,39,-1,0,0);
        UserEntity user201=new UserEntity(201,interestEntity201);

        InterestEntity interestEntity202=new InterestEntity("shoe","red","beijing",1,43,-1,0,0);
        UserEntity user202=new UserEntity(202,interestEntity202);

        InterestEntity interestEntity203=new InterestEntity("fair-hair","red","changsha",3,40,-1,0,0);
        UserEntity user203=new UserEntity(203,interestEntity203);

        List<UserEntity> userEntities=new ArrayList(){{
            add(user201);
            add(user202);
            add(user203);
        }};

        Map<Integer,Double> scoreMap=new HashMap<>();
        for(UserEntity user:userEntities){
            InterestEntity interestEntity=user.getInterestEntity();
            double score=interestEntity.compare(interestEntity0);
            scoreMap.put(user.getId(),score);
        }
        System.out.println(scoreMap);
    }
}
