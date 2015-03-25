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

import es.upm.dit.isst.labreserve.dao.ReserveDAO;
import es.upm.dit.isst.labreserve.dao.ReserveDAOImpl;
import es.upm.dit.isst.labreserve.model.Reserve;

public class ModifyReserveServlet extends HttpServlet {

	private static final Long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		System.out.println("Updating resource ");
		ReserveDAO dao = ReserveDAOImpl.getInstance();

		User user = (User) req.getAttribute("user");
		if (user == null) {
			UserService userService = UserServiceFactory.getUserService();
			user = userService.getCurrentUser();
		}
		String date = checkNull(req.getParameter("initDate"));
//		String finalDate = checkNull(req.getParameter("finalDate"));
		String initHour = checkNull(req.getParameter("initTime"));
		String finalHour = checkNull(req.getParameter("finalTime"));
	    Long resourceID = Long.parseLong(req.getParameter("resourceID"));
	    String resourceName = checkNull(req.getParameter("resourceName"));
	    Long id = Long.parseLong(req.getParameter("id"));

	    
		if (dao.isResourceReserved(resourceID, date, initHour, finalHour) ){
			System.out.println("Resource busy");
			req.getSession().setAttribute("flashMessageError", "Recurso Ocupado");

			RequestDispatcher view = req.getRequestDispatcher("ModifyReserve.jsp");
	        view.forward(req, resp);
		} else {
			dao.update(id, resourceName, resourceID,  date, initHour, finalHour);
			req.getSession().setAttribute("flashMessageSuccess", "¡Reserva modificada!");
			resp.sendRedirect("/main");
		}
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		ReserveDAO dao = ReserveDAOImpl.getInstance();

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		String url = userService.createLoginURL(req.getRequestURI());
		String urlLinktext = "Login";
	    Long id = Long.parseLong(req.getParameter("id"));

		Reserve reserve = dao.getReserve(id);

		
		if (user != null){
		    url = userService.createLogoutURL(req.getRequestURI());
		    urlLinktext = "Logout";
		}
		
		req.getSession().setAttribute("user", user);
		req.getSession().setAttribute("reserve", reserve);
		req.getSession().setAttribute("url", url);
		req.getSession().setAttribute("urlLinktext", urlLinktext);
		req.getSession().setAttribute("flashMessageError", null);

		
		RequestDispatcher view = req.getRequestDispatcher("ModifyReserve.jsp");
        view.forward(req, resp);
		
	}
	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
} 