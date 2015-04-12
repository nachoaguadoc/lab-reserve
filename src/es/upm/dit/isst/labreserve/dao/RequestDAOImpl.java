package es.upm.dit.isst.labreserve.dao;

import java.util.Date;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.upm.dit.isst.labreserve.model.AppUser;
import es.upm.dit.isst.labreserve.model.Request;
import es.upm.dit.isst.labreserve.model.Reserve;

public class RequestDAOImpl implements RequestDAO {

	private static RequestDAOImpl instance;

	private RequestDAOImpl() {
	}
	
	public static RequestDAOImpl getInstance(){
		if (instance == null)
			instance = new RequestDAOImpl();
		return instance;
	}
	
	@Override
	public List<Request> listRequests() {
		EntityManager em = EMFService.get().createEntityManager();
		// read the existing entries
		Query q = em.createQuery("select m from Request m");
		List<Request> requests = q.getResultList();
		return requests;
	}

	@Override
	public void add(String userId, String email, int priority, String desc,
			Date date) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			Request request = new Request(userId, email, priority, desc, date);
			em.persist(request);
			em.close();
		}
	}

	@Override
	public void update(String userId, String email, int priority,
			String desc, Date date) {
		EntityManager em = EMFService.get().createEntityManager();
		Request request = em.find(Request.class, userId);
		request.setPriority(priority);
		request.setDesc(desc);
		request.setDate(date);
		em.merge(request);
		em.close();
	}

	@Override
	public Request getRequest(String userId) {
		EntityManager em = EMFService.get().createEntityManager();
		Request request = em.find(Request.class, userId);
		return request;
	}

	@Override
	public void remove(String userId) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Request request = em.find(Request.class, userId);
			em.remove(request);
		} finally {
			em.close();
		}
	}

}
