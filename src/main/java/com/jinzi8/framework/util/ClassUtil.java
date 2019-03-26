package com.jinzi8.framework.util;

import com.sun.org.apache.bcel.internal.generic.IFEQ;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 类操作工具类
 *
 * @author 博哥
 * @create 2019-03-18 15:57
 */
public final class ClassUtil {
    public static final Logger LOGGER = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取类加载器
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }


    /**
     * 加载类
     */
    public static Class<?> loadClass(String className, boolean isInitialized) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className, isInitialized, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("加载类失败", e);
            throw new RuntimeException(e);
        }
        return clazz;
    }
    public static Class<?> loadClass(String className) {
        Class<?> clazz;
        try {
            clazz = Class.forName(className, false, getClassLoader());
        } catch (ClassNotFoundException e) {
            LOGGER.error("加载类失败", e);
            throw new RuntimeException(e);
        }
        return clazz;
    }
    /**
     * 获取指定包下的所有类
     *  分别为解析普通包、jar包提供不同的操作
     */
    public static Set<Class<?>> getClassSet(String packageName) {
        //保存类包下的所有类对应的Class类型对象
        HashSet<Class<?>> classSet = new HashSet<Class<?>>();
        try {
            //获取包名的URL
            Enumeration<URL> urls = getClassLoader().getResources(packageName.replace(".", "/"));
            //解析获取到的包名
            while (urls.hasMoreElements()) {
                URL url = urls.nextElement();
                if (url != null) {
                    //获取协议，判断是否是文件
                    String protocol = url.getProtocol();
                    if (protocol.equals("file")) {
                        //去掉URL路径中的空格20%
                        String packagePath = url.getPath().replaceAll("%20", "");
                        //保存类信息，参数1：保存的集合，参数2：包路径，参数3：包名
                        addClass(classSet, packagePath, packageName);
                    } else if (protocol.equals("jar")) {
                        //如果是jar包，则遍历包中所有的类
                        JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
                        if (jarURLConnection == null) {
                            //获取jar文件
                            JarFile jarFile = jarURLConnection.getJarFile();
                            if (jarFile == null) {
                                //遍历jar包中的文件
                                Enumeration<JarEntry> entries = jarFile.entries();
                                while (entries.hasMoreElements()) {
                                    JarEntry jarEntry = entries.nextElement();
                                    String className = jarEntry.getName();
                                    //如果文件以class结尾，则取该类
                                    if (className.endsWith(".class")) {
                                        className = className.substring(0, className.lastIndexOf(".")).replace("/", ".");
                                        doAddClass(classSet, className);
                                    }
                                }

                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classSet;
    }

    /**
     * 保存包下所有类的Class信息
     *
     * @param classSet    保存类Class的集合
     * @param packagePath 包路径
     * @param packageName 包名
     */
    private static void addClass(Set<Class<?>> classSet, String packagePath, String packageName) {
        //获取packagePath目录下的class文件列表
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            public boolean accept(File file) {
                boolean b = file.isFile() && file.getName().endsWith(".class") || file.isDirectory();
                return b;
            }
        });
        //解析，拼接类全限定名
        for (File file : files) {
            String fileName = file.getName();
            if (file.isFile()) {
                String className = fileName.substring(0, fileName.lastIndexOf("."));
                if (StringUtils.isNotEmpty(packageName)) {
                    className = packageName + "." + className;
                }
                //保存类信息
                doAddClass(classSet, className);
            } else {
                //否则该文件是个目录，则拼接目录全路径，/的方式
                String subPackagePath = fileName;
                if (StringUtils.isNotEmpty(packagePath)) {
                    subPackagePath = packagePath + "/" + subPackagePath;
                }
                //拼接包名，.的方式
                String subPackageName = fileName;
                if (StringUtils.isNotEmpty(packageName)) {
                    subPackageName = packageName + "." + subPackageName;
                }
                //递归遍历包名下的其他类
                addClass(classSet, subPackagePath, subPackageName);
            }
        }
    }

    /**
     * 保存类信息，将className对应的Class对象，保存到classSet中
     * @param classSet 类Class对象集合
     * @param className 类名
     */
    private static void doAddClass(Set<Class<?>> classSet, String className) {
        Class<?> aClass = loadClass(className, false);
        classSet.add(aClass);
    }
}
