package com.noisyle.tools.orahelper.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.noisyle.tools.orahelper.core.DBConfig;
import com.noisyle.tools.orahelper.core.JDBCUtil;
import com.noisyle.tools.orahelper.core.MyHttpServlet;

@WebServlet(name="CreateTablespaceServlet",urlPatterns="/createTablespace")
public class CreateTablespaceServlet extends MyHttpServlet {
	private static final long serialVersionUID = 2519010741218835166L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		context.put("conninfos", DBConfig.getConnInfoList());
		render(response, "createTablespace.html", context);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String conninfo = request.getParameter("conninfo");
			String ts_name = request.getParameter("ts_name");
			JDBCUtil.createTablespace(conninfo, ts_name);
			context.put("msg_info", "表空间创建成功");
		} catch (Exception e) {
			context.put("msg_error", e.getMessage());
		}
		doGet(request, response);
	}
}
