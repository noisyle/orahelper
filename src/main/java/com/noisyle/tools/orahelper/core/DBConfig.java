package com.noisyle.tools.orahelper.core;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noisyle.tools.orahelper.entity.ConnInfo;

public class DBConfig {
	
	private static Map<String, ConnInfo> connInfoMap = new HashMap<String, ConnInfo>();
	private static File dbconfig;
	
	public static synchronized void addConnInfo(ConnInfo connInfo) {
		try {
			connInfoMap.put(connInfo.getAlias(), connInfo);
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(dbconfig, connInfoMap.values());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static ConnInfo getConnInfoByAlias(String alias) {
		return connInfoMap.get(alias);
	}
	
	public static List<ConnInfo> getConnInfoList() {
		List<ConnInfo> connInfos = new LinkedList<ConnInfo>();
		connInfos.addAll(connInfoMap.values());
		return connInfos;
	}
	
	protected static void initConfigFile() throws IOException {
//		String classpath = this.getClass().getResource("/").getPath();
//		String webroot = sce.getServletContext().getRealPath("/");
//		if(webroot == null){
//			webroot = sce.getServletContext().getResource("/").getPath();
//		}
		String curdir = System.getProperty("user.dir");
		System.out.println("当前工作目录:"+curdir);
		File configdir = new File(curdir+File.separator+"config");
		if(!configdir.exists()){
			System.out.println("创建配置文件目录:"+configdir.getAbsolutePath());
			configdir.mkdirs();
		}
		
		dbconfig = new File(configdir, "dbconfig.json");
		if(!dbconfig.exists()){
			System.out.println("创建数据库连接配置文件:"+dbconfig.getAbsolutePath());
			dbconfig.createNewFile();
			ObjectMapper mapper = new ObjectMapper();
			mapper.writeValue(dbconfig, connInfoMap.values());
		}else{
			ObjectMapper mapper = new ObjectMapper();
			ConnInfo[] infos = mapper.readValue(dbconfig, ConnInfo[].class);
			System.out.println("从配置文件加载数据库连接设置:");
			for(ConnInfo info : infos){
				connInfoMap.put(info.getAlias(), info);
				System.out.println(info.toString());
			}
		}
	}
}
