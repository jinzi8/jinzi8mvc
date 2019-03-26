package com.jinzi8.framework.helper;

import com.jinzi8.framework.annotation.Aspect;
import com.jinzi8.framework.annotation.Service;
import com.jinzi8.framework.annotation.Transaction;
import com.jinzi8.framework.proxy.AspectProxy;
import com.jinzi8.framework.proxy.Proxy;
import com.jinzi8.framework.proxy.ProxyManager;
import com.jinzi8.framework.proxy.TransactionProxy;
import com.jinzi8.framework.util.CodecUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author 博哥
 * @create 2019-03-26 10:14
 */
public class AopHelper {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodecUtil.class);

    /**
     * 初始化AOP框架
     */
    static {
        try {
            Map<Class<?>, Set<Class<?>>> proxyMap = createProxyMap();
            Map<Class<?>, List<Proxy>> targetMap = createTargetMap(proxyMap);
            for (Map.Entry<Class<?>, List<Proxy>> targetEntry : targetMap.entrySet()) {
                Class<?> targetClass = targetEntry.getKey();
                List<Proxy> proxyList = targetEntry.getValue();
                Object proxy = ProxyManager.createProxy(targetClass, proxyList);
                BeanHeapler.setBean(targetClass, proxy);
            }
        } catch (Exception e) {
            LOGGER.error("aop初始化失败",e);
        }
    }
    /**
     * 获取Aspect切面注解标识的所有类
     *
     * @param aspect
     * @return
     * @throws Exception
     */
    private static Set<Class<?>> createTargetClassSet(Aspect aspect) throws Exception {
        HashSet<Class<?>> targetClassSet = new HashSet<Class<?>>();
        Class<? extends Annotation> annotation = aspect.value();
        /*
            获取aspect注解中设置的注解类，若该注解类不是aspect类，则获取相关类，并存放到集合中返回
         */
        if (annotation != null && !annotation.equals(Aspect.class)) {
            targetClassSet.addAll(ClassHelper.getClassSetByAnnotation(annotation));
        }
        return targetClassSet;
    }

    /**
     * 获取代理类及其目标类集合之间的映射关系
     */
    private static Map<Class<?>, Set<Class<?>>> createProxyMap() throws Exception {
        //存储映射关系
        HashMap<Class<?>, Set<Class<?>>> proxyMap = new HashMap<Class<?>, Set<Class<?>>>();
        addAspectProxy(proxyMap);
        addTransactionProxy(proxyMap);
        return proxyMap;
    }

    private static void addAspectProxy(Map<Class<?>,Set<Class<?>>> proxyMap)throws Exception {
        Set<Class<?>> proxyClassSet = ClassHelper.getClassSetBySuper(AspectProxy.class);
        for (Class<?> proxyClass : proxyClassSet) {
            if (proxyClass.isAnnotationPresent(Aspect.class)) {
                Aspect aspect = proxyClass.getAnnotation(Aspect.class);
                Set<Class<?>> targetClassSet = createTargetClassSet(aspect);
                proxyMap.put(proxyClass, targetClassSet);
            }
        }
    }

    private static void addTransactionProxy(Map<Class<?>, Set<Class<?>>> proxyMap) {
        Set<Class<?>> serviceClassSet = ClassHelper.getClassSetByAnnotation(Service.class);
        proxyMap.put(TransactionProxy.class, serviceClassSet);
    }
    /**
     * 分析目标类与代理对象映射关系
     */
    private static Map<Class<?>, List<Proxy>> createTargetMap(Map<Class<?>, Set<Class<?>>> proxyMap) throws IllegalAccessException, InstantiationException {
        HashMap<Class<?>, List<Proxy>> targetMap = new HashMap<Class<?>, List<Proxy>>();
        for (Map.Entry<Class<?>, Set<Class<?>>> proxyEntry : proxyMap.entrySet()) {
            Class<?> proxyClass = proxyEntry.getKey();
            Set<Class<?>> targetClassSet = proxyEntry.getValue();
            //解析目标类们，创建实例对象，并保存
            for (Class<?> targetClass : targetClassSet) {
                Proxy proxy = (Proxy) proxyClass.newInstance();
                if (targetMap.containsKey((targetClass))) {
                    targetMap.get(targetClass).add(proxy);
                } else {
                    ArrayList<Proxy> proxies = new ArrayList<Proxy>();
                    proxies.add(proxy);
                    targetMap.put(targetClass, proxies);
                }
            }
        }
        return targetMap;
    }

}
