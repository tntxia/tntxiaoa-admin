package com.tntxia.oa.admin.action;

import java.io.File;
import java.util.Map;

import org.dom4j.Document;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;
import com.tntxia.web.mvc.view.FileView;
import com.tntxia.web.util.Dom4jUtil;

public class StampAction extends BaseAction {
	
	private DBManager dbManager = this.getDBManager("admin");
	
	private String getProjectPath() throws Exception{
		String sql = "select * from config";
		Map<String,Object> map = dbManager.queryForMap(sql, true);
		return (String)map.get("path");
	}
	
	public FileView getImg(WebRuntime runtime) throws Exception{
		FileView view = new FileView();
		String projectPath = this.getProjectPath();
		
		Document doc = Dom4jUtil.getDoc(projectPath+"\\WEB-INF\\config\\design\\stamp.xml");
		
		String img = Dom4jUtil.getProp(doc.getRootElement(), "img");
		
		String[] arr = img.split("/");
		
		String stampPath = projectPath;
		
		for(String s : arr){
			stampPath+= File.separator+s;
		}
		
		view.setFilePath(stampPath);
		return view;
	}

}
