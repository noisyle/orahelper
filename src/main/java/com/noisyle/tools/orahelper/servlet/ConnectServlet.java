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
		context.put("con_ip", DBConfig.con_ip);
		context.put("con_port", DBConfig.con_port);
		context.put("con_ssid", DBConfig.con_ssid);
		render(response, "connect.html", context);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DBConfig.con_username = request.getParameter("con_username");
		DBConfig.con_password = request.getParameter("con_password");
		DBConfig.con_ip = request.getParameter("con_ip");
		DBConfig.con_port = request.getParameter("con_port");
		DBConfig.con_ssid = request.getParameter("con_ssid");
		try {
			JDBCUtil.testConnection(DBConfig.getCon_url(), DBConfig.con_username, DBConfig.con_password);
			context.put("msg_info", "连接成功");
		} catch (Exception e) {
			context.put("msg_error", e.getMessage());
		}
		doGet(request, response);
	}
}
