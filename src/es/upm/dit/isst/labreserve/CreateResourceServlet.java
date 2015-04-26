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

public class CreateResourceServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		System.out.println("Creating new resource ");
		User user = (User) req.getAttribute("user");
		if (user == null) {
			UserService userService = UserServiceFactory.getUserService();
			user = userService.getCurrentUser();
		}

		String name = checkNull(req.getParameter("name"));
		String state = checkNull(req.getParameter("state"));
		String description = checkNull(req.getParameter("description"));

		if (name == ""){
			req.getSession().setAttribute("flashMessageError", "Nombre en blanco");
			RequestDispatcher view = req.getRequestDispatcher("CreateResource.jsp");
	        view.forward(req, resp);
	        return;
		}
		
		if (state == ""){
			req.getSession().setAttribute("flashMessageError", "Estado en blanco");
			RequestDispatcher view = req.getRequestDispatcher("CreateResource.jsp");
	        view.forward(req, resp);
	        return;
		}

		
		if (description == ""){
			req.getSession().setAttribute("flashMessageError", "Descripciónn en blanco");
			RequestDispatcher view = req.getRequestDispatcher("CreateResource.jsp");
	        view.forward(req, resp);
	        return;
		}


		ResourceDAO dao = ResourceDAOImpl.getInstance();
		dao.add(name, state, description);
		req.getSession().setAttribute("flashMessageSuccess", "¡Recurso creado!");
		resp.sendRedirect("/main");
	}
	
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		ResourceDAO dao = ResourceDAOImpl.getInstance();

		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		String url = userService.createLoginURL(req.getRequestURI());
		String urlLinktext = "Login";
		List<Resource> resources = new ArrayList<Resource>();
		            
		if (user != null){
		    url = userService.createLogoutURL(req.getRequestURI());
		    urlLinktext = "Logout";
		    resources = dao.listResources();
		}
		
		req.getSession().setAttribute("user", user);
		req.getSession().setAttribute("resources", new ArrayList<Resource>(resources));
		req.getSession().setAttribute("url", url);
		req.getSession().setAttribute("urlLinktext", urlLinktext);
		req.getSession().setAttribute("flashMessageError", null);

		RequestDispatcher view = req.getRequestDispatcher("CreateResource.jsp");
        view.forward(req, resp);
		
	}


	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
} 