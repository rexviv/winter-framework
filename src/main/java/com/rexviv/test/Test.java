package com.rexviv.test;

import com.rexviv.springslim.ApplicationContext;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        ApplicationContext context = new ApplicationContext(AppConfig.class);

        UserService userService = (UserService) context.getBean("userService");
        System.out.println(userService);
        userService.printUser();
    }

}
