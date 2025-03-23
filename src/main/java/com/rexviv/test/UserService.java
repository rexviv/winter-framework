package com.rexviv.test;

import com.rexviv.springslim.Autowired;
import com.rexviv.springslim.Component;

@Component
public class UserService {
    @Autowired
    private User user;

    public void printUser(){
        System.out.println("user: " + user);
    }
}
