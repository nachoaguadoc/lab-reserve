package es.upm.dit.isst.labreserve.dao;

import java.util.Calendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.upm.dit.isst.labreserve.model.Reserve;
import es.upm.dit.isst.labreserve.model.Resource;

public class ReserveDAOImpl implements ReserveDAO {
	
	private static ReserveDAOImpl instance;

	private ReserveDAOImpl() {
	}
	
	public static ReserveDAOImpl getInstance(){
		if (instance == null)
			instance = new ReserveDAOImpl();
		return instance;
	}
	
	@Override
	public List<Reserve> listReserves() {
		EntityManager em = EMFService.get().createEntityManager();
		// read the existing entries
		Query q = em.createQuery("select m from Reserve m");
		List<Reserve> reserves = q.getResultList();
		return reserves;
	}

	@Override
	public void add(String author, Long resourceID, String date) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			Reserve reserve = new Reserve(author, resourceID, date);
			em.persist(reserve);
			em.close();
		}

	}

	@Override
	public void update(Long id, Long resourceID, String Date) {
		EntityManager em = EMFService.get().createEntityManager();
		Reserve reserve = em.find(Reserve.class, id);
		reserve.setResourceID(resourceID);
		reserve.setDate(Date);

		em.merge(reserve);
		em.close();

	}

	@Override
	public Reserve getReserve(Long id) {
		EntityManager em = EMFService.get().createEntityManager();
		Reserve reserve = em.find(Reserve.class, id);
		return reserve;
		
	}

	@Override
	public void remove(Long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Reserve reserve = em.find(Reserve.class, id);
			em.remove(reserve);
		} finally {
			em.close();
		}
	}

	@Override
	public List<Reserve> listAuthorReserves(String author) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select r from Reserve r where r.author = :author");
		q.setParameter("author", author);
		List<Reserve> reserves = q.getResultList();
		return reserves;
	}

}
