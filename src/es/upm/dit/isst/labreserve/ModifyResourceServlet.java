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

import es.upm.dit.isst.labreserve.dao.ResourceDAO;
import es.upm.dit.isst.labreserve.dao.ResourceDAOImpl;
import es.upm.dit.isst.labreserve.model.Resource;

public class ModifyResourceServlet extends HttpServlet {

	private static final Long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		System.out.println("Updating resource ");
		User user = (User) req.getAttribute("user");
		if (user == null) {
			UserService userService = UserServiceFactory.getUserService();
			user = userService.getCurrentUser();
		}
	    Long id = Long.parseLong(req.getParameter("id"));
		String name = checkNull(req.getParameter("name"));
		String state = checkNull(req.getParameter("state"));
		String description = checkNull(req.getParameter("description"));

		if (name == ""){
			req.getSession().setAttribute("flashMessageError", "Nombre en blanco");
			RequestDispatcher view = req.getRequestDispatcher("ModifyResource.jsp");
	        view.forward(req, resp);
	        return;
		}
		
		if (state == ""){
			req.getSession().setAttribute("flashMessageError", "Estado en blanco");
			RequestDispatcher view = req.getRequestDispatcher("ModifyResource.jsp");
	        view.forward(req, resp);
	        return;
		}

		
		if (description == ""){
			req.getSession().setAttribute("flashMessageError", "Descripción en blanco");
			RequestDispatcher view = req.getRequestDispatcher("ModifyResource.jsp");
	        view.forward(req, resp);
	        return;
		}

		ResourceDAO dao = ResourceDAOImpl.getInstance();
		dao.update(id, name, state, description);
		req.getSession().setAttribute("flashMessageSuccess", "Â¡Recurso modificado!");
		resp.sendRedirect("/main");
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		ResourceDAO dao = ResourceDAOImpl.getInstance();

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		String url = userService.createLoginURL(req.getRequestURI());
		String urlLinktext = "Login";
	    Long id = Long.parseLong(req.getParameter("id"));

		Resource resource = dao.getResource(id);

		
		if (user != null){
		    url = userService.createLogoutURL(req.getRequestURI());
		    urlLinktext = "Logout";
		}
		
		req.getSession().setAttribute("user", user);
		req.getSession().setAttribute("flashMessageError", null);
		req.getSession().setAttribute("resource", resource);
		req.getSession().setAttribute("url", url);
		req.getSession().setAttribute("urlLinktext", urlLinktext);
		
		RequestDispatcher view = req.getRequestDispatcher("ModifyResource.jsp");
        view.forward(req, resp);
		
	}
	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
} 