package cn.demo.redis01.service.impl;

import cn.demo.redis01.client.RedisClient;
import cn.demo.redis01.dao.ProductDao;
import cn.demo.redis01.entity.ProductEntity;
import cn.demo.redis01.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service("ProductServiceImpl")
public class ProductServiceImpl implements ProductService {
    //传统方式用new实现，现在被Autowired托管
    @Autowired
    RedisClient redisClient;

    @Autowired
    ProductDao productDao;

    @Override
    public List<ProductEntity> getProductData() {
        List<ProductEntity> entityList=productDao.getProducts();
        return entityList;
    }
}
