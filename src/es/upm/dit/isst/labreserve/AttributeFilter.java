package es.upm.dit.isst.labreserve;


import java.io.IOException;
import java.util.Enumeration;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;


public class AttributeFilter implements Filter {

	@Override
	public void destroy() {
		System.out.println("Destroy");
	}
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response,FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest httpServletRequest= (HttpServletRequest) request;
		HttpSession session = httpServletRequest.getSession();
		Enumeration sessionAttributes =  session.getAttributeNames();
		while (sessionAttributes.hasMoreElements()) {
			//attrName get name of attribute
			Object attrName = (Object) sessionAttributes.nextElement();
			
			//attrVal store value of attribute
			Object attrVal=session.getAttribute(attrName.toString());
			
			//This condition is used to filter the flashMessage Attributes. And do further process, like add attribute in session.
			if(attrVal instanceof FlashMessage){
				request.setAttribute(attrName.toString(), attrVal.toString());
				session.removeAttribute(attrName.toString());
				System.out.println("Flash Message Successfully Removed From Session.");
			}
		}
		
		filterChain.doFilter(request, response);
		
		httpServletRequest= (HttpServletRequest) request;
		Enumeration requestAttributes =  request.getAttributeNames();
		while (requestAttributes.hasMoreElements()) {
			//attrName get name of attribute
			Object attrName = (Object) requestAttributes.nextElement();
			
			//attrVal store value of attribute
			Object attrVal=request.getAttribute(attrName.toString());
			if(attrVal instanceof FlashMessage){
				System.out.println("Flash Message Successfully Added in Session.");
				session.setAttribute(attrName.toString(), attrVal);
			}
			
		}
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("Init");
	}

}
