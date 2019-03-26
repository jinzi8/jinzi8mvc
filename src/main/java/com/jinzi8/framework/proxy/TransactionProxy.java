package com.jinzi8.framework.proxy;

import com.jinzi8.framework.annotation.Transaction;
import com.jinzi8.framework.helper.DatabaseHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * 事务代理
 * @author 博哥
 * @create 2019-03-26 13:33
 */
public class TransactionProxy implements  Proxy {
    private static final Logger LOGGER = LoggerFactory.getLogger(Transaction.class);
    private static final ThreadLocal<Boolean> FLAG_HOLDER = new ThreadLocal<Boolean>(){
            @Override
            protected Boolean initialValue() {
                return false;
            }

    };
    @Override
    public Object doProxy(ProxyChain proxyChain) throws Throwable {
    Object result = null;
    boolean flag = FLAG_HOLDER.get();
    Method method = proxyChain.getTargetMethod();
    //如果fla改为false，并且执行的目标方法上使用Transaction注解标识
    if (!flag && method.isAnnotationPresent(Transaction.class)) {
        FLAG_HOLDER.set(true);
        try {
            DatabaseHelper.beginTransaction();
            LOGGER.debug("开启事务");
            //放行，得到返回结果
            result = proxyChain.doProxyChain();
            //提交事务
            DatabaseHelper.commitTransaction();
            LOGGER.debug("提交事务");
        } catch (Exception e) {
            DatabaseHelper.rollbackTransaction();
            LOGGER.debug("回滚事务");
        }finally{
            FLAG_HOLDER.remove();
        }
    }else{
        result = proxyChain.doProxyChain();
    }
    return result;
    }
}
