package es.upm.dit.isst.labreserve;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import es.upm.dit.isst.labreserve.dao.ConfigDAO;
import es.upm.dit.isst.labreserve.dao.ConfigDAOImpl;
import es.upm.dit.isst.labreserve.dao.ReserveDAO;
import es.upm.dit.isst.labreserve.dao.ReserveDAOImpl;
import es.upm.dit.isst.labreserve.dao.ResourceDAO;
import es.upm.dit.isst.labreserve.dao.ResourceDAOImpl;
import es.upm.dit.isst.labreserve.model.Reserve;
import es.upm.dit.isst.labreserve.model.Resource;
public class SetConfigServlet extends HttpServlet {
  private static final Long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
	
		ConfigDAO dao = ConfigDAOImpl.getInstance();
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		String url = userService.createLoginURL(req.getRequestURI());
		String urlLinktext = "Login";
		            
		if (user != null){
		    url = userService.createLogoutURL(req.getRequestURI());
		    urlLinktext = "Logout";
		}
				
		req.getSession().setAttribute("flashMessageError", null);
		req.getSession().setAttribute("user", user);
		req.getSession().setAttribute("url", url);
		req.getSession().setAttribute("urlLinktext", urlLinktext);
		req.getSession().setAttribute("dateSelected", null);


		RequestDispatcher view = req.getRequestDispatcher("SetConfig.jsp");
        view.forward(req, resp);
		
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		ConfigDAO dao = ConfigDAOImpl.getInstance();
		User user = (User) req.getAttribute("user");
		if (user == null) {
			UserService userService = UserServiceFactory.getUserService();
			user = userService.getCurrentUser();
		}


	    
		String webmaster = checkNull(req.getParameter("webmaster"));
		String sessionTime = checkNull(req.getParameter("sessionTime"));
		String opening = checkNull(req.getParameter("opening"));
		String closing = checkNull(req.getParameter("closing"));
		
		DateFormat dateFormat = new SimpleDateFormat ("hh:mm");	
		java.util.Date horaini, horafin;
		
		if (webmaster == ""){
			req.getSession().setAttribute("flashMessageError", "Correo del administrador en blanco");
			RequestDispatcher view = req.getRequestDispatcher("SetConfig.jsp");
	        view.forward(req, resp);
	        return;
		}
		
		try{
			horaini = dateFormat.parse(opening);
			horafin = dateFormat.parse(closing);
			if(horaini.compareTo(horafin) >= 0){
				req.getSession().setAttribute("flashMessageError", "Horario incorrecto");
				RequestDispatcher view = req.getRequestDispatcher("SetConfig.jsp");
			    view.forward(req, resp);
			    return;
			}
			
		}catch(Exception parseException){
			req.getSession().setAttribute("flashMessageError", "Horario incorrecto");
			RequestDispatcher view = req.getRequestDispatcher("SetConfig.jsp");
		    view.forward(req, resp);
		    return;
		}
		
		if (dao.getConfig("global") != null ){
			dao.update("global", webmaster, sessionTime, opening, closing);
		} else {
			dao.add("global", webmaster, sessionTime, opening, closing);
		}
		req.getSession().setAttribute("flashMessageSuccess", "¡Parámetros de configuración fijados!");
		resp.sendRedirect("/main");
	}
	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
} 

