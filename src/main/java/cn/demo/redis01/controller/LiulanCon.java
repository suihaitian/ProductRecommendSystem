package cn.demo.redis01.controller;

import cn.demo.redis01.client.RedisClient;
import cn.demo.redis01.entity.ProductEntity;
import cn.demo.redis01.service.KafkaService;
import cn.demo.redis01.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class LiulanCon {
    @Autowired
    RedisClient redisClient;

    //Service层
    @Autowired
    ProductService productService;

    @Autowired
    KafkaService kafkaService;

    @GetMapping("/Liulan")
    public String LiulanAction(Model model, @RequestParam(value = "user",defaultValue = "李想")String pname){
        //用户通过输入Liulan这一路径，他想要看产品的productName、productImg、productCount
        //这部分代码写到了Service层中
        /*ProductEntity entity1=new ProductEntity(1,"商品01","https://g-search3.alicdn.com/img/bao/uploaded/i4/i1/1862340628/O1CN018Xo2gi1GVfTvJ9nEU_!!1862340628-0-picasso.jpg_460x460Q90.jpg_.webp",10);
        String count1=redisClient.getData("商品01");
        entity1.setProductCount(count1==null?0:Integer.parseInt(count1));
        ProductEntity entity2=new ProductEntity(2,"商品02","https://g-search3.alicdn.com/img/bao/uploaded/i4/i3/2774997607/O1CN013GvK4A2643cIos7cy_!!2774997607.jpg_460x460Q90.jpg_.webp",12);
        String count2=redisClient.getData("商品02");
        entity2.setProductCount(count2==null?0:Integer.parseInt(count2));
        ProductEntity entity3=new ProductEntity(3,"商品03","https://g-search2.alicdn.com/img/bao/uploaded/i4/i1/1032435764/O1CN01zndfLM1sRxgUqQXXk_!!0-item_pic.jpg_460x460Q90.jpg_.webp",15);
        String count3=redisClient.getData("商品03");
        entity3.setProductCount(count3==null?0:Integer.parseInt(count3));
        List<ProductEntity> entityList=new ArrayList<>();
        entityList.add(entity1);
        entityList.add(entity2);
        entityList.add(entity3);*/

        List<ProductEntity> entityList=productService.getProductData();

        model.addAttribute("products",entityList);
        model.addAttribute("userName",pname);

        //return到spring框架的某一个函数中
        return "Liulan";
    }

    @PostMapping("/LiulanAction")
    @ResponseBody
    public Map<String,Object> postLiulanAction(@RequestParam(value = "userName",defaultValue = "王宇")String pUserName,@RequestParam(value = "productId",defaultValue = "0")String pProductId,@RequestParam(value = "action",defaultValue = "0")String pActionType){
        /*int pcount=0;
        String pcount0=redisClient.getData(pname);
        if(pcount0!=null){
            pcount=Integer.parseInt(pcount0);
        }
        pcount++;
        redisClient.setData(pname,String.valueOf(pcount));*/

        String strLog= kafkaService.createLog(pUserName,pProductId,pActionType);
        kafkaService.send(strLog);
        Map<String,Object> ret=new HashMap<>();
        ret.put("code",200);
        ret.put("msg","success");
        return ret;
    }
}
