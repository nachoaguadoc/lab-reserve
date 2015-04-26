package es.upm.dit.isst.labreserve;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import es.upm.dit.isst.labreserve.dao.MovimientoDAO;
import es.upm.dit.isst.labreserve.dao.MovimientoDAOImpl;
import es.upm.dit.isst.labreserve.dao.RequestDAO;
import es.upm.dit.isst.labreserve.dao.RequestDAOImpl;
import es.upm.dit.isst.labreserve.dao.ReserveDAO;
import es.upm.dit.isst.labreserve.dao.ReserveDAOImpl;
import es.upm.dit.isst.labreserve.model.Movimiento;
import es.upm.dit.isst.labreserve.model.Request;
import es.upm.dit.isst.labreserve.dao.ResourceDAO;
import es.upm.dit.isst.labreserve.dao.ResourceDAOImpl;
import es.upm.dit.isst.labreserve.model.Resource;


public class CheckStatsServlet extends HttpServlet {
  private static final Long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		MovimientoDAO dao = MovimientoDAOImpl.getInstance();
		ResourceDAO resourceDAO = ResourceDAOImpl.getInstance();
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		String url = userService.createLoginURL(req.getRequestURI());
		String urlLinktext = "Login";
		            
		if (user != null){
		    url = userService.createLogoutURL(req.getRequestURI());
		    urlLinktext = "Logout";
		}

		List<Movimiento> movimientos = dao.listMovimientos();
		List<Resource> resources = new ArrayList<Resource>();
		resources = resourceDAO.listResources();
		req.getSession().setAttribute("movimientos", new ArrayList<Movimiento>(movimientos));
		req.getSession().setAttribute("user", user);
		req.getSession().setAttribute("url", url);
		req.getSession().setAttribute("urlLinktext", urlLinktext);
		req.getSession().setAttribute("resources", new ArrayList<Resource>(resources));
		
	    Object counter = req.getSession().getAttribute("Count");
		if (counter == null) {
			req.getSession().setAttribute("Count", 2);
		} else {
			if (req.getSession().getAttribute("flashMessageSuccess") != null){
				int count = (Integer) counter;
				count--;
			    if (count == 0) {
			        count = 2;
			        req.getSession().setAttribute("Count", count);
					req.getSession().setAttribute("flashMessageSuccess", null);
			    } else {
			    	req.getSession().setAttribute("Count", count);
			    }
			} else {
		    	req.getSession().setAttribute("Count", 2);

			}
		}
		
		RequestDispatcher view = req.getRequestDispatcher("CheckStats.jsp");
        view.forward(req, resp);
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		ResourceDAO resourceDAO = ResourceDAOImpl.getInstance();
		ReserveDAO reserveDAO = ReserveDAOImpl.getInstance();
		MovimientoDAO movimientoDAO = MovimientoDAOImpl.getInstance();
		List<Resource> resources = new ArrayList<Resource>();
		resources = resourceDAO.listResources();

		User user = (User) req.getAttribute("user");
		if (user == null) {
			UserService userService = UserServiceFactory.getUserService();
			user = userService.getCurrentUser();
		}
	    
