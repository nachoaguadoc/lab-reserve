package es.upm.dit.isst.labreserve.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.upm.dit.isst.labreserve.model.Group;
import es.upm.dit.isst.labreserve.model.Resource;

public class GroupDAOImpl implements GroupDAO {
	private static GroupDAOImpl instance;

	private GroupDAOImpl() {
	}
	
	public static GroupDAOImpl getInstance(){
		if (instance == null)
			instance = new GroupDAOImpl();
		return instance;
	}
	@Override
	public List<Group> listGroups() {
		EntityManager em = EMFService.get().createEntityManager();
		// read the existing entries
		Query q = em.createQuery("select m from Group m");
		List<Group> groups = q.getResultList();
		return groups;
	}

	@Override
	public void add(String name, List<Long> resources, String description) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			Group group = new Group(name, resources, description);
			em.persist(group);
			em.close();
		}
	}

	@Override
	public void update(Long id, String name, List<Long> resources, String description) {
		EntityManager em = EMFService.get().createEntityManager();
		Group group = em.find(Group.class, id);
		group.setName(name);
		group.setResources(resources);
		group.setDescription(description);
		em.merge(group);
		em.close();
	}

	@Override
	public Group getGroup(Long groupId) {
		EntityManager em = EMFService.get().createEntityManager();
		Group group = em.find(Group.class, groupId);
		return group;
	}

	@Override
	public void remove(Long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Group group = em.find(Group.class, id);
			em.remove(group);
		} finally {
			em.close();
		}
	}

}
