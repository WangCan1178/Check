package com.example.demo.Controller;

import com.example.demo.Service.MessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.beans.BeansException;

import java.time.LocalDateTime;


@Component
class ApplicationContextUtil  implements ApplicationContextAware {
    @Autowired
    private static ApplicationContext context;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return context;
    }

    public static Object getBean(String name) {
        return getApplicationContext().getBean(name);
    }
}

@Controller
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class SaticScheduleTask {
    private MessService messService = (MessService)ApplicationContextUtil.getBean("messService");
//    private MessService messService;
    @Scheduled(fixedRate=86400000)
    private void configureTasks() {
        messService.messenday();
//        System.err.println("执行静态定时任务时间: " + LocalDateTime.now());
    }
}


