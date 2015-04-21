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
import es.upm.dit.isst.labreserve.model.AppUser;
import es.upm.dit.isst.labreserve.model.Reserve;

public class CancelReserveServlet extends HttpServlet {
  private static final Long serialVersionUID = 1L;

  public void doPost(HttpServletRequest req, HttpServletResponse resp)
  throws IOException {
		System.out.println("Canceling reserve ");

    String id = req.getParameter("id");
    ReserveDAO dao = ReserveDAOImpl.getInstance();
    AppUserDAO userDAO = AppUserDAOImpl.getInstance();
    
    Reserve reserve = dao.getReserve(Long.parseLong(id));
    AppUser userRes = userDAO.getUser(reserve.getAuthor());
    String date = reserve.getDate();
    String initHour = reserve.getInitHour();
    String finalHour = reserve.getFinalHour();
    String emailTo = userRes.getEmail();
    String name = userRes.getName();
    if (name == null || name.equals("")){
    	name = "usuario";
    }
	  
	  Properties props = new Properties();
	  Session session = Session.getDefaultInstance(props, null);

	  String msgBody = "Estimado " + name + ":" + "\n\n" + "Su reserva con fecha " + date + " de " + initHour + " a " + finalHour + "  ha sido cancelada." +  "\n\n" + "Si usted no la ha cancelado, por favor, realice una nueva reserva." + "\n\n" + "Disculpe las molestias." + "\n\n" +  "LabReserve Team";

	  try {
	      Message msg = new MimeMessage(session);
	      msg.setFrom(new InternetAddress("labreserve2015@gmail.com", "LabReserve Notification"));
	      msg.addRecipient(Message.RecipientType.TO,
	       new InternetAddress(emailTo, name));
	      msg.setSubject("Reserva cancelada");
	      msg.setText(msgBody);
	      Transport.send(msg);
	      dao.remove(Long.parseLong(id));

	  	req.getSession().setAttribute("flashMessageSuccess", "¡Reserva cancelada!");
	    resp.sendRedirect("/main");

	  } catch (AddressException e) {
	      // ...
	  } catch (MessagingException e) {
	      // ...
	  }
    


  }
} 
