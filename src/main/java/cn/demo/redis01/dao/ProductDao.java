package cn.demo.redis01.dao;

import cn.demo.redis01.entity.ProductEntity;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;

@Mapper
public interface ProductDao {
    List<ProductEntity> getProducts();
}
