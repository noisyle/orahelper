package com.noisyle.tools.orahelper.entity;

public class ConnInfo {
	private String alias;
	
	private String host;
	private String port;
	private String ssid;
	private String username;
	private String password;

	private String url;

	protected ConnInfo() {
	}
	
	/**
	 * @param alias
	 * @param host
	 * @param port
	 * @param ssid
	 * @param username
	 * @param password
	 */
	public ConnInfo(String alias, String host, String port, String ssid, String username, String password) {
		this.alias = alias;
		this.host = host;
		this.port = port;
		this.ssid = ssid;
		this.username = username;
		this.password = password;
		this.url = "jdbc:oracle:thin:@" + host + ":" + port + ":" + ssid;
	}
	
	@Override
	public String toString() {
		return alias+"|"+username+"|"+password+"|"+url;
	}
	
	public String getAlias() {
		return alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getUrl() {
		return url;
	}
}
