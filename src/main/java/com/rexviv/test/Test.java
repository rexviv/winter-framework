package com.rexviv.test;

import com.rexviv.test.config.AppConfig;
import com.rexviv.test.service.UserService;
import com.rexviv.winter.ApplicationContext;
import com.rexviv.winter.beans.factory.config.BeanDefinition;
import com.rexviv.winter.beans.factory.support.DefaultListableBeanFactory;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 2.注册 bean
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class);
        beanFactory.registerBeanDefinition("userService", beanDefinition);
        // 3.第一次获取 bean
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.printUser();
        // 4.第二次获取 bean from Singleton
        UserService userService_singleton = (UserService) beanFactory.getBean("userService");
        userService_singleton.printUser();
    }

}
