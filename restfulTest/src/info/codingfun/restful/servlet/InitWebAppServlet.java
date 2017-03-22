package info.codingfun.restful.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import info.codingfun.restful.util.DataUtil;
import info.codingfun.restful.util.SysConfigUtil;

public class InitWebAppServlet extends HttpServlet {
	private static Logger logger = LogManager.getLogger(InitWebAppServlet.class.getName());
	static {
		ThreadContext.put("className", InitWebAppServlet.class.getName());
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {		
		super.init(config);
		ServletContext context = config.getServletContext();
		
		//1.取得系統設定檔
		String sConfigList = DataUtil.StringNullIf(config.getInitParameter("configlist"),"sysConfig");
		String configFile[] = sConfigList.split(",");
		for (int iIdxF=0;iIdxF < configFile.length;iIdxF++) {
			boolean bLoadOK = SysConfigUtil.loadSysConfig(logger,context,configFile[iIdxF]);
			if (bLoadOK==true)
				logger.debug("Load Config File - [" + configFile[iIdxF] + "] => Load OK");
			else
				logger.error("Load Config File - [" + configFile[iIdxF] + "] => Load Fail");
		}
	}

	public void destroy() {
	    logger.info(getServletName() + " ending...");
	}
}