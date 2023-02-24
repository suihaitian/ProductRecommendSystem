package cn.demo.redis01.service.impl;
import cn.demo.redis01.service.KafkaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFutureCallback;
import java.util.Date;

@Service("KafkaServiceImpl")
public class KafkaServiceImpl implements KafkaService {
    @Autowired
    KafkaTemplate<String,String> kafkaTemplate;

    @Override
    public String createLog(String pUserName,String pProductId,String pActionType) {
        long tsms=new Date().getTime();
        //return String.format("%s;%s;%d",pUserName,pProductId,pActionType);
        return String.format("%s;%s",pUserName,pProductId,pActionType);
    }

    @Override
    public void send(String strLog) {
        kafkaTemplate.send("mymall-recommend","uaction",strLog).addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable throwable) {
                System.out.println(throwable.getMessage());
            }

            @Override
            public void onSuccess(SendResult<String, String> stringStringSendResult) {
                System.out.println(stringStringSendResult.toString());
            }
        });
    }
}
