package cn.demo.redis01.service;

import cn.demo.redis01.entity.ProductEntity;
import java.util.List;

public interface RecommandService {
    public List<ProductEntity> getRecommandData01();
    public List<ProductEntity> getRecommandData02();
    public List<ProductEntity> getRecommandData03();
}
