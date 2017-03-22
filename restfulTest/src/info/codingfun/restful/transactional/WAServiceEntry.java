package info.codingfun.restful.transactional;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

import info.codingfun.restful.beanwa.WABlog;
import info.codingfun.restful.beanwa.WAIPDeny;
import info.codingfun.restful.constants.DATA_ENTRY_TYPE;

public class WAServiceEntry {
	private static Logger logger = LogManager.getLogger(WAServiceEntry.class.getName());
	private static GenericXmlApplicationContext ctx = null;
	static {
		ctx = new GenericXmlApplicationContext();
		ctx.load(new ClassPathResource("../applicationContext_WA.xml"));
		ctx.refresh();
		
		logger.warn("WAServiceEntry init ");
	}
	private WAServiceDao waServiceDao = null;
	
	public WAServiceEntry() {
		waServiceDao = ctx.getBean("WAServiceDao",WAServiceDao.class);
		logger.debug("waServiceDao init ");
	}
		
	//0.Start
	public boolean modifyWA(DATA_ENTRY_TYPE data_entry_type, Object object) {
		return waServiceDao.modifyWA(data_entry_type, object);
	}
	
	//1.Blog
	public List<WABlog> retriveWABlog(int iMaxRecord) {
		return waServiceDao.retriveWABlog(iMaxRecord);
	}
	
	public List<WABlog> retriveWABlogNew(int iMaxRecord,int iLastMaxId) {
		return waServiceDao.retriveWABlogNew(iMaxRecord,iLastMaxId);
	}

	public WABlog retriveWABlogData(int blogId) {
		return waServiceDao.retriveWABlogData(blogId);
	}

	public WABlog addWABlog(String title, String slogan, String description, String content,
			int imgKind, String imgUrl, String modifyEmp) {
		return waServiceDao.addWABlog(title,slogan,description,content,imgKind,imgUrl,modifyEmp);
	}
	
	public boolean modifyWABlogStatus(int blogId,int status,String modifyEmp) {
		return waServiceDao.modifyWABlogStatus(blogId,status,modifyEmp);
	}
	
	//2.WAIPDeny
	public List<WAIPDeny> retriveWAIPDeny() {
		return waServiceDao.retriveWAIPDeny();
	}
}
