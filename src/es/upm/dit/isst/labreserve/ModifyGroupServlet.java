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

import es.upm.dit.isst.labreserve.dao.GroupDAO;
import es.upm.dit.isst.labreserve.dao.GroupDAOImpl;
import es.upm.dit.isst.labreserve.dao.ResourceDAO;
import es.upm.dit.isst.labreserve.dao.ResourceDAOImpl;
import es.upm.dit.isst.labreserve.model.Group;
import es.upm.dit.isst.labreserve.model.Resource;


public class ModifyGroupServlet extends HttpServlet {

	private static final Long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		System.out.println("Updating resource ");
		User user = (User) req.getAttribute("user");
		if (user == null) {
			UserService userService = UserServiceFactory.getUserService();
			user = userService.getCurrentUser();
		}
		String name = checkNull(req.getParameter("name"));
		String[] resources = req.getParameterValues("resources");
		String description = checkNull(req.getParameter("description"));
	    Long id = Long.parseLong(req.getParameter("id"));
		List<Long> resourcesId = new ArrayList<Long>();
		
		if (name == ""){
			req.getSession().setAttribute("flashMessageError", "Nombre en blanco");
			RequestDispatcher view = req.getRequestDispatcher("CreateGroup.jsp");
	        view.forward(req, resp);
	        return;
		}
		
		if (description == ""){
			req.getSession().setAttribute("flashMessageError", "Descripcion en blanco");
			RequestDispatcher view = req.getRequestDispatcher("CreateGroup.jsp");
	        view.forward(req, resp);
	        return;
		}
		
		if (resources == null || resources.length <=0){
			req.getSession().setAttribute("flashMessageError", "Seleccione al menos un recurso");
			RequestDispatcher view = req.getRequestDispatcher("CreateGroup.jsp");
	        view.forward(req, resp);
	        return;
		}
		
		for (String res : resources) {
			Long resId = Long.parseLong(res);
			resourcesId.add(resId);
		}

		GroupDAO dao = GroupDAOImpl.getInstance();
		dao.update(id, name, resourcesId, description);
		req.getSession().setAttribute("flashMessageSuccess", "¡Grupo de recursos modificado!");
		resp.sendRedirect("/main");
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		ResourceDAO dao = ResourceDAOImpl.getInstance();
		GroupDAO groupDao = GroupDAOImpl.getInstance();
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();

		String url = userService.createLoginURL(req.getRequestURI());
		String urlLinktext = "Login";
	    Long id = Long.parseLong(req.getParameter("id"));
		List<Resource> resources = new ArrayList<Resource>();


	    Group group = groupDao.getGroup(id);
	
		if (user != null){
		    url = userService.createLogoutURL(req.getRequestURI());
		    urlLinktext = "Logout";
		    resources = dao.listResources();

		}
		
		req.getSession().setAttribute("user", user);
		req.getSession().setAttribute("group", group);
		req.getSession().setAttribute("resources", new ArrayList<Resource>(resources));
		req.getSession().setAttribute("flashMessageError", null);
		req.getSession().setAttribute("url", url);
		req.getSession().setAttribute("urlLinktext", urlLinktext);
		
		RequestDispatcher view = req.getRequestDispatcher("ModifyGroup.jsp");
        view.forward(req, resp);
		
	}
	private String checkNull(String s) {
		if (s == null) {
			return "";
		}
		return s;
	}
} 