package com.noisyle.tools.orahelper.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.noisyle.tools.orahelper.core.DBConfig;
import com.noisyle.tools.orahelper.core.JDBCUtil;
import com.noisyle.tools.orahelper.core.MyHttpServlet;

@WebServlet(name = "ExportDumpServlet", urlPatterns = "/exportDump")
public class ExportDumpServlet extends MyHttpServlet {
	private static final long serialVersionUID = -7896340205071357324L;
	private String contentType;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		contentType = config.getInitParameter("contentType");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		context.put("conninfos", DBConfig.getConnInfoList());
		render(response, "exportDump.html", context);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String fileName = request.getParameter("filename");
			if(!fileName.endsWith(".dmp")) fileName += ".dmp";
			String conninfo = request.getParameter("conninfo");
			
			String tmpDir = System.getProperty("java.io.tmpdir");
			String filePath = tmpDir + UUID.randomUUID().toString()+".dmp";
			
			JDBCUtil.exportDump(conninfo, filePath);

			File downloadFile = new File(filePath);
			if (downloadFile.exists()) {
				response.setContentType(contentType);
				Long length = downloadFile.length();
				response.setContentLength(length.intValue());
				fileName = URLEncoder.encode(fileName, "UTF-8");
				response.addHeader("Content-Disposition", "attachment; filename=" + fileName);

				ServletOutputStream servletOutputStream = response.getOutputStream();
				FileInputStream fileInputStream = new FileInputStream(downloadFile);
				BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
				int size = 0;
				byte[] b = new byte[4096];
				while ((size = bufferedInputStream.read(b)) != -1) {
					servletOutputStream.write(b, 0, size);
				}
				servletOutputStream.flush();
				servletOutputStream.close();
				bufferedInputStream.close();
			} else {
//				logger.info("File is not exist");
			}
		} catch (Exception e) {
			context.put("msg_error", e.getMessage());
			doGet(request, response);
		}
	}
}
