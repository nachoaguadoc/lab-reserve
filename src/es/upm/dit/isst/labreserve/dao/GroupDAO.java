package es.upm.dit.isst.labreserve.dao;

import java.util.List;

import es.upm.dit.isst.labreserve.model.Group;

public interface GroupDAO {

	public List<Group> listGroups();
	
	public void add (String name, List<Long> resources, String description);
	
	public void update (Long id, String name, List<Long> resources, String description);
	
	public Group getGroup(Long groupId);

	public void remove (Long id);
			
}
