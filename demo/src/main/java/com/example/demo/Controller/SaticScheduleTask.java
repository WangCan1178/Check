package com.example.demo.Controller;

import com.example.demo.Service.MessService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDateTime;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {
    private MessService messService;
    @Scheduled(fixedRate=86400000)
    private void configureTasks() {
        messService.messenday();
        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
    }
}