package com.rexviv.test;

import com.rexviv.test.config.AppConfig;
import com.rexviv.test.service.UserService;
import com.rexviv.winter.ApplicationContext;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        ApplicationContext context = new ApplicationContext(AppConfig.class);

        UserService userService = (UserService) context.getBean("userService");
        System.out.println(userService);
        userService.printUser();
    }

}
