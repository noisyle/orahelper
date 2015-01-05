package server;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerCollection;
import org.eclipse.jetty.server.handler.ResourceHandler;

import core.MyServletContextHandler;

public class RunServer {

	public static void main(String[] args) throws Exception {
		int port = 8080;
		
		Server server = new Server(port);
		
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setResourceBase("static");
		
		MyServletContextHandler servletHandler = new MyServletContextHandler();
        
		HandlerCollection handlers = new HandlerCollection();
        handlers.setHandlers(new Handler[]{resourceHandler, servletHandler});
        server.setHandler(handlers);
        server.start();
        
        try {
            URI uri = new URI("http://localhost:"+port+"/home");
            Desktop.getDesktop().browse(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        server.join();
	}

}
