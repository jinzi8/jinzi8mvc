package com.jinzi8.framework;

import com.jinzi8.framework.helper.*;
import com.jinzi8.framework.util.ClassUtil;

/**
 * 加载Helper助手类
 *
 * @author 博哥
 * @create 2019-03-20 15:57
 */
public class HelperLoader {
    public static void init(){
    Class<?>[] classList = {ClassHelper.class, BeanHeapler.class,AopHelper.class, IocHelper.class, ControllerHelper.class};
        for (Class<?> cls : classList) {
            ClassUtil.loadClass(cls.getName());
        }
    }
}

