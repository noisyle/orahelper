package com.noisyle.tools.orahelper.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noisyle.tools.orahelper.core.DBConfig;
import com.noisyle.tools.orahelper.core.JDBCUtil;
import com.noisyle.tools.orahelper.core.MyHttpServlet;

@WebServlet(name="ServerInfoServlet",urlPatterns="/serverInfo")
public class ServerInfoServlet extends MyHttpServlet {
	private static final long serialVersionUID = 2519010741218835166L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		context.put("conninfos", DBConfig.getConnInfoList());
		render(response, "serverInfo.html", context);
	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String alias = request.getParameter("alias");
			Map result = new HashMap();
			result.put("resultType", 1);
			result.put("data", JDBCUtil.querySessions(DBConfig.getConnInfoByAlias(alias)));
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(response.getWriter(), result);
			
		} catch (Exception e) {
			response.getWriter().print("{\"resultType\":2,\"msg\":\""+e.getMessage().trim()+"\"}");
		}
		response.setContentType("application/x-json");
		response.getWriter().close();
	}
}
