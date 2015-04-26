package es.upm.dit.isst.labreserve.dao;

import java.util.List;

import es.upm.dit.isst.labreserve.model.Movimiento;
import es.upm.dit.isst.labreserve.model.Reserve;
import es.upm.dit.isst.labreserve.model.Resource;

public interface MovimientoDAO {
	
	public List<Movimiento> listMovimientos();
	
	public void add (Long resourceid, String resourcename, String date, int movimiento);
	
	public List<Movimiento> listMovimientos(String date);
	
	public List<Movimiento> listMovimientosbyYear(String date);
	
	public List<Movimiento> listMovimientos(String mes, String year);
	
	public List<Movimiento> listMovimientos(String date, int tipo);
	
	public List<Movimiento> listMovimientos(Long resourceid);
	
	public Movimiento getMovimiento(Long id);
	
	public void update(Long id, Long resourceid, String date, int tipo);
	
	public void remove (Long id);
	
}
