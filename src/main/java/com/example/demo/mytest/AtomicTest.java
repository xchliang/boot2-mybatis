package com.example.demo.mytest;

import org.springframework.http.converter.json.GsonBuilderUtils;
import org.w3c.dom.ls.LSOutput;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService e = Executors.newFixedThreadPool(10);
        AtomicInteger a = new AtomicInteger(0);
        Runnable r = new Runnable() {
            @Override
            public void run() {
                int n =0;
                for (int i = 0; i < 1000000; i++) {
                    n = a.incrementAndGet();
                }
                System.out.println("结束->"+n);
            }
        };
        for (int i = 0; i < 10; i++) {
            e.execute(r);
        }
        e.shutdown();
        e.awaitTermination(1, TimeUnit.DAYS);
        System.out.println("终值：" + a.get());
    }

}
