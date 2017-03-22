package info.codingfun.restful.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.Properties;
import javax.servlet.ServletContext;
import org.apache.logging.log4j.Logger;

public class SysConfigUtil {
	public static boolean loadSysConfig(Logger logger,ServletContext context,String configFile){
		try {
			logger.debug("start...");
		    Properties props = new Properties();
		    
		    String sFilePathTmp = context.getRealPath("/")+"WEB-INF/classes/" + configFile + ".xml";
		    logger.info("sFilePathTmp: " + sFilePathTmp);
		    String sFilePath = URLDecoder.decode(sFilePathTmp, "UTF-8");//sFilePathTmp.replaceAll("%20", " ");
		    logger.info("sFilePath: " + sFilePath);
		    FileInputStream fis = new FileInputStream(sFilePath);
		    logger.debug("sysConfig PATH => " + sFilePath);
		    props.loadFromXML(fis); 
		    
		    String propName = "SYSCONFIG_" + configFile;
		    printProperties(logger,propName,props);
		    context.setAttribute(propName, props);
		    return true;
		}catch(FileNotFoundException fe){
			logger.error("ERROR [FileNotFoundException] => "+fe);
		}catch(IOException e){
			logger.error("ERROR [IOException] => "+e);
		}
		return false;
	}
	
	public static void printProperties(Logger logger,String propName,Properties props) {
		logger.debug("Print Properties : " + propName + " Start...");
		Enumeration<Object> keys = props.keys();
		while (keys.hasMoreElements()) {
		  String key = (String)keys.nextElement();
		  String value = (String)props.get(key);
		  logger.debug(key + " : " + value);
		}	
	}
	
	public static String getValue(ServletContext context,String configFile,String key) {
		Properties props = (Properties) context.getAttribute("SYSCONFIG_" + configFile);
		return props.getProperty(key,"");
	}
	
	public static String getValue(ServletContext context,String key) {
		return getValue(context,"sysConfig",key);
	}
	
	public static String getValue(Logger logger,String classPath,String configFile,String key) {
		try {
			logger.debug("start...");
		    Properties props = new Properties();
		    
		    logger.info("classPath: " + classPath);
		    classPath = URLDecoder.decode(classPath, "UTF-8");
		    logger.info("classPath: " + classPath);
		    String sFilePath = classPath + configFile + ".xml";
		    FileInputStream fis = new FileInputStream(sFilePath);
		    logger.debug("sysConfig PATH => " + sFilePath);
		    props.loadFromXML(fis); 
		    
		    return props.getProperty(key,"");
		}catch(FileNotFoundException fe){
			logger.error("ERROR [FileNotFoundException] => "+fe);
		}catch(IOException e){
			logger.error("ERROR [IOException] => "+e);
		}
		return "";
	}	
}
