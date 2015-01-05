package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.VelocityContext;

import core.JDBCUtil;
import core.MyHttpServlet;

public class CreateTablespaceServlet extends MyHttpServlet {
	private static final long serialVersionUID = 2519010741218835166L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		VelocityContext context = new VelocityContext();
		render(response, "createTablespace.html", context);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String ts_name = request.getParameter("ts_name");
		JDBCUtil.createTablespace(ts_name);
		doGet(request, response);
	}
}
