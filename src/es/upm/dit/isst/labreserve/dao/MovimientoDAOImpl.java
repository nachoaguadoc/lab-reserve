package es.upm.dit.isst.labreserve.dao;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
	public void add(Long resourceid, String resourcename, String date, int tipo) {
		synchronized (this) {
			EntityManager em = EMFService.get().createEntityManager();
			Movimiento movimiento = new Movimiento(resourceid, resourcename, date, tipo);
			em.persist(movimiento);
			em.close();
		}
		
	}


	@Override
	public List<Movimiento> listMovimientos(String mes, String año) {
		EntityManager em = EMFService.get().createEntityManager();
		// read the existing entries
		Query q = em.createQuery("select m from Movimiento m");
		List<Movimiento> movimientos = q.getResultList();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");	
		java.util.Date dateini, datefin, date;
		String ini, fin;
		java.util.Calendar cal = java.util.Calendar.getInstance();
		List<Movimiento> resultado = new ArrayList<Movimiento>();
				
		ini = ("01/" + mes+ "/" + año);	
		try {
			System.out.println("traduzco :" + ini);
		dateini = dateFormat.parse(ini);
		cal.setTime(dateini);
		//cal.add(java.util.Calendar.DATE, -1);
		dateini=cal.getTime();
		cal.add(java.util.Calendar.DATE, 30);
		datefin=cal.getTime();
		for(Movimiento m: movimientos){
			System.out.println("traduzco :" + m.getDate());
			String fecha= m.getDate();
			String ndia, nmes, naño;
			String[] trozos=fecha.split("/");
			if(trozos[0].length()==2){
				ndia=trozos[0];
			}else{
				ndia=("0" + trozos[0]);
			}
			if(trozos[1].length()==2){
				nmes=trozos[1];
			}else{
				nmes=("0" + trozos[1]);
			}
			if(trozos[2].length()==4){
				naño=trozos[2];
			}else{
				naño=("20" + trozos[2]);
			}
			fecha=(ndia+"/"+nmes+"/"+naño);
			date = dateFormat.parse(fecha);
			cal.setTime(date);
			date=cal.getTime();
		if(date.compareTo(dateini)>=0 && date.compareTo(datefin)<=0) {
			System.out.println("true");
			resultado.add(m);
		}
		}
		} catch (ParseException ex) {

		ex.printStackTrace();
		}		
		return resultado;
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
	public List<Movimiento> listMovimientos(String date, int tipo) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select m from Movimiento m where m.date = :date and m.tipo = :tipo");
		q.setParameter("date", date);
		q.setParameter("tipo", tipo);
		List<Movimiento> movimientos = q.getResultList();
		return movimientos;	}

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

	@Override
	public List<Movimiento> listMovimientos(Long resourceid) {
		EntityManager em = EMFService.get().createEntityManager();
		Query q = em.createQuery("select m from Movimiento m where m.resourceid = :resourceid");
		q.setParameter("resourceid", resourceid);
		List<Movimiento> movimientos = q.getResultList();
		return movimientos;
	}

	@Override
	public List<Movimiento> listMovimientosbyYear(String oyear) {
		System.out.println("oyear es:" + oyear);
		EntityManager em = EMFService.get().createEntityManager();
		// read the existing entries
		Query q = em.createQuery("select m from Movimiento m");
		List<Movimiento> movimientos = q.getResultList();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");	
		java.util.Date dateini, datefin, date;
		String ini, fin;
		java.util.Calendar cal = java.util.Calendar.getInstance();
		List<Movimiento> resultado = new ArrayList<Movimiento>();
				
		ini = ("01/01/" + oyear);	
		try {
			System.out.println("traduzco :" + ini);
		dateini = dateFormat.parse(ini);
		System.out.println("dateini antes:" + dateini.toString());
		cal.setTime(dateini);
		dateini=cal.getTime();
		System.out.println("dateini :" + dateini.toString());
		cal.add(java.util.Calendar.DATE, 365);
		datefin=cal.getTime();
		System.out.println("datefin :" + datefin.toString());
		for(Movimiento m: movimientos){
			System.out.println("traduzco :" + m.getDate());
			String fecha= m.getDate();
			String ndia, nmes, naño;
			String[] trozos=fecha.split("/");
			System.out.println("trozo0 :" + trozos[0] + " 1: " + trozos[1] + " 2: " + trozos[2]);
			if(trozos[0].length()==2){
				ndia=trozos[0];
			}else{
				ndia=("0" + trozos[0]);
			}
			if(trozos[1].length()==2){
				nmes=trozos[1];
			}else{
				nmes=("0" + trozos[1]);
			}
			if(trozos[2].length()==4){
				naño=trozos[2];
			}else{
				naño=("20" + trozos[2]);
			}
			fecha=(ndia+"/"+nmes+"/"+naño);
			System.out.println("traduzco :" + fecha);
			date = dateFormat.parse(fecha);
			cal.setTime(date);
			date=cal.getTime();
			System.out.println("sale :" + date.toString()+(date.compareTo(dateini))+(date.compareTo(datefin)));
		if(date.compareTo(dateini)>=0 && date.compareTo(datefin)<=0) {
			System.out.println("true");
			resultado.add(m);
		}
		}
		} catch (ParseException ex) {

		ex.printStackTrace();
		}		
		return resultado;
	}

}