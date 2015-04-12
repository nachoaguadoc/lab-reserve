package es.upm.dit.isst.labreserve.dao;

import java.util.Date;
import java.util.List;

import es.upm.dit.isst.labreserve.model.Request;

public interface RequestDAO {
	
	public List<Request> listRequests();
	
	public void add (String userId, String email, int priority, String desc, Date date);
	
	public void update (String userId, String email, int priority, String desc, Date date);
	
	public Request getRequest(String userId);
	
	public void remove (String userId);
	
}
