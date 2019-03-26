package com.jinzi8.framework.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类  提供反射相关的常用操作
 * @author 博哥
 * @create 2019-03-19 16:08
 */
public final class ReflectionUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(ReflectionUtil.class);

    /**
     * 创建实例
     */
    public static Object newInstance(Class<?> cls){
        Object instance;
        try {
            instance = cls.newInstance();
        } catch (Exception e) {
            LOGGER.error("创建实例对象失败！",e);
            throw new RuntimeException(e);
        }
        return instance;
    }

    /**
     * 调用方法
     */
    public static Object invokeMethod(Object obj , Method method, Object... params) {
        Object result = null;
        try {
            method.setAccessible(true);
            method.invoke(obj, params);
        } catch (Exception e) {
            LOGGER.error("执行方法失败！",e);
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * 设置成员变量的值
     */
    public static void setField(Object obj, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(obj,value);
        } catch (Exception e) {
            LOGGER.error("为字段赋值失败！",e);
            throw new RuntimeException(e);
        }
    }

}
