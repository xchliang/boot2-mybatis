package com.example.demo.component;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * 获取bean
 */
@Component
public class ApplicationUtil implements ApplicationContextAware {
    @Autowired
    private static ApplicationContext applicationContext;

    //@Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationUtil.applicationContext =applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static Object getBean(String beanName){
        return applicationContext.getBean(beanName);
    }

    public static <T> T getBean(Class<T> aClass){
        return applicationContext.getBean(aClass);
    }

    public static <T> T getBean(String beanName,Class<T> aClass){
        return applicationContext.getBean(beanName,aClass);
    }

}
