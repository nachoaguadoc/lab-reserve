package es.upm.dit.isst.labreserve;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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

import es.upm.dit.isst.labreserve.dao.ReserveDAO;
import es.upm.dit.isst.labreserve.dao.ReserveDAOImpl;
import es.upm.dit.isst.labreserve.dao.ResourceDAO;
import es.upm.dit.isst.labreserve.dao.ResourceDAOImpl;
import es.upm.dit.isst.labreserve.model.Reserve;
import es.upm.dit.isst.labreserve.model.Resource;

public class ReserveResourceServlet extends HttpServlet {
  private static final Long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		ResourceDAO dao = ResourceDAOImpl.getInstance();
		ReserveDAO resDao = ReserveDAOImpl.getInstance();
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		String url = userService.createLoginURL(req.getRequestURI());
		String urlLinktext = "Login";
		            
		if (user != null){
		    url = userService.createLogoutURL(req.getRequestURI());
		    urlLinktext = "Logout";
		}
	    Long id = Long.parseLong(req.getParameter("id"));
		Resource resource = dao.getResource(id);
		req.getSession().setAttribute("error", null);
		req.getSession().setAttribute("success", null);
		req.getSession().setAttribute("user", user);
		req.getSession().setAttribute("resource", resource);
		req.getSession().setAttribute("url", url);
		req.getSession().setAttribute("urlLinktext", urlLinktext);
		RequestDispatcher view = req.getRequestDispatcher("ReserveResource.jsp");
        view.forward(req, resp);
		
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		ResourceDAO dao = ResourceDAOImpl.getInstance();
		User user = (User) req.getAttribute("user");
		if (user == null) {
			UserService userService = UserServiceFactory.getUserService();
			user = userService.getCurrentUser();
		}
	    Long resourceID = Long.parseLong(req.getParameter("resourceID"));
	    String resourceName = dao.getResource(resourceID).getName();

	    
		String date = checkNull(req.getParameter("initDate"));
//		String finalDate = checkNull(req.getParameter("finalDate"));
		String initHour = checkNull(req.getParameter("initTime"));
		String finalHour = checkNull(req.getParameter("finalTime"));
		ReserveDAO resDao = ReserveDAOImpl.getInstance();
		
		if (resDao.isResourceReserved(resourceID, date, initHour, finalHour) ){
			System.out.println("Resource busy");
			req.getSession().setAttribute("error", "Recurso ocupado");
			RequestDispatcher view = req.getRequestDispatcher("ReserveResource.jsp");
	        view.forward(req, resp);
		} else {
			resDao.add(user.getUserId(), resourceName, resourceID, date, initHour, finalHour );
			req.getSession().setAttribute("success", "Resource reserved");
			resp.sendRedirect("/main");
		}
	}
	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
} 

