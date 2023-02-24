package cn.demo.redis01.task;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//计时器
public class SchedulerTask {
    public static void main(String[] args) {
        final int[] li={0};
        ExecutorService executorService= Executors.newFixedThreadPool(10);
        Timer timer1=new Timer();
        timer1.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                executorService.execute(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println(li[0]++);
                        /*List<String> ids=new ArrayList<>(1000);
                        for(String id:ids){
                            List<ProductEntity> entityList=new ArrayList<>(100000);
                            executorService.execute(new XgdServiceRunnable(id,ids));
                        }*/
                    }
                });
            }
        },0,10*1000);//每隔十秒增加1
    }

    /*private static class XgdServiceRunnable implements Runnable {
        @Override
        public void run() {

        }
    }*/
}
