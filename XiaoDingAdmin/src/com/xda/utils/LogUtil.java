package com.xda.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogUtil {

	 //使用slf4j创建日志对象，好处时，以后更换为其它日志工具时，只要修改配置文件，不用修改代码  
    private static Logger logger = LoggerFactory.getLogger(LogUtil.class);  
    //使用log4j创建日志对象  
//  private org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(getClass());  
      
    public static void error(String message) {  
        logger.error(message);  
    }  
    public static void debug(String message) {  
        logger.debug(message);  
    }  
    public static void info(String message) {  
        logger.info(message);  
//      org.apache.log4j.Logger.getLogger(LogUtil.class).info(message);  
    }  
}
