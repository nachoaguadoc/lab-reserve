package es.upm.dit.isst.labreserve;

import java.io.IOException;
import java.util.Date;
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

import es.upm.dit.isst.labreserve.dao.AppUserDAO;
import es.upm.dit.isst.labreserve.dao.AppUserDAOImpl;
import es.upm.dit.isst.labreserve.dao.ConfigDAO;
import es.upm.dit.isst.labreserve.dao.ConfigDAOImpl;
import es.upm.dit.isst.labreserve.dao.RequestDAO;
import es.upm.dit.isst.labreserve.dao.RequestDAOImpl;
import es.upm.dit.isst.labreserve.dao.ReserveDAO;
import es.upm.dit.isst.labreserve.dao.ReserveDAOImpl;
import es.upm.dit.isst.labreserve.dao.ResourceDAO;
import es.upm.dit.isst.labreserve.dao.ResourceDAOImpl;
import es.upm.dit.isst.labreserve.model.AppUser;
import es.upm.dit.isst.labreserve.model.Request;
import es.upm.dit.isst.labreserve.model.Reserve;
import es.upm.dit.isst.labreserve.model.Resource;
public class SignUpServlet extends HttpServlet {
  private static final Long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
	
		AppUserDAO dao = AppUserDAOImpl.getInstance();
		RequestDAO requestDAO = RequestDAOImpl.getInstance();
		
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		
		String url = userService.createLoginURL(req.getRequestURI());
		String urlLinktext = "Login";
		AppUser appUser= null;
		Request request = null;
		
		if (user != null){
		    url = userService.createLogoutURL(req.getRequestURI());
		    urlLinktext = "Logout";
		    appUser = dao.getUser(user.getUserId());
		    request = requestDAO.getRequest(user.getUserId());
		}
		
		if (request != null) {
			req.getSession().setAttribute("request", "Su petición se está procesando");
		} else  {
			req.getSession().setAttribute("request", null);
		}
		req.getSession().setAttribute("flashMessageError", null);
		req.getSession().setAttribute("user", user);
		req.getSession().setAttribute("appUser", appUser);
		req.getSession().setAttribute("url", url);
		req.getSession().setAttribute("urlLinktext", urlLinktext);
		req.getSession().setAttribute("dateSelected", null);


		RequestDispatcher view = req.getRequestDispatcher("NewUser.jsp");
        view.forward(req, resp);
		
	}
	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		AppUserDAO dao = AppUserDAOImpl.getInstance();
		RequestDAO reqDAO = RequestDAOImpl.getInstance();
		
		User user = (User) req.getAttribute("user");
		if (user == null) {
			UserService userService = UserServiceFactory.getUserService();
			user = userService.getCurrentUser();
		}
		int oldPriority = dao.getUser(user.getUserId()).getPriority();
		int priority = Integer.parseInt(req.getParameter("priority"));
		String userId = user.getUserId();
		String email = user.getEmail();
		String name = checkNull(req.getParameter("name"));
		String desc = checkNull(req.getParameter("description"));
		Date date = new Date();
		
		if (priority != 1){
			if (reqDAO.getRequest(userId) != null) {
				reqDAO.update(userId, email, priority, desc, date);
			} else {
				reqDAO.add(userId, email, priority, desc, date);
			}
			
			if (dao.getUser(user.getUserId()) != null ){
				dao.update(user.getUserId(), oldPriority, name);
			} else {
				//Se le pone prioridad 1 hasta que el administrador acepte su petición
				dao.add(user.getUserId(), user.getEmail(), 1, name);
			}			
		} else {
			if (dao.getUser(user.getUserId()) != null ){
				dao.update(user.getUserId(), priority, name);
			} else {
				dao.add(user.getUserId(), user.getEmail(), priority, name);
			}
		}
		
		req.getSession().setAttribute("flashMessageSuccess", "¡Usuario creado!");
		resp.sendRedirect("/main");
	}
	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
} 

