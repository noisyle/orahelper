package com.noisyle.tools.orahelper.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.noisyle.tools.orahelper.core.DBConfig;
import com.noisyle.tools.orahelper.core.JDBCUtil;
import com.noisyle.tools.orahelper.core.MyHttpServlet;

@WebServlet(name="CreateUserServlet",urlPatterns="/createUser")
public class CreateUserServlet extends MyHttpServlet {
	private static final long serialVersionUID = 2519010741218835166L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		context.put("conninfos", DBConfig.getConnInfoList());
		render(response, "createUser.html", context);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String conninfo = request.getParameter("conninfo");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String tablespace = request.getParameter("tablespace");
		String exp = request.getParameter("exp");
		String imp = request.getParameter("imp");
		String dba = request.getParameter("dba");
		try {
			JDBCUtil.createUser(conninfo, username, password, tablespace);
			context.put("msg_info", "用户创建成功");
		} catch (Exception e) {
			context.put("msg_error", e.getMessage());
		}
		doGet(request, response);
	}
}
