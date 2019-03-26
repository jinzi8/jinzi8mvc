package com.jinzi8.framework.proxy;

/**
 * 代理接口
 * @author 博哥
 * @create 2019-03-25 13:51
 */
public interface Proxy {
    /**
     * 执行链式代理
     */
    Object doProxy(ProxyChain proxyChain) throws Throwable;
}
