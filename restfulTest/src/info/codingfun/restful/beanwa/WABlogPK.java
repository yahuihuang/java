package info.codingfun.restful.beanwa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Embeddable
public class WABlogPK implements Serializable {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "blogId")
	private int blogId;
	
	public WABlogPK() {
		
	}
	
	public WABlogPK(int blogId) {
		this.blogId = blogId;
	}

	@Override
	public int hashCode() {
	    final int prime = 31; 
	    int result = 1; 
	    result = prime * result + blogId;
	    return result; 
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) 
			return true; 
		if (obj == null) 
	        return false; 
	    if (getClass() != obj.getClass()) 
	        return false; 
	    
	    final WABlogPK other = (WABlogPK) obj; 
	    if (blogId != other.blogId) 
	    	return false;
	    
	    return true; 
	}
	
	public int getBlogId() {
		return blogId;
	}

	public void setBlogId(int blogId) {
		this.blogId = blogId;
	}

	@Override
	public String toString() {
		return "WABlogPK [blogId=" + blogId + "]";
	}		
}
