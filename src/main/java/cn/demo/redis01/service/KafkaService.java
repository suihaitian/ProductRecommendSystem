package cn.demo.redis01.service;

public interface KafkaService {
    String createLog(String pUserName,String pProductId,String pActionType);
    void send(String strLog);
}
