package com.rexviv.test.service;

import com.rexviv.test.domain.pojo.User;
import com.rexviv.winter.Autowired;
import com.rexviv.winter.Component;

@Component
public class UserService {
    @Autowired
    private User user;

    public void printUser(){
        System.out.println("UserService: "+this);
        System.out.println("user: "+user);
    }
}
