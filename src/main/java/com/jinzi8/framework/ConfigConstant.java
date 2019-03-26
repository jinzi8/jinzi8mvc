package com.jinzi8.framework;

/**
 *  提供关于配置项的常量
 *  在应用本框架时，固定为classpath目录下添加jinzi8.properties配置文件。
 *  如下指定了配置文件中的键。
 *  @author 博哥
 *  @create 2019-03-18 14:52
 */
public interface ConfigConstant {
    String CONFIG_FILE = "jinzi8.properties";
    String JDBC_DRIVER="jinzi8.framework.jdbc.driver";
    String JDBC_URL = "jinz8.framework.jdbc.url";
    String JDBC_USERNAME = "jinzi8.framework.jdbc.username";
    String JDBC_PASSWORD = "jinzi8.framework.jdbc.password";

    String APP_BASE_PACKAGE = "jinzi8.framework.app.base_package";
    String APP_JSP_PATH = "jinzi8.framework.app.jsp_path";
    String APP_ASSET_PATH = "jinzi8.framework.app.asset_path";
}