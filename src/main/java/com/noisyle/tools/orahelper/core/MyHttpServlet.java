package com.noisyle.tools.orahelper.core;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

abstract public class MyHttpServlet extends HttpServlet {
	private static final long serialVersionUID = -1262601962778794684L;

	protected VelocityEngine ve;
	protected VelocityContext context;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ve = new VelocityEngine();
		Properties prop = new Properties();
		String path = this.getServletContext().getRealPath("/");
		prop.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, path + "\\template\\");
		prop.setProperty(Velocity.ENCODING_DEFAULT, "UTF8");
		prop.setProperty(Velocity.INPUT_ENCODING, "UTF8");
		prop.setProperty(Velocity.OUTPUT_ENCODING, "UTF8");
		try {
			ve.init(prop);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		if ((!(req instanceof HttpServletRequest)) || (!(res instanceof HttpServletResponse))) {
			throw new ServletException("non-HTTP request or response");
		}
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		context = new VelocityContext();
		service(request, response);
	}

	protected void render(HttpServletResponse response, String template, VelocityContext context) {
		StringWriter writer = new StringWriter();
		context.put("page", template);
		
		Template t = ve.getTemplate("base.html");
		t.merge(context, writer);

		response.setContentType("text/html; charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		try {
			response.getWriter().println(writer.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
