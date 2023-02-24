package cn.demo.redis01.service;

import cn.demo.redis01.entity.ProductEntity;
import java.util.List;

//简化Controller层
public interface ProductService {
    public List<ProductEntity> getProductData();
}
