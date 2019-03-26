package com.jinzi8.framework.helper;

import com.jinzi8.framework.annotation.Action;
import com.jinzi8.framework.bean.Handler;
import com.jinzi8.framework.bean.Request;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 控制器助手类
 *
 * @author 博哥
 * @create 2019-03-20 9:01
 */
public class ControllerHelper {
    /**
     * 用于存放请求与处理器映射关系（简称 Action Map）
     */
    private static final Map<Request, Handler> ACTION_MAP = new HashMap<Request, Handler>();

    static {
        //获取所有的Controller类
        Set<Class<?>> controllerClassSet = ClassHelper.getControllerClassSet();
        if (CollectionUtils.isNotEmpty(controllerClassSet)) {
            //遍历这些Controller类
            for (Class<?> controllerClass : controllerClassSet) {
                //获取Controller类中定义的方法
                Method[] declaredMethods = controllerClass.getDeclaredMethods();
                if (ArrayUtils.isNotEmpty(declaredMethods)) {
                    //遍历这些方法，判断哪些方法带有action注解
                    for (Method declaredMethod : declaredMethods) {
                        if (declaredMethod.isAnnotationPresent(Action.class)) {
                            //从Action注解中获取URL映射规则
                            Action annotation = declaredMethod.getAnnotation(Action.class);
                            String mapping = annotation.value();

                            //匹配规则：请求方式:请求路径
                            if (mapping.matches("\\w+:/\\w*")) {
                                String[] array = mapping.split(":");
                                if (ArrayUtils.isNotEmpty(array) && array.length == 2) {
                                    //获取请求方法与请求路径
                                    String requestMethod = array[0];
                                    String requestPath = array[1];
                                    //封装请求对象与处理器对象
                                    Request request = new Request(requestMethod, requestPath);
                                    Handler handler = new Handler(controllerClass, declaredMethod);
                                    //初始化Action Map，保存请求对象与处理器对象的映射关系
                                    ACTION_MAP.put(request, handler);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 获取Handler
     */
    public static Handler getHandler(String requestMethod, String requestPath) {
        Request request = new Request(requestMethod, requestPath);
        return ACTION_MAP.get(request);
    }
}
