package com.simplejframework.utils;

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
 * 类加载工具类
 * Created by dell on 2017/12/25.
 */
public class ClassUtil {
    private static Logger logger = LoggerFactory.getLogger(ClassUtil.class);

    /**
     * 获取当前类加载器
     * @return
     */
    public static ClassLoader getClassLoader(){
        return Thread.currentThread().getContextClassLoader();
    }

    /**
     * 加载类
     * @param className 类全限定名
     * @param isInitialized  是否执行静态块
     * @return
     */
    public static Class<?> loadClass(String className,boolean isInitialized){
        Class<?> cls = null;
        try {
            cls = Class.forName(className,isInitialized,getClassLoader());
        } catch (ClassNotFoundException e) {
            logger.error("class not find:"+className,e);
        }
        return cls;
    }

    /**
     * 获取指定包名下所有类
     *  1.1 通过类加载器根据包名得到包的文件系统全路径
     *  1.2 根据包的路径，通过递归加载路径下的.class文件
     * @param packageName
     * @return
     */
    public static Set<Class> getClassSet(String packageName){
        Set<Class> classSet = new HashSet<>();

        try {
            if (StringUtils.isEmpty(packageName)){
                throw new RuntimeException("scan packagename can not be empty");
            }
             Enumeration<URL> urlEnumeration = getClassLoader().getResources(packageName.replace(".","/"));

             while (urlEnumeration.hasMoreElements()){
                 URL url = urlEnumeration.nextElement();
                 if (url!=null){
                     String protocol = url.getProtocol();

                     if ("file".equals(protocol)){
                         //文件路径在中文环境下空格会变成 %20
                         String packagePath = url.getPath().replace("%20"," ");
                         addClass(classSet,packagePath,packageName);
                     }else if("jar".equals(protocol)){
                         JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();

                         if (jarURLConnection!=null){
                             JarFile jarFile = jarURLConnection.getJarFile();

                             if (jarFile!=null){
                                 Enumeration<JarEntry> jarEntryEnumeration = jarFile.entries();

                                 while (jarEntryEnumeration.hasMoreElements()){
                                     JarEntry jarEntry = jarEntryEnumeration.nextElement();
                                     String jarEntryName = jarEntry.getName();

                                     if (jarEntryName.endsWith(".class")){
                                         String className = jarEntryName.substring(0,jarEntryName.lastIndexOf(".")).replaceAll("/",".");
                                         doAddClass(classSet,className);
                                     }
                                 }
                             }
                         }
                     }
                 }
             }
        } catch (IOException e) {
            logger.error("io exception",e);
        }
        return classSet;
    }

    private static void doAddClass(Set<Class> classSet, String className) {
        Class<?> cls = loadClass(className,false);
        classSet.add(cls);
    }

    /**
     * 递归加载class文件到 classset
     * @param classSet
     * @param packagePath
     * @param packageName
     */
    private static void addClass(Set<Class> classSet, String packagePath, String packageName) {
        File[] files = new File(packagePath).listFiles(new FileFilter() {
            @Override
            public boolean accept(File file) {
                return (file.isFile()&&file.getName().endsWith(".class"))||file.isDirectory();
            }
        });

        for (File file : files) {
            String fileName = file.getName();

            if (file.isFile()){
                String className = fileName.substring(0,fileName.lastIndexOf("."));
                if (StringUtils.isNotEmpty(packageName)){
                    className = packageName+"."+className;
                }
                doAddClass(classSet,className);
            }else {
                String subPackageName = fileName;
                if (StringUtils.isNotEmpty(packageName)){
                    subPackageName = packageName +"."+subPackageName;
                }
                String subPackagePath = fileName;
                if (StringUtils.isNotEmpty(packagePath)){
                    subPackagePath = packagePath + "/" +subPackagePath;
                }
                addClass(classSet,subPackagePath,subPackageName);
            }
        }
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("java.classpath"));
        //getClassSet("com.simplejframework");
    }
}
