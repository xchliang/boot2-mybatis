package com.example.demo;

import com.example.demo.entity.Drugs;
import com.example.demo.service.DrugsService;
import com.example.demo.component.ApplicationUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import java.util.concurrent.atomic.AtomicInteger;

@MapperScan("com.example.demo.mapper")
@SpringBootApplication
public class BootmybatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootmybatisApplication.class, args);
        //DrugsService drugsService = (DrugsService) ApplicationUtil.getBean("drugsService");
        DrugsService drugsService = ApplicationUtil.getBean(DrugsService.class);
        Drugs drugs = drugsService.getDrugs(1L);
        System.out.println("aaa");
        //String filePath = "F:\\work\\文档\\协和用药信息知识库\\3.txt";
        //drugsService.resolve(filePath);
        AtomicInteger ai= new AtomicInteger(0);
        int a = ai.getAndIncrement();
    }
}
