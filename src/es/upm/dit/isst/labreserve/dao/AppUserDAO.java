package es.upm.dit.isst.labreserve.dao;

import java.util.List;

import es.upm.dit.isst.labreserve.model.AppUser;
import es.upm.dit.isst.labreserve.model.Resource;

public interface AppUserDAO {
	
	public List<AppUser> listUsers();
	
	public void add (String id, String email, int priority);
	
	public void update (String id, int priority);

	public AppUser getUser(String id);
	
	public void remove (String id);
	
}
