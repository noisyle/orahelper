package com.noisyle.tools.orahelper.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.noisyle.tools.orahelper.core.DBConfig;
import com.noisyle.tools.orahelper.core.JDBCUtil;
import com.noisyle.tools.orahelper.core.MyHttpServlet;

@WebServlet(name="ConnectServlet",urlPatterns="/connect")
public class ConnectServlet extends MyHttpServlet {
	private static final long serialVersionUID = 4900675659300817284L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		context.put("con_username", DBConfig.con_username);
		context.put("con_password", DBConfig.con_password);
		context.put("con_url", DBConfig.con_url);
		render(response, "connect.html", context);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String con_username = request.getParameter("con_username");
		String con_password = request.getParameter("con_password");
		String con_url = request.getParameter("con_url");
		try {
			if(JDBCUtil.testConnection(con_url, con_username, con_password)){
				DBConfig.con_username = con_username;
				DBConfig.con_password = con_password;
				DBConfig.con_url = con_url;
			}
			context.put("msg_info", "连接成功");
		} catch (Exception e) {
			context.put("msg_error", e.getMessage());
		}
		doGet(request, response);
	}
}
