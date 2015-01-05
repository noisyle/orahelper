package core;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHandler;

import servlet.CreateTablespaceServlet;
import servlet.IndexServlet;

public class MyServletContextHandler extends ServletContextHandler{
	private ServletHandler servletHandler;
	
	public MyServletContextHandler() {
		super(ServletContextHandler.SESSIONS);
		
		servletHandler = new ServletHandler();
		
        servletHandler.addServletWithMapping(IndexServlet.class, "/home");
        servletHandler.addServletWithMapping(CreateTablespaceServlet.class, "/createTablespace");
        
        this.setHandler(servletHandler);
	}

}
