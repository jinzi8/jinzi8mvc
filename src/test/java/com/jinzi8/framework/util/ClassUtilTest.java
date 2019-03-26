package com.jinzi8.framework.util;


import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * @author 博哥
 * @create 2019-03-19 8:26
 */
public class ClassUtilTest {

    @Test
    public void getClassLoader() {
    }
    @Test
    public void loadClass() {

    }

    @Test
    public void getClassSet() {
        Set<Class<?>> classSet = ClassUtil.getClassSet("com.jinzi8.framework");
        for (Class<?> aClass : classSet) {
            System.out.println(aClass);
        }
    }
    @Test
    public void demo1() throws IOException {
/*
        URL resources = Thread.currentThread().getContextClassLoader().getResource("com/jinzi8/framework/UserDemo.java");
        System.out.println(resources);
*/
        URL resource = Thread.currentThread().getContextClassLoader().getResource("com/jinzi8/framework/UserDemo.class");
        System.out.println(resource);
        System.out.println(resource.getProtocol());
        System.out.println("-------------------------------------------");
        Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources("com/jinzi8/framework");
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            System.out.println(url.getProtocol());
            System.out.println(url);
        }


    }
}
