package com.rexviv.winter.beans.factory.support;

import com.rexviv.winter.beans.factory.config.BeanDefinition;

public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);

}
