package es.upm.dit.isst.labreserve.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.upm.dit.isst.labreserve.model.AppUser;
import es.upm.dit.isst.labreserve.model.Config;
import es.upm.dit.isst.labreserve.model.Request;
import es.upm.dit.isst.labreserve.model.Reserve;

public class AppUserDAOImpl implements AppUserDAO {
	
	private static AppUserDAOImpl instance;

	private AppUserDAOImpl() {
	}
	
	public static AppUserDAOImpl getInstance(){
		if (instance == null)
			instance = new AppUserDAOImpl();
		return instance;
	}
	
	@Override
	public List<AppUser> listUsers() {
		EntityManager em = EMFService.get().createEntityManager();
		// read the existing entries
		Query q = em.createQuery("select m from AppUser m");
		List<AppUser> users = q.getResultList();
		return users;
	}

	@Override
	public void add(String id, String email, int priority, String name) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			AppUser user = new AppUser(id, email, priority, name);
			em.persist(user);
			em.close();
		}
	}

	@Override
	public void update(String id, int priority, String name) {
		EntityManager em = EMFService.get().createEntityManager();
		AppUser user = em.find(AppUser.class, id);
		user.setPriority(priority);
		user.setName(name);
		em.merge(user);
		em.close();
	}

	@Override
	public AppUser getUser(String id) {
		EntityManager em = EMFService.get().createEntityManager();
		AppUser user = em.find(AppUser.class, id);
		return user;
	}

	@Override
	public void remove(String id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			AppUser user = em.find(AppUser.class, id);
			em.remove(user);
		} finally {
			em.close();
		}
	}
	@Override
	public void removeAll() {
		EntityManager em = EMFService.get().createEntityManager();
		em.createQuery("DELETE FROM " + AppUser.class.getName() + " m").executeUpdate(); 	    
	    em.close();
	}
}
