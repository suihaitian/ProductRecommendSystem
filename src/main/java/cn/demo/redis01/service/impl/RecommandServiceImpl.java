package cn.demo.redis01.service.impl;

import cn.demo.redis01.client.RedisClient;
import cn.demo.redis01.dao.RecommandDao;
import cn.demo.redis01.entity.ProductEntity;
import cn.demo.redis01.service.RecommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("RecommandServiceImpl")
public class RecommandServiceImpl implements RecommandService {
    @Autowired
    RedisClient redisClient;

    @Autowired
    RecommandDao recommandDao;

    @Override
    public List<ProductEntity> getRecommandData01() {
        List<ProductEntity> entityList= recommandDao.getRecommandProducts01();
        return entityList;
    }

    @Override
    public List<ProductEntity> getRecommandData02() {
        List<ProductEntity> entityList= recommandDao.getRecommandProducts02();
        return entityList;
    }

    @Override
    public List<ProductEntity> getRecommandData03() {
        List<ProductEntity> entityList= recommandDao.getRecommandProducts03();
        return entityList;
    }
}
