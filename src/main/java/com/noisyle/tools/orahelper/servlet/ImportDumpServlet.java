package com.noisyle.tools.orahelper.servlet;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.noisyle.tools.orahelper.core.DBConfig;
import com.noisyle.tools.orahelper.core.JDBCUtil;
import com.noisyle.tools.orahelper.core.MyHttpServlet;

@WebServlet(name = "ImportDumpServlet", urlPatterns = "/importDump")
public class ImportDumpServlet extends MyHttpServlet {
	private static final long serialVersionUID = -7896340205071357324L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		context.put("con_username", DBConfig.con_username);
		context.put("con_url", DBConfig.getCon_url());
		render(response, "importDump.html", context);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			DiskFileItemFactory df = new DiskFileItemFactory();
	        //设定阈值1M，如果超过这个值，则文件就直接写到临时文件，不会一直占用内存  
	        //利用这个特性可在上传大文件的时候不会占用大量内存，可以提高并发使用量。  
			df.setSizeThreshold(1024 * 1024);

			String tmpDir = System.getProperty("java.io.tmpdir");

			File tmpPath = new File(tmpDir);

			df.setRepository(tmpPath);
			ServletFileUpload sf = new ServletFileUpload(df);
			sf.setFileSizeMax(1024 * 1024 * 100);// 上传文件最大为100M
			List<FileItem> list = sf.parseRequest(request);
			File tmpFile = null;
			String username = null;
			for (FileItem fileItem : list) {
				if(!fileItem.isFormField()){
					String fileName = fileItem.getName();
					if (fileName == null || "".equals(fileName.trim())) {
						continue;
					} else {
						tmpFile = new File(tmpPath, UUID.randomUUID().toString()+".dmp");
						fileItem.write(tmpFile);
					}
				}else{
					String fieldName = fileItem.getFieldName();
					if ("username".equals(fieldName)) {  
						username = new String(fileItem.getString("utf-8"));  
	                }
				}
			}
			if (tmpFile != null) {
				String result = JDBCUtil.importDump(tmpFile.getAbsolutePath(), username);
				context.put("msg_info", result);
			}
	  
		} catch (Exception e) {
			context.put("msg_error", e.getMessage());
		}
		doGet(request, response);
	}
}
