package com.jinzi8.framework.bean;

import com.jinzi8.framework.util.CastUtil;

import java.util.Map;

/**
 * 封装请求参数
 *
 * @author 博哥
 * @create 2019-03-21 9:30
 */
public class Param {
    /**
     * 保存请求的参数
     */
    private Map<String, Object> paramMap;

    public Param(Map<String, Object> paramMap) {
        this.paramMap = paramMap;
    }

    /**
     * 根据参数名获取Long类型参数值
     */
    public long getLong(String name) {
        return CastUtil.castLong(paramMap.get(name));
    }

    public int getInt(String name) {
        return CastUtil.castInt(paramMap.get(name));
    }

    public boolean getBoolean(String name) {
        return CastUtil.castBoolean(paramMap.get(name));
    }

    public double getDouble(String name) {
        return CastUtil.castDouble(paramMap.get(name));
    }

    public String getString(String name) {
        return CastUtil.castString(paramMap.get(name),"");
    }

    /**
     * 获取所有字段信息
     */
    public Map<String, Object> getMap() {
        return paramMap;
    }

}

