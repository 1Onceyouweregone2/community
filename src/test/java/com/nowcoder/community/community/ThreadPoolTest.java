package com.nowcoder.community.community;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.test.context.ContextConfiguration;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@ContextConfiguration(classes = CommunityApplication.class)
@Slf4j
public class ThreadPoolTest {

    //JDK普通线程池
    private ExecutorService executorService = Executors.newFixedThreadPool(5);

    //spring普通线程池
    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;

    //spring定时任务线程池
    @Autowired
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    //JDK定时任务的线程池
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

    private void sleep(long m) {
        try {
            Thread.sleep(m);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    //JDK普通线程池
    @Test
    public void testExecutorService() {
        for (int i = 0; i < 10; i++) {
            executorService.submit(() -> {
                log.debug("hello ExecutorService!");
            });
        }
        sleep(10000);
    }

    //JDK定时线程池
    @Test
    public void testScheduledExecutorService() {
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    log.debug("hello ScheduledExecutorService");
                }
            },10000,1000, TimeUnit.MICROSECONDS);
        sleep(10000);
    }

    //spring普通线程池
    @Test
    public void testThreadPoolTaskExecutor() {
        for (int i = 0; i < 10; i++) {
            threadPoolTaskExecutor.submit(() -> {
                log.debug("hello ThreadPoolTaskExecutor");
            });
        }
        sleep(10000);
    }

    //spring定时线程池
    @Test
    public void testThreadPoolTaskScheduler() {
        threadPoolTaskScheduler.scheduleAtFixedRate(() -> {
            log.debug("hello threadPoolTaskScheduler");
        },new Date(System.currentTimeMillis()+10000),1000);
        sleep(30000);
    }

}
