package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;

import core.Config;
import core.JDBCUtil;
import core.MyHttpServlet;

public class IndexServlet extends MyHttpServlet {
	private static final long serialVersionUID = 4900675659300817284L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		VelocityContext context = new VelocityContext();
		context.put("con_username", Config.con_username);
		context.put("con_password", Config.con_password);
		context.put("con_url", Config.con_url);
		render(response, "index.html", context);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String con_username = request.getParameter("con_username");
		String con_password = request.getParameter("con_password");
		String con_url = request.getParameter("con_url");
		if(JDBCUtil.testConnection(con_url, con_username, con_password)){
			Config.con_username = con_username;
			Config.con_password = con_password;
			Config.con_url = con_url;
			
		}
		doGet(request, response);
	}
}
