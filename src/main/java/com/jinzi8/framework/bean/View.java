package com.jinzi8.framework.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回视图对象
 *
 * @author 博哥
 * @create 2019-03-21 9:44
 */
public class View {
    /**
     * 视图路径
     */
    private String path;

    /**
     * 模型数据
     */
    private Map<String, Object> model;

    public View(String path) {
        this.path = path;
        model = new HashMap<String, Object>();
    }

    /**
     * 向模型中添加数据
     */
    public View addModel(String key, Object value) {
        model.put(key, value);
        return this;
    }

    public String getPath() {
        return path;
    }

    public Map<String, Object> getModel() {
        return model;
    }

}
