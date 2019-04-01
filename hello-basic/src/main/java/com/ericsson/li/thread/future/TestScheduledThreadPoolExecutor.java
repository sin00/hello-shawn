package com.ericsson.li.thread.future;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestScheduledThreadPoolExecutor {
    public static void main(String[] args) throws InterruptedException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        ScheduledThreadPoolExecutor pool = new ScheduledThreadPoolExecutor(10);
        for (int i = 0; i < 6; i ++){
            int temp = i + 1;
            pool.schedule(() -> {
                System.out.println(Thread.currentThread().getName() + "->第"+temp+"个炸弹爆炸时间:" + simpleDateFormat.format(new Date()));
            }, temp * 5, TimeUnit.SECONDS);
        }
        pool.shutdown();
        System.out.println("end main时间:" + simpleDateFormat.format(new Date()));
    }
}
