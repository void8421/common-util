package com.zlh.common.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Properties;

/**
 * 类名称 : PropsUtils 类描述 : 用于更新读取*.properties文件,
 * 特别说明：为方便读，请使用getDefaultKeyFromClasspath方法，读取classpath下的res.properties
 *
 * @author 10448
 */
public class PropsUtils {
    private static Properties properties;
    private static PropsUtils proUtil;

    static {
        properties = new Properties();
        proUtil = new PropsUtils();
    }

    public PropsUtils() {
    }

    /**
     * initStream
     *
     * @return Properties
     */
    public static Properties initStream(InputStream in, String... file) throws Exception {
        FileInputStream inputFile;
        if (file != null && file.length == 1) {
            inputFile = new FileInputStream(file[0]);
            properties.load(inputFile);
        } else {
            properties.load(in);
        }
        return properties;
    }

    /**
     * <pre>
     * 从classpath得到Properties中的值
     * &#64;param key  要读取的key
     * &#64;param pathAndName 要读的文件(含路径),
     * eg:getKeyFromClasspath(key,"res.properties")
     * 或getKeyFromClasspath(key,"com/utils/res.properties")
     * &#64;return value
     * </pre>
     */
    public static String getKeyFromClasspath(String key, String pathAndName) {
        String value = null;
        try {
            InputStream in = proUtil.getClass().getClassLoader().getResourceAsStream(pathAndName);
            Properties properties = initStream(in);
            value = properties.getProperty(key);
        } catch (Exception e) {
            System.err.println("load file error , file---->[" + pathAndName + "]," + " error message: " + e.toString());
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 默认从classpath下的res.properties得到Properties中的值
     *
     * @param key         要读取的key
     * @return value
     */
    public static String getDefaultKeyFromClasspath(String key) {
        String pathAndName = "conf/res.properties";
        String value = null;
        try {
            InputStream in = proUtil.getClass().getClassLoader().getResourceAsStream(pathAndName);
            Properties properties = initStream(in);
            value = properties.getProperty(key);
        } catch (Exception e) {
            System.err.println("load file error , file---->[" + pathAndName + "]," + " error message: " + e.toString());
            e.printStackTrace();
        }
        return value;
    }

    /**
     * <pre>
     * 从文件系统得到Properties中的值
     * &#64;param key  要读取的key
     * &#64;param pathAndName 要读的文件(含路径)
     * eg: getKeyFromFileSystem(key,"d:/res.properties")
     * &#64;return value
     * </pre>
     */
    public static String getKeyFromFileSystem(String key, String pathAndName) {
        String value = null;
        try {
            InputStream in = new FileInputStream(pathAndName);
            Properties properties = initStream(in);
            value = properties.getProperty(key);
        } catch (Exception e) {
            System.err.println("load file error , file---->[" + pathAndName + "]," + " error message: " + e.toString());
            e.printStackTrace();
        }
        return value;
    }

    /**
     * 在classpath改变或添加一个key的值，当key存在于properties文件中时该key的值被value所代替，
     * 当key不存在时，该key的值是value
     *
     * @param key         要存入的键
     * @param value       要存入的值
     * @param pathAndName 要存的文件(含路径)
     */
    public static void setClasspathPropes(String key, String value, String pathAndName) {
        try {
            InputStream in = proUtil.getClass().getClassLoader().getResourceAsStream(pathAndName);
            Properties properties = initStream(in);
			in.close();
            FileOutputStream out = new FileOutputStream("src/" + pathAndName);
            properties.setProperty(key, value);
            properties.store(out, "update  " + key + " value");
            out.close();
        } catch (Exception e) {
            System.err.println(
                    "set property error , file---->[" + pathAndName + "]," + " error message: " + e.toString());
            e.printStackTrace();
        }
    }

    /**
     * 在文件系统改变或添加一个key的值，当key存在于properties文件中时该key的值被value所代替， 当key不存在时，该key的值是value
     *
     * @param key         要存入的键
     * @param value       要存入的值
     * @param pathAndName 要存的文件(含路径)
     */
    public static void setFileSystemPropes(String key, String value, String pathAndName) {
        try {
            InputStream in = new FileInputStream(pathAndName);
            Properties properties = initStream(in);
            in.close();
            FileOutputStream out = new FileOutputStream(pathAndName);
            properties.setProperty(key, value);
            properties.store(out, "update  " + key + " value");
            out.close();
        } catch (Exception e) {
            System.err.println(
                    "set property error , file---->[" + pathAndName + "]," + " error message: " + e.toString());
            e.printStackTrace();
        }
    }
}