package info.codingfun.restful.service;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Service;

import info.codingfun.restful.beanwa.WABlog;
import info.codingfun.restful.beanwa.WABlogPK;
import info.codingfun.restful.constants.BLOG_STATUS;
import info.codingfun.restful.constants.DATA_ENTRY_TYPE;
import info.codingfun.restful.transactional.WAServiceEntry;
import info.codingfun.restful.xsd.BlogPostListType;
import info.codingfun.restful.xsd.BlogPostType;

@Service("blogService")
public class BlogServiceImpl implements IBlogService {
	private static Logger logger = LogManager.getLogger(IBlogService.class.getName());	  
	static {
	    ThreadContext.put("className", IBlogService.class.getName());
	}
	  
    public static XMLGregorianCalendar toXMLGregorianCalendar(Date date){
        GregorianCalendar gCalendar = new GregorianCalendar();
        gCalendar.setTime(date);
        XMLGregorianCalendar xmlCalendar = null;
        try {
            xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar(gCalendar);
        } catch (DatatypeConfigurationException ex) {
            logger.error(ex);
        }
        return xmlCalendar;
    }
  
    public static Date toDate(XMLGregorianCalendar calendar){
        if(calendar == null) {
            return null;
        }
        return calendar.toGregorianCalendar().getTime();
    }

	@Override
	public String createOrSaveNewPost(BlogPostType blogPostType) {
	    WAServiceEntry waServiceEntry = new WAServiceEntry();
	    WABlog waBlog = waServiceEntry.addWABlog(blogPostType.getTitle(), blogPostType.getSlogan(),
	    		blogPostType.getDescription(), "", 0, "", blogPostType.getModifyEmp());	    
	    logger.info(waBlog);
	    if (waBlog != null)
	    	return "create Post - Blog Id: " + waBlog.getWABlogPK().getBlogId();
	    else
	    	return "create Post fail.";
	}

	@Override
	public BlogPostType getBlogPostInfo(int blogId) {
		logger.info("blogId: " + blogId);
		WAServiceEntry waServiceEntry = new WAServiceEntry();
		WABlog waBlog = waServiceEntry.retriveWABlogData(blogId);

		BlogPostType blogPostType = new BlogPostType();
	    if (waBlog != null) {
	    	blogPostType.setBlogId(waBlog.getWABlogPK().getBlogId());
	    	blogPostType.setTitle(waBlog.getTitle());
	    	blogPostType.setSlogan(waBlog.getSlogan());
	    	blogPostType.setDescription(waBlog.getDescription());
	    	blogPostType.setStatus(waBlog.getStatus());
	    	blogPostType.setModifyTime(toXMLGregorianCalendar(waBlog.getModifyTime()));
	    	blogPostType.setModifyEmp(waBlog.getModifyEmp());
	    }
	    return blogPostType;
	}

	@Override
	public String updateBlogPostInfo(BlogPostType blogPostType) {	  
	    WABlog waBlog = new WABlog(new WABlogPK(blogPostType.getBlogId()), blogPostType.getTitle(), 
	    		blogPostType.getSlogan(), blogPostType.getDescription(), blogPostType.getStatus(), 
	    		toDate(blogPostType.getModifyTime()), blogPostType.getModifyEmp());
	    
	    WAServiceEntry waServiceEntry = new WAServiceEntry();
	    boolean bOK = waServiceEntry.modifyWA(DATA_ENTRY_TYPE.MODIFY, waBlog);
	    return (bOK == true) ? "update blog Post successfully." : "update blog Post fail.";
	}

	@Override
	public String deleteBlogPostInfo(int blogId) {
		logger.info("blogId: " + blogId);
		WAServiceEntry waServiceEntry = new WAServiceEntry();
	    boolean bOK = waServiceEntry.modifyWABlogStatus(blogId, BLOG_STATUS.BLOG_CANCEL.getValue(),
	    		"restful");
	    return (bOK == true) ? "delete blog Post successfully." : "delete blog Post fail.";
	}

	@Override
	public BlogPostListType getAllBlogPostInfo() {
		WAServiceEntry waServiceEntry = new WAServiceEntry();
		List<WABlog> waBlogList = waServiceEntry.retriveWABlog(20);
		if (waBlogList == null)
			return null;
		
	    BlogPostListType blogPostListType = new BlogPostListType();
	    for (WABlog waBlog : waBlogList) {
	      BlogPostType blogPostType = new BlogPostType();
		  blogPostType.setBlogId(waBlog.getWABlogPK().getBlogId());
		  blogPostType.setTitle(waBlog.getTitle());
		  blogPostType.setSlogan(waBlog.getSlogan());
		  blogPostType.setDescription(waBlog.getDescription());
		  blogPostType.setStatus(waBlog.getStatus());
		  blogPostType.setModifyTime(toXMLGregorianCalendar(waBlog.getModifyTime()));
		  blogPostType.setModifyEmp(waBlog.getModifyEmp());
		    
	      blogPostListType.getBlogPostType().add(blogPostType);	      
	    }
	    
	    return blogPostListType;
	}
}
