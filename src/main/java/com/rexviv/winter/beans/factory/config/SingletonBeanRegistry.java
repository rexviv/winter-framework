package com.rexviv.winter.beans.factory.config;

public interface SingletonBeanRegistry {
    Object getSingleton(String beanName);
}
