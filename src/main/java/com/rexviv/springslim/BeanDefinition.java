package com.rexviv.springslim;

public class BeanDefinition {
    private Class type;
    private String scope;


    public void setType(Class clazz){
        this.type = clazz;
    }

    public Class getType() {
        return type;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getScope() {
        return scope;
    }
}
