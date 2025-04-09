package com.rexviv.winter.beans.factory;

import com.rexviv.winter.beans.BeansException;

public interface BeanFactory {

    Object getBean(String name) throws BeansException;

    Object getBean(String name, Object... args) throws BeansException;

}

