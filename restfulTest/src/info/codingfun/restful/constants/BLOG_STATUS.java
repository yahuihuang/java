package info.codingfun.restful.constants;

public enum BLOG_STATUS {
	BLOG_EDITING(1,"編輯中"),BLOG_AUDIT(2,"審核中"),BLOG_ONBOARD(5,"已上架"),
	BLOG_CANCEL(6,"取消"),BLOG_REVOCATION(9,"已下架");
	
	protected int value ;
	protected String desc;
	BLOG_STATUS (int status,String desc){
    	this.value = status ;
    	this.desc = desc;
    }
	
	public int getValue() {
		return value;
	}
	
	public String getDesc() {
		return desc;
	}
}
