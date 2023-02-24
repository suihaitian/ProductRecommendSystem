package cn.demo.redis01.dao;

import cn.demo.redis01.entity.ProductEntity;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface RecommandDao {
    List<ProductEntity> getRecommandProducts01();
    List<ProductEntity> getRecommandProducts02();
    List<ProductEntity> getRecommandProducts03();
}
