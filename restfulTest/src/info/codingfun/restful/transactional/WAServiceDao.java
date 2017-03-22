package info.codingfun.restful.transactional;

import java.util.List;

import info.codingfun.restful.beanwa.WABlog;
import info.codingfun.restful.beanwa.WAIPDeny;
import info.codingfun.restful.constants.DATA_ENTRY_TYPE;

public interface WAServiceDao {
	//0.Start
	public boolean modifyWA(DATA_ENTRY_TYPE data_entry_type, Object object);
		
	//1.Blog
	public List<WABlog> retriveWABlog(int iMaxRecord);
	public List<WABlog> retriveWABlogNew(int iMaxRecord,int iLastMaxId);
	public WABlog retriveWABlogData(int blogId);
	public WABlog addWABlog(String title,String slogan,String description,String content,
			int imgKind,String imgUrl,String modifyEmp);
	public boolean modifyWABlogStatus(int blogId,int status,String modifyEmp);

	//2.WAIPDeny
	public List<WAIPDeny> retriveWAIPDeny();
}
