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

import es.upm.dit.isst.labreserve.dao.AppUserDAO;
import es.upm.dit.isst.labreserve.dao.AppUserDAOImpl;
import es.upm.dit.isst.labreserve.dao.ConfigDAO;
import es.upm.dit.isst.labreserve.dao.ConfigDAOImpl;
import es.upm.dit.isst.labreserve.dao.GroupDAO;
import es.upm.dit.isst.labreserve.dao.GroupDAOImpl;
import es.upm.dit.isst.labreserve.dao.MovimientoDAO;
import es.upm.dit.isst.labreserve.dao.MovimientoDAOImpl;
import es.upm.dit.isst.labreserve.dao.RequestDAO;
import es.upm.dit.isst.labreserve.dao.RequestDAOImpl;
import es.upm.dit.isst.labreserve.dao.ReserveDAO;
import es.upm.dit.isst.labreserve.dao.ReserveDAOImpl;
import es.upm.dit.isst.labreserve.dao.ResourceDAO;
import es.upm.dit.isst.labreserve.dao.ResourceDAOImpl;
import es.upm.dit.isst.labreserve.model.Request;


public class RemoveAllTest extends HttpServlet {
  private static final Long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		ReserveDAO reserveDAO = ReserveDAOImpl.getInstance();
		ResourceDAO resourceDAO = ResourceDAOImpl.getInstance();
		AppUserDAO userDAO = AppUserDAOImpl.getInstance();
		GroupDAO groupDAO = GroupDAOImpl.getInstance();
		RequestDAO requestDAO = RequestDAOImpl.getInstance();
		ConfigDAO configDAO = ConfigDAOImpl.getInstance();
		MovimientoDAO movimientoDAO = MovimientoDAOImpl.getInstance();
		
		reserveDAO.removeAll();
		resourceDAO.removeAll();
		userDAO.removeAll();
		groupDAO.removeAll();
		requestDAO.removeAll();
		movimientoDAO.removeAll();
		configDAO.remove("global");
		
		
		resp.sendRedirect("/main");
		
		RequestDispatcher view = req.getRequestDispatcher("CheckRequests.jsp");
        view.forward(req, resp);
		
	}

} 