	    String type = req.getParameter("type");
	    req.getSession().setAttribute("flashMessageError", null);
	    if (type.equals("month")){
			String month = checkNull(req.getParameter("month"));
			String year = checkNull(req.getParameter("year"));
			String fecha=(month+"/"+year);
			List<Movimiento> movimientos = movimientoDAO.listMovimientos(month, year);
			int tipo1=0;
	    	int tipo2=0;
	    	int tipo3=0;
	    	for(Movimiento m: movimientos){
	    		if(m.getTipo()==1){
	    			tipo1++;
	    	    }else if (m.getTipo()==2){
	    	    	tipo2++;
	    	    }else if (m.getTipo()==3){
	    	    	tipo3++;
	    	    }
	    	}
	    	req.getSession().setAttribute("tipo1", tipo1);
	    	req.getSession().setAttribute("tipo2", tipo2);
	    	req.getSession().setAttribute("tipo3", tipo3);
	    	req.getSession().setAttribute("listadia", null);
	    	req.getSession().setAttribute("listames", new ArrayList(movimientos));
	    	req.getSession().setAttribute("listayear", null);
	    	req.getSession().setAttribute("listarecurso", null);
	    	req.getSession().setAttribute("fecha", fecha);
	    	req.getSession().setAttribute("nombrerecurso", null);
			resp.sendRedirect("/stats");

	    } else if (type.equals("year")) {
	    	String syear = checkNull(req.getParameter("syear"));
	    	List<Movimiento> movimientos = movimientoDAO.listMovimientosbyYear(syear);
	    	int tipo1=0;
	    	int tipo2=0;
	    	int tipo3=0;
	    	for(Movimiento m: movimientos){
	    		if(m.getTipo()==1){
	    			tipo1++;
	    	    }else if (m.getTipo()==2){
	    	    	tipo2++;
	    	    }else if (m.getTipo()==3){
	    	    	tipo3++;
	    	    }
	    	}
	    	req.getSession().setAttribute("tipo1", tipo1);
	    	req.getSession().setAttribute("tipo2", tipo2);
	    	req.getSession().setAttribute("tipo3", tipo3);
	    	req.getSession().setAttribute("listadia", null);
	    	req.getSession().setAttribute("listames", null);
	    	req.getSession().setAttribute("listayear", new ArrayList(movimientos));
	    	req.getSession().setAttribute("listarecurso", null);
	    	req.getSession().setAttribute("year", syear);
	    	req.getSession().setAttribute("nombrerecurso", null);
			resp.sendRedirect("/stats");
			
	    }else if (type.equals("day")) {
	    	String date = checkNull(req.getParameter("consultDate"));
	    	if(date==""){
	    		req.getSession().setAttribute("flashMessageError", "Fecha incorrecta");
				RequestDispatcher view = req.getRequestDispatcher("CheckStats.jsp");
		        view.forward(req, resp);
		        return;
	    	}
	    	List<Movimiento> movimientos = movimientoDAO.listMovimientos(date);
	    	int tipo1=0;
	    	int tipo2=0;
	    	int tipo3=0;
	    	for(Movimiento m: movimientos){
	    		if(m.getTipo()==1){
	    			tipo1++;
	    	    }else if (m.getTipo()==2){
	    	    	tipo2++;
	    	    }else if (m.getTipo()==3){
	    	    	tipo3++;
	    	    }
	    	}
	    	req.getSession().setAttribute("tipo1", tipo1);
	    	req.getSession().setAttribute("tipo2", tipo2);
	    	req.getSession().setAttribute("tipo3", tipo3);
	    	req.getSession().setAttribute("listadia", new ArrayList(movimientos));
	    	req.getSession().setAttribute("listames", null);
	    	req.getSession().setAttribute("listayear", null);
	    	req.getSession().setAttribute("listarecurso", null);
	    	req.getSession().setAttribute("dia", date);
	    	req.getSession().setAttribute("nombrerecurso", null);
			resp.sendRedirect("/stats");
			
	    } else if (type.equals("resources")) {
	    	Long recursoid = Long.parseLong(req.getParameter("recurso"));
	    	String nombrerecurso = resourceDAO.getResource(recursoid).getName() ;
	    	List<Movimiento> movimientos = movimientoDAO.listMovimientos(recursoid);
	    	int tipo1=0;
	    	int tipo2=0;
	    	for(Movimiento m: movimientos){
	    		if(m.getTipo()==1){
	    			tipo1++;
	    	    }else if (m.getTipo()==2){
	    	    	tipo2++;
	    	    }
	    	}
	    	req.getSession().setAttribute("tipo1", tipo1);
	    	req.getSession().setAttribute("tipo2", tipo2);
	    	req.getSession().setAttribute("listadia", null);
	    	req.getSession().setAttribute("listames", null);
	    	req.getSession().setAttribute("listayear", null);
	    	req.getSession().setAttribute("listarecurso", new ArrayList(movimientos));
	    	req.getSession().setAttribute("dia", null);
	    	req.getSession().setAttribute("nombrerecurso", nombrerecurso);
			resp.sendRedirect("/stats");
	    }else{
		    req.getSession().setAttribute("flashMessageError", "Tipo incorrecto");
			RequestDispatcher view = req.getRequestDispatcher("CheckStats.jsp");
	        view.forward(req, resp);
	        return;
		    }
	    resp.sendRedirect("/stats");
	}

	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
} 
