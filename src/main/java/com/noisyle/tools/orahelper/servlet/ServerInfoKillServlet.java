package com.noisyle.tools.orahelper.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.noisyle.tools.orahelper.core.DBConfig;
import com.noisyle.tools.orahelper.core.JDBCUtil;
import com.noisyle.tools.orahelper.core.MyHttpServlet;

@WebServlet(name="ServerInfoKillServlet",urlPatterns="/serverInfoKill")
public class ServerInfoKillServlet extends MyHttpServlet {
	private static final long serialVersionUID = 8328737800158015792L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String alias = request.getParameter("alias");
			System.out.println(alias);
			String sid = request.getParameter("sid");
			String serial = request.getParameter("serial");
			if(alias!=null&&sid!=null&&serial!=null){
				JDBCUtil.killSessions(DBConfig.getConnInfoByAlias(alias), sid, serial);
				response.getWriter().print("{\"resultType\":1}");
			}else{
				throw new Exception("传入参数不正确");
			}
		} catch (Exception e) {
			response.getWriter().print("{\"resultType\":2}");
		}
		response.setContentType("application/x-json;charset=utf-8");
		response.getWriter().close();
	}
}
