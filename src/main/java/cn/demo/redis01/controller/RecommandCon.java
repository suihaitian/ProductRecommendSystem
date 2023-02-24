package cn.demo.redis01.controller;

import cn.demo.redis01.entity.ProductEntity;
import cn.demo.redis01.service.KafkaService;
import cn.demo.redis01.service.RecommandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class RecommandCon {
    @Autowired
    RecommandService recommandService;
    @Autowired
    KafkaService kafkaService;
    /**
     * 返回推荐页面
     * @param userId
     * @return
     * @throws IOException
     */
    @GetMapping("/Recommand")
    public String recommandByUserId(@RequestParam(value = "userId",defaultValue = "1") String userId,
                                    Model model) throws IOException {
        // 拿到不同推荐方案的结果
        List<ProductEntity> recommandList01 = recommandService.getRecommandData01();
        List<ProductEntity> recommandList02 = recommandService.getRecommandData02();
        List<ProductEntity> recommandList03 = recommandService.getRecommandData03();
        // 将结果返回给前端
        model.addAttribute("userId", userId);
        model.addAttribute("recommandList01",recommandList01);
        model.addAttribute("recommandList02", recommandList02);
        model.addAttribute("recommandList03", recommandList03);
        return "user";
    }

    @PostMapping("/Log")
    @ResponseBody
    public Map<String,Object> logToKafka(@RequestParam("id") String userId,
                                         @RequestParam("prod") String productId,
                                         @RequestParam("action") String action){

        String log = kafkaService.createLog(userId, productId, action);
        kafkaService.send(log);
        Map<String,Object> ret=new HashMap<>();
        ret.put("code",200);
        ret.put("msg","success");
        return ret;
    }
}
