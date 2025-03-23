package com.rexviv.winter;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

public class ApplicationContext {
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();

    public ApplicationContext(Class<?> configClass){
        if(configClass.isAnnotationPresent(ComponentScan.class)){
            ComponentScan componentScan = configClass.getAnnotation(ComponentScan.class);
            String path = componentScan.value();
            if(path.equals("")){
                path = configClass.getPackageName();
            }
            path = path.replace(".", "/");
            ClassLoader classLoader = ApplicationContext.class.getClassLoader();
            URL url = classLoader.getResource(path);
            File files = new File(url.getFile());
            doScan(files);
        } else {
            throw new RuntimeException("Missing @ComponentScan annotation");
        }

        for(String beanName : beanDefinitionMap.keySet()){
            Object bean = doCreateBean(beanName, beanDefinitionMap.get(beanName));
            for(Field f: bean.getClass().getDeclaredFields()){
                f.setAccessible(true);
                try {
                    f.set(bean, getBean(f.getName()));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
            }
            singletonObjects.put(beanName, bean);
        }
    }

    private void doScan(File files){
        ClassLoader classLoader = ApplicationContext.class.getClassLoader();
        if(files.isDirectory()) {
            for (File file : files.listFiles()) {
                if(file.isDirectory()){
                    doScan(file);
                }else{
                    String fileName = file.getAbsolutePath();
                    if (file.toString().endsWith(".class")) {
                        BeanDefinition beanDefinition = new BeanDefinition();

                        String className = fileName.substring(fileName.indexOf("com"), fileName.indexOf(".class"));
                        className = className.replace("\\", ".");

                        try {
                            Class<?> clazz = classLoader.loadClass(className);
                            String beanName = Introspector.decapitalize(className.substring(className.lastIndexOf(".") + 1));

                            if (clazz.isAnnotationPresent(Component.class)) {
                                beanDefinition.setType(clazz);
                                if (clazz.isAnnotationPresent(Scope.class)) {
                                    Scope scope = (Scope) clazz.getAnnotation(Scope.class);
                                    beanDefinition.setScope(scope.value());
                                } else {
                                    beanDefinition.setScope("singleton");
                                }
                                beanDefinitionMap.put(beanName, beanDefinition);
                            }
                        } catch (ClassNotFoundException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }


    public Object getBean(String beanName){
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        Class clazz = beanDefinition.getType();
        String scope = beanDefinition.getScope();
        if(scope.equals("singleton")){
            Object bean = singletonObjects.get(beanName);
            if(bean == null){
                bean = doCreateBean(beanName, beanDefinition);
                singletonObjects.put(beanName, bean);
            }
            return bean;
        }else {
            return doCreateBean(beanName, beanDefinition);
        }
    }

    private Object doCreateBean(String beanName, BeanDefinition beanDefinition){
        Class<?> clazz = beanDefinition.getType();
        Object bean = null;
        try {
            bean = clazz.getConstructor().newInstance();
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
        return bean;
    }
}
