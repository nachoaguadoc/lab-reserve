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
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		String url = userService.createLoginURL(req.getRequestURI());
		String urlLinktext = "Login";
		            
		if (user != null){
		    url = userService.createLogoutURL(req.getRequestURI());
		    urlLinktext = "Logout";
		}

		List<Movimiento> movimientos = dao.listMovimientos();
		req.getSession().setAttribute("movimientos", new ArrayList<Movimiento>(movimientos));
		req.getSession().setAttribute("user", user);
		req.getSession().setAttribute("url", url);
		req.getSession().setAttribute("urlLinktext", urlLinktext);
		
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

		User user = (User) req.getAttribute("user");
		if (user == null) {
			UserService userService = UserServiceFactory.getUserService();
			user = userService.getCurrentUser();
		}
	    
	    String type = req.getParameter("type");
	    if (type.equals("month")){
			String month = checkNull(req.getParameter("month"));
			String year = checkNull(req.getParameter("year"));
			resp.sendRedirect("/stats");

	    } else {
	    	String date = checkNull(req.getParameter("consultDate"));
	    	List<Movimiento> movimientos = movimientoDAO.listMovimientos(date);
	    	List<Movimiento> movtotal = movimientoDAO.listMovimientos();
	    	System.out.println("movimientos dia " + date +  " = " + movimientos.size() + " de un total = " + movtotal.size());
	    	req.getSession().setAttribute("lista", new ArrayList(movimientos));
			resp.sendRedirect("/stats");
	    }
	}

	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
} 
