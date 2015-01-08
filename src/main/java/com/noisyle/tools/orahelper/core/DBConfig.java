package com.noisyle.tools.orahelper.core;

public class DBConfig {
	public static String con_ip = "127.0.0.1";
	public static String con_port = "1521";
	public static String con_ssid = "xe";
	public static String con_username = "test1";
	public static String con_password = "password";
	
	public static String getCon_url() {
		return "jdbc:oracle:thin:@"+con_ip+":"+con_port+":"+con_ssid;
	}
}
