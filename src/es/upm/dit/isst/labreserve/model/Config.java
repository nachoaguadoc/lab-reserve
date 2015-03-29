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
	private String opening;
	private String closing;
	
	
	public Config(String type, String webmaster, String sessionTime, String opening, String closing) {
		this.type = type;
		this.webmaster = webmaster;
		this.sessionTime = sessionTime;
		this.opening = opening;
		this.closing = closing;
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

	public String getOpening() {
		return opening;
	}

	public void  setOpening (String opening) {
		this.opening = opening;
	}
	
	public String getClosing() {
		return closing;
	}

	public void  setClosing (String closing) {
		this.closing = closing;
	}
} 
