package com.noisyle.tools.orahelper.core;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class MyFilter implements Filter {
	protected String sEncodingName;

	protected FilterConfig sysFilter;

	protected boolean bEnable;

	public void init(FilterConfig arg0) throws ServletException {
		this.sysFilter = arg0;
		this.loadFilterSetting();
	}

	public void destroy() {

	}

	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2) throws IOException,
			ServletException {
		if (this.bEnable) {
			try {
				arg0.setCharacterEncoding(this.sEncodingName);
				arg1.setContentType("text/html;charset=" + this.sEncodingName);
				arg1.setCharacterEncoding(this.sEncodingName);
				arg2.doFilter(arg0, arg1);

			} catch (Exception e) {
				System.out.println("出错了");
			}
			// session.close();
		} else {
			arg2.doFilter(arg0, arg1);
		}
	}

	private void loadFilterSetting() {
		this.sEncodingName = this.sysFilter.getInitParameter("encoding");
		System.out.println("encoding:" + sEncodingName);
		String sEnable = this.sysFilter.getInitParameter("enable");
		if (sEnable != null && sEnable.equalsIgnoreCase("true")) {
			this.bEnable = true;
		} else {
			this.bEnable = false;
		}
	}

}