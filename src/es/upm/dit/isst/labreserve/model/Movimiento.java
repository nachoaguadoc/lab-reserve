package es.upm.dit.isst.labreserve.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Movimiento implements Serializable {
	private static final Long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long resourceid;
	private String resourcename;
	private String date;
	private int tipo; //1 reserva 2 cancelacion 3 registrado nuevo usuario


	public Movimiento (Long resourceid, String resourcename, String date, int tipo) {
		this.resourceid = resourceid;
		this.resourcename = resourcename;
		this.date = date;
		this.tipo = tipo;
	}

	public Long getId() {
		return id;
	}

	public Long getResourceid() {
		return resourceid;
	}

	public void setResource(Long resourceid) {
		this.resourceid = resourceid;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public String getResourcename() {
		return resourcename;
	}

	public void setResourcename(String resourcename) {
		this.resourcename = resourcename;
	}

	
} 
