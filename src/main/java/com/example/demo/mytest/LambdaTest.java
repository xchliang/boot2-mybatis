package com.example.demo.mytest;

import java.util.ArrayList;
import java.util.List;

public class LambdaTest {

    public static void main(String[] args) {
        testList();
        testThread();
    }
    public static void testList(){
        List<Integer> list = new ArrayList();
        list.add(1);
        list.add(3);
        list.add(2);
        System.out.println("++++++++++++++++++++++++++++++++");
        list.forEach(num -> System.out.println(num));
        System.out.println("++++++++++++++++++++++++++++++++");
        list.forEach((num) -> {
            num += 1;
            System.out.println(num);
        });
        System.out.println("++++++++++++++++++++++++++++++++");
        list.forEach(System.out::println);
    }
    public static void testThread(){
        new Thread(()->{
            System.out.println("a");
        }).start();
    }
}
