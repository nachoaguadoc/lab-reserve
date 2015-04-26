package es.upm.dit.isst.labreserve;

import java.io.IOException;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.upm.dit.isst.labreserve.dao.AppUserDAO;
import es.upm.dit.isst.labreserve.dao.AppUserDAOImpl;
import es.upm.dit.isst.labreserve.dao.ReserveDAO;
import es.upm.dit.isst.labreserve.dao.ReserveDAOImpl;
import es.upm.dit.isst.labreserve.dao.ResourceDAO;
import es.upm.dit.isst.labreserve.dao.ResourceDAOImpl;
import es.upm.dit.isst.labreserve.dao.MovimientoDAO;
import es.upm.dit.isst.labreserve.dao.MovimientoDAOImpl;
import es.upm.dit.isst.labreserve.model.AppUser;
import es.upm.dit.isst.labreserve.model.Reserve;
import es.upm.dit.isst.labreserve.model.Resource;
import es.upm.dit.isst.labreserve.model.Movimiento;

public class CancelReserveServlet extends HttpServlet {
  private static final Long serialVersionUID = 1L;

  public void doPost(HttpServletRequest req, HttpServletResponse resp)
  throws IOException {
	System.out.println("Canceling reserve ");

    String id = req.getParameter("id");
    ReserveDAO dao = ReserveDAOImpl.getInstance();
    AppUserDAO userDAO = AppUserDAOImpl.getInstance();
    ResourceDAO resourceDAO = ResourceDAOImpl.getInstance();
    MovimientoDAO movimientoDAO = MovimientoDAOImpl.getInstance();
    
	Reserve reserve = dao.getReserve(Long.parseLong(id));
	AppUser userRes = userDAO.getUser(reserve.getAuthor());
	Long resourceID = reserve.getResourceID();
	Resource resource = resourceDAO.getResource(resourceID);
	
	if (resource == null){
		dao.remove(Long.parseLong(id), "", "", "");
		resp.sendRedirect("/main");
	}
	
	String date = reserve.getDate();
	String initHour = reserve.getInitHour();
	String finalHour = reserve.getFinalHour();
	String name = userRes.getName();
	String resourceName = resource.getName();
	if (name == null || name.equals("")){
		name = "usuario";
	}
	  
	Properties props = new Properties();
	Session session = Session.getDefaultInstance(props, null);
	String to = userRes.getEmail();
	String body = "Estimado " + name + ":" + "\n\n" + "Su reserva con fecha " + date + " de " + initHour + " a " + finalHour + " del recurso '" + resourceName + "' ha sido cancelada." +  "\n\n" + "Si usted no la ha cancelado, por favor, realice una nueva reserva." + "\n\n" + "Disculpe las molestias." + "\n\n" +  "LabReserve Team";
	dao.remove(Long.parseLong(id), to, body, name);
	movimientoDAO.add(resourceID, resourceName, date, 2);
	req.getSession().setAttribute("flashMessageSuccess", "¡Reserva cancelada!");
	resp.sendRedirect("/main");

  }
} 
