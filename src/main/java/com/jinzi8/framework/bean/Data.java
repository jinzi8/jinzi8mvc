package com.jinzi8.framework.bean;

/**
 返回数据对象
 * @author 博哥
 * @create 2019-03-21 9:47
 */
public class Data {
    /**
     * 模型数据
     */
    private Object model;

    public Data(Object model) {
        this.model = model;
    }
    public Object getModel(){
        return model;
    }

}
