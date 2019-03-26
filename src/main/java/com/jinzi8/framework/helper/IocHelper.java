package com.jinzi8.framework.helper;

import com.jinzi8.framework.annotation.Inject;
import com.jinzi8.framework.helper.BeanHeapler;
import com.jinzi8.framework.util.ReflectionUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 依赖注入助手类，遍历beanMap集合，获取依赖信息，并进行注入
 *
 * @author 博哥
 * @create 2019-03-19 17:30
 */
public final class IocHelper {
    static {
        //获取所有的Bean类与Bean实例之间的映射关系（简称Bean Map）
        Map<Class<?>, Object> beanMap = BeanHeapler.getBeanMap();
        if (CollectionUtils.isNotEmpty(beanMap.keySet())) {
            //遍历BeanMap集合，进行依赖注入操作
            for (Map.Entry<Class<?>, Object> beanEntry : beanMap.entrySet()) {
                //获取bean类与bean实例对象
                Class<?> key = beanEntry.getKey();
                Object value = beanEntry.getValue();
                //获取Bean类定义的所有成员变量，也就是Filed类型对象
                Field[] declaredFields = key.getDeclaredFields();
                if (ArrayUtils.isNotEmpty(declaredFields)) {
                    //遍历bean Field
                    for (Field field : declaredFields) {
                        //判断当前字段是否带有Inject注解，如果是，则说明当前类依赖该字段类型对象
                        if (field.isAnnotationPresent(Inject.class)) {
                            //获取字段对应的Class类型
                            Class<?> fieldType = field.getType();
                            //得到该类型实例对象
                            Object fieldInstance = beanMap.get(fieldType);
                            if (fieldInstance != null) {
                                //通过反射进行依赖注入
                                ReflectionUtil.setField(value, field, fieldInstance);
                            }
                        }
                    }
                }
            }
        }
    }
}



