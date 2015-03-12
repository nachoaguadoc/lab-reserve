package es.upm.dit.isst.labreserve.dao;

import java.util.Calendar;
import java.util.List;

import es.upm.dit.isst.labreserve.model.Reserve;

public interface ReserveDAO {

	public List<Reserve> listReserves();
	
	public void add (String author, Long resourceID, String Date);
	
	public void update (Long id, Long resourceID, String Date);
	
	public Reserve getReserve(Long id);

	public void remove (Long id);
		
	public List<Reserve> listAuthorReserves(String author);
	
	//public boolean isResourceBusy(Long id, String Date);
	
}
