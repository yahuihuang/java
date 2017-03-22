package info.codingfun.restful.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import info.codingfun.restful.xsd.BlogPostListType;
import info.codingfun.restful.xsd.BlogPostType;

@Path("/blogservice")
public abstract interface IBlogService {
	  @POST
	  @Path("addpost")
	  @Consumes({"application/xml", "application/json"})
	  @Produces({"application/x-www-form-urlencoded"})
	  public abstract String createOrSaveNewPost(BlogPostType blogPostType);
	  
	  @GET
	  @Path("getpost/{id}")
	  @Consumes({"application/x-www-form-urlencoded"})
	  @Produces({"application/xml", "application/json"})
	  public abstract BlogPostType getBlogPostInfo(@PathParam("id") int blogId);
	  
	  @PUT
	  @Path("updatepost")
	  @Consumes({"application/xml", "application/json"})
	  @Produces({"application/x-www-form-urlencoded"})
	  public abstract String updateBlogPostInfo(BlogPostType blogPostType);
	  
	  @DELETE
	  @Path("deletepost")
	  @Consumes({"application/xml", "application/json"})
	  @Produces({"application/x-www-form-urlencoded"})
	  public abstract String deleteBlogPostInfo(@PathParam("id") int blogId);
	  
	  @GET
	  @Path("getallpost")
	  @Consumes({"application/x-www-form-urlencoded"})
	  @Produces({"application/xml", "application/json"})
	  public abstract BlogPostListType getAllBlogPostInfo();
}
