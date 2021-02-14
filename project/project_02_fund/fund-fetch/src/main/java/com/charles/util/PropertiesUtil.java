/*
 * Power by www.xiaoi.com
 */
package com.charles.util;


import org.apache.commons.lang3.StringUtils;


import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author eko.zhan
 * @date Nov 14, 2014 2:35:56 PM
 * @version 1.0
 */

public class PropertiesUtil {


	/**
	 * 系统属性
	 */
	private static final String APP_PROPERTIES_FILE_NAME = "app.properties";
	private static Map<String, String> params = reader(APP_PROPERTIES_FILE_NAME);
	
	/**
	 * 如果没有找到配置则返回空字符串
	 * @param key
	 * @return
	 */
	public static String getString(String key) {
		return getString(key, "");
	}
	
	/**
	 * 如果没有找到配置则返回默认字符串
	 * @param key
	 * @return
	 */
	public static String getString(String key, String defaultValue){
		String val = params.get(key);

		if (StringUtils.isBlank(val)) {
			return defaultValue;
		}
		return val;
	}
	
	/**
	 * 获取所以配置文件中的值
	 * @param key
	 * @return
	 */
	public static String value(String key){
		return getString(key, null);
	}
	
	/**
	 * 获取所以配置文件中的值
	 * 	不存在将返回false
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(String key){
		return getBoolean(key, false);
	}
	
	/**
	 * 获取所以配置文件中的值
	 * 	不存在返回指定数据
	 * @param key	
	 * @param defaultValue 指定数据
	 * @return
	 */
	public static boolean getBoolean(String key, boolean defaultValue){
		String value = value(key);
		return StringUtils.isBlank(value) ? defaultValue : Boolean.parseBoolean(value);
	}
	
	/**
	 * ,分隔获取为List<String>
	 * @param key
	 * @return
	 */
	public static List<String> getList(String key) {
		String val = getString(key);
		if (StringUtils.isNotBlank(val)) {
			List<String> list = new ArrayList<String>();
			for (String str : val.split(",")) {
				if (StringUtils.isNotBlank(str))
					list.add(str);
			}
			if (list.size() > 0)
				return list;
		}
		return null;
	}

    /**
     * 修改属性值
     */
    public static void updateValue(String key, String value) {
        params.put(key, value);
    }

	public static int valueToInt(String key, int defaultValue) {
		String value = value(key);
		return StringUtils.isNotBlank(value) ? Integer.parseInt(value) : defaultValue;
	}

	/**
	 * 重新加载配置文件
	 * app.properties
	 */
	public static void reloadApp() {
		try {
			params = reader(APP_PROPERTIES_FILE_NAME);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Map<String, String> reader(String propertiesFileName) {
		Properties properties = new Properties() {
			private static final long serialVersionUID = 1L;

			{
				InputStream is = null;
				try {
					is = PropertiesUtil.class.getResourceAsStream("/" + propertiesFileName);
					load(is);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					try {
						if (is != null) {
							is.close();
						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};

		return new ConcurrentHashMap<String, String>(properties.size()) {
			private static final long serialVersionUID = 1L;

			{
				for (String key : properties.stringPropertyNames()) {
					put(key, properties.getProperty(key));
				}
			}
		};
	}

}
