package info.codingfun.restful.util;

import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.FactoryBean;

public class PropertiesEncryptFactoryBean implements FactoryBean {
	private static Logger logger = LogManager.getLogger(PropertiesEncryptFactoryBean.class.getName());
	static {
		ThreadContext.put("className", PropertiesEncryptFactoryBean.class.getName());
	}
	
	private Properties properties;  
    
    public Object getObject() throws Exception {  
        return getProperties();  
    }  
  
    public Class getObjectType() {  
        return java.util.Properties.class;  
    }  
  
    public boolean isSingleton() {  
        return true;  
    }  
  
    public Properties getProperties() {  
        return properties;  
    }  
  
    public void setProperties(Properties inProperties) {  
        this.properties = inProperties;  
        String originalPassword = properties.getProperty("password"); 
        if (originalPassword != null){  
            String newPassword = deEncryptPassword(originalPassword);  
            properties.put("password", newPassword);  
        }  
    }   
      
    private String deEncryptPassword(String originalPassword){ 
        String sPassword = "";
		try {
			DesUtil des = new DesUtil();
			sPassword = des.decrypt(originalPassword);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("EncryptedDataSource decrypt db login password exception"+e);
		}
		return sPassword;
    } 
}
