package info.codingfun.restful.servlet;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import info.codingfun.restful.util.SysConfigUtil;

public class FilterServlet implements Filter {	
	protected Logger logger = LogManager.getLogger(this.getClass().getName());
	//for 弱掃 Host header attack
	private boolean bLoad_AllowList = false;
	private static String hostAllowList_default = "127.0.0.1:8080,127.0.0.1,172.24.20.54,localhost";
	private List<String> hostAllowList = new LinkedList<String>();
	
	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, 
			FilterChain filterChain)
			throws IOException, ServletException {		
		//for 弱掃 Host header attack		
		boolean bCheckHost = Check_HostAllowList(request, response);
		logger.info("bCheckHost: " + bCheckHost);
		if (bCheckHost == false) {	
			if (response instanceof HttpServletResponse) {
				((HttpServletResponse)response).sendError(HttpServletResponse.SC_FORBIDDEN);
			}
			return;
		}
		
		filterChain.doFilter(request, response);
	}
	
	/**
	 * for 弱掃 Host header attack
	 * @param request
	 * @param response
	 * @return
	 */
	private boolean Check_HostAllowList(ServletRequest request, ServletResponse response) {
		boolean bLoadOK = Load_HostAllowList(request);
		if (bLoadOK == false)
			return false;
		
		boolean bOK = false;
		if (request instanceof HttpServletRequest) {	
			String host = ((HttpServletRequest) request).getHeader("HOST");
			for (String allowHost : hostAllowList) {
				if (host.compareTo(allowHost) == 0) {
					bOK = true;
					break;
				}
			}
			
			if (bOK == false)
				logger.info("Check_HostAllowList fail host: " + host);
		}
		
		return bOK;
	}
	
	/**
	 * for 弱掃 Host header attack
	 * @param request
	 * @return
	 */
	private boolean Load_HostAllowList(ServletRequest request) {
		if (bLoad_AllowList == true)
			return bLoad_AllowList;
		
		String hostAllow = SysConfigUtil.getValue(request.getServletContext(), 
									"Header_HOST.allow");
		if (hostAllow == null)
			hostAllow = hostAllowList_default;
		logger.info("Header_HOST.allow: " + hostAllow);
		String hostAllowAry[] = hostAllow.split(",");
		for (int iIdx = 0; iIdx < hostAllowAry.length; iIdx++) {
			hostAllowList.add(hostAllowAry[iIdx]);
			logger.info("Header_HOST.allow-" + (iIdx+1) + " : " + hostAllowAry[iIdx]);
		}
		
		bLoad_AllowList = true;
		return bLoad_AllowList;
	}
	
	@Override
	public void destroy() {
		logger.info("Destroy");
	}
}