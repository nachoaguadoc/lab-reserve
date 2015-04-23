package es.upm.dit.isst.labreserve.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import es.upm.dit.isst.labreserve.model.Movimiento;
import es.upm.dit.isst.labreserve.model.Reserve;
import es.upm.dit.isst.labreserve.model.Resource;

public class MovimientoDAOImpl implements MovimientoDAO {
	
	private static MovimientoDAOImpl instance;

	private MovimientoDAOImpl() {
	}

	public static MovimientoDAOImpl getInstance(){
		if (instance == null)
			instance = new MovimientoDAOImpl();
		return instance;
	}
	
	@Override
	public List<Movimiento> listMovimientos() {
		EntityManager em = EMFService.get().createEntityManager();
		// read the existing entries
		Query q = em.createQuery("select m from Movimiento m");
		List<Movimiento> movimientos = q.getResultList();
		return movimientos;
	}

	@Override
	public void add(Long resourceid, String date, int tipo) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			Movimiento movimiento = new Movimiento(resourceid, date, tipo);
			em.persist(movimiento);
			em.close();
		}
		
	}


	@Override
	public List<Movimiento> listMovimientos(String initdate, String finaldate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Movimiento> listMovimientos(String date) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select m from Movimiento m where m.date = :date");
		q.setParameter("date", date);
		List<Movimiento> movimientos = q.getResultList();
		return movimientos;
	}

	@Override
	public Movimiento getMovimiento(Long id) {
		EntityManager em = EMFService.get().createEntityManager();
		Movimiento movimiento = em.find(Movimiento.class, id);
		return movimiento;
	}

	@Override
	public void update(Long id, Long resourceid, String date, int tipo) {
		EntityManager em = EMFService.get().createEntityManager();
		Movimiento movimiento = em.find(Movimiento.class, id);
		movimiento.setResource(resourceid);
		movimiento.setDate(date);
		movimiento.setTipo(tipo);

		em.merge(movimiento);
		em.close();

	}
	
	@Override
	public void remove(Long id) {
		EntityManager em = EMFService.get().createEntityManager();
		try {
			Movimiento movimiento = em.find(Movimiento.class, id);
			em.remove(movimiento);
		} finally {
			em.close();
		}
	}

}