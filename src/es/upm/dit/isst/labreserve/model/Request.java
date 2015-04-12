package es.upm.dit.isst.labreserve.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Request implements Serializable{

	private static final Long serialVersionUID = 1L;
	@Id
	private String userId;
	private String email;
	private int priority;
	private String desc;
	private Date date;
	
	public Request(String userId, String email, int priority, String desc, Date date) {
		this.userId = userId;
		this.email = email;
		this.priority = priority;
		this.date = date;
		this.desc = desc;
	}
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getEmail() {
		return email;
	}

	public void  setEmail (String email) {
		this.email = email;
	}
	
	public int getPriority() {
		return priority;
	}

	public void setPriority (int priority) {
		this.priority = priority;
	}
	
	public Date getDate() {
		return date;
	}

	public void setDate (Date date) {
		this.date = date;
	}
	
	public String getDesc() {
		return desc;
	}

	public void  setDesc (String desc) {
		this.desc = desc;
	}

} 
