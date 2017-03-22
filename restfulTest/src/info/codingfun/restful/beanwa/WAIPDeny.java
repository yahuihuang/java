package info.codingfun.restful.beanwa;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "WAIPDeny")
public class WAIPDeny implements Serializable {
	@Id
	@Column(name = "networkIp")
	protected String networkIp;
	@Column(name = "subnetMask")
	protected String subnetMask;
	@Column(name = "modifyTime")
	protected Date modifyTime;
	@Column(name = "modifyEmp")
	protected String modifyEmp;
	
	public WAIPDeny() {
		
	}
	
	public WAIPDeny(String networkIp, String subnetMask, Date modifyTime, String modifyEmp) {
		super();
		this.networkIp = networkIp;
		this.subnetMask = subnetMask;
		this.modifyTime = modifyTime;
		this.modifyEmp = modifyEmp;
	}
	
	public String getNetworkIp() {
		return networkIp;
	}

	public void setNetworkIp(String networkIp) {
		this.networkIp = networkIp;
	}

	public String getSubnetMask() {
		return subnetMask;
	}

	public void setSubnetMask(String subnetMask) {
		this.subnetMask = subnetMask;
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
		return "WAIPDeny [networkIp=" + networkIp + ", subnetMask=" + subnetMask + ", modifyTime=" + modifyTime
				+ ", modifyEmp=" + modifyEmp + "]";
	}	
}
