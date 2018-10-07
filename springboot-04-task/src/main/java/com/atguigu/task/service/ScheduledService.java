package com.atguigu.task.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledService {

    /**
     * second/minute/hour/day of month/month/day of week
     */
    @Scheduled(cron = "0 * * * * MON-SAT")
    public void hello() {
        System.out.println("hello...");
    }

}
