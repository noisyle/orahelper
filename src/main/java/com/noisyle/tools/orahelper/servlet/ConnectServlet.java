package com.noisyle.tools.orahelper.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.noisyle.tools.orahelper.core.DBConfig;
import com.noisyle.tools.orahelper.core.JDBCUtil;
import com.noisyle.tools.orahelper.core.MyHttpServlet;
import com.noisyle.tools.orahelper.entity.ConnInfo;

@WebServlet(name="ConnectServlet",urlPatterns="/connect")
public class ConnectServlet extends MyHttpServlet {
	private static final long serialVersionUID = 4900675659300817284L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		context.put("conninfos", DBConfig.getConnInfoList());
		render(response, "connect.html", context);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String method = request.getParameter("method");
			if("delete".equals(method)){
				String alias = request.getParameter("conninfo");
				DBConfig.DelConnInfo(alias);
				
				context.put("msg_info", "删除成功");
			}else{
				String alias = request.getParameter("con_alias");
				String host = request.getParameter("con_host");
				String port = request.getParameter("con_port");
				String ssid = request.getParameter("con_ssid");
				String username = request.getParameter("con_username");
				String password = request.getParameter("con_password");
				
				ConnInfo info = new ConnInfo(alias, host, port, ssid, username, password);
				System.out.println("保存数据库连接配置:"+info.toString());
				JDBCUtil.testConnection(info);
				DBConfig.addConnInfo(info);
				
				context.put("msg_info", "添加成功");
			}
		} catch (Exception e) {
			context.put("msg_error", e.getMessage());
		}
		doGet(request, response);
	}
}
