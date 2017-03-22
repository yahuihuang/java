package info.codingfun.restful.beanwa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "WABlog")
public class WABlog implements Serializable {
	@EmbeddedId
	@AttributeOverrides({
        @AttributeOverride(
            name = "blogId",
            column = @Column(name = "blogId"))
    })
	protected WABlogPK WABlogPK;
	@Column(name = "title")
	protected String title;
	@Column(name = "slogan")
	protected String slogan;
	@Column(name = "description")
	protected String description;
	@Column(name = "status")
	protected int status;
	@Column(name = "modifyTime")
	protected Date modifyTime;
	@Column(name = "modifyEmp")
	protected String modifyEmp;

	public WABlog() {
		
	}
	
	public WABlog(WABlogPK PK) {
		this.WABlogPK = PK;
	}
	
	public WABlog(WABlogPK wABlogPK, String title, String slogan, String description,
			int status, Date modifyTime, String modifyEmp) {
		super();
		WABlogPK = wABlogPK;
		this.title = title;
		this.slogan = slogan;
		this.description = description;
		this.status = status;
		this.modifyTime = modifyTime;
		this.modifyEmp = modifyEmp;
	}

	public WABlogPK getWABlogPK() {
		return WABlogPK;
	}

	public void setWABlogPK(WABlogPK wABlogPK) {
		WABlogPK = wABlogPK;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public String getModifyEmp() {
		return modifyEmp;
	}

	public void setModifyEmp(String modifyEmp) {
		this.modifyEmp = modifyEmp;
	}
	
	@Override
	public String toString() {
		return "WABlog [WABlogPK=" + WABlogPK + ", title=" + title + ", slogan=" + slogan + ", description="
				+ description + ", status=" + status + ", modifyTime=" + modifyTime + ", modifyEmp=" + modifyEmp + "]";
	}	
}
