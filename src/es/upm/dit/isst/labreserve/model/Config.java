package es.upm.dit.isst.labreserve.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Config implements Serializable{

	private static final Long serialVersionUID = 1L;
	@Id
	private String type;
	private String webmaster;
	private String sessionTime; 

	public Config(String type, String webmaster, String sessionTime) {
		this.type = type;
		this.webmaster = webmaster;
		this.sessionTime = sessionTime;

	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getWebmaster() {
		return webmaster;
	}

	public void  setWebmaster (String webmaster) {
		this.webmaster = webmaster;
	}
	public String getSessionTime() {
		return sessionTime;
	}

	public void  setSessionTime (String sessionTime) {
		this.sessionTime = sessionTime;
	}

} 
