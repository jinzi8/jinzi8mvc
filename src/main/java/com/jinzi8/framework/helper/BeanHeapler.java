package com.jinzi8.framework.helper;

import com.jinzi8.framework.helper.ClassHelper;
import com.jinzi8.framework.util.ReflectionUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Bean助手类
 * @author 博哥
 * @create 2019-03-19 16:39
 */
public final class BeanHeapler {
    /**
     * 定义Bean映射（用于存放Bean类与Bean实例的映射关系）,所谓的容器
     */
    public static final Map<Class<?>,Object> BEAN_MAP = new HashMap<Class<?>,Object>();
    static{
        //获取bean对应的Class的集合
        Set<Class<?>> beanClassSet = ClassHelper.getBeanClassSet();
        //遍历所有的Class类型，进行实例化，并保存到MAP集合
        for (Class<?> beanClass : beanClassSet) {
            Object object = ReflectionUtil.newInstance(beanClass);
            BEAN_MAP.put(beanClass, object);
        }
    }
    /**
     * 设置Bean实例
     */
    public static void setBean(Class<?> cls,Object obj){
        BEAN_MAP.put(cls, obj);
    }
    /**
     * 获取Bean映射
     */
    public static Map<Class<?>,Object> getBeanMap(){
        return BEAN_MAP;
    }
    /**
     * 获取Bean实例
     */
    public static <T> T getBean(Class<T> cls){
        if (!BEAN_MAP.containsKey(cls)) {
            throw new RuntimeException("容器中并不存在");
        }
        return (T) BEAN_MAP.get(cls);
    }
}
