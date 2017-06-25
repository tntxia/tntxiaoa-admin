package com.tntxia.oa.admin.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.admin.util.PageMaker;

public class StaticService{
	
//	private DBManager oaDBManager = new DBManager(new FilepathDataSource("c:\\jdbc.properties"));
//	
//	private DBManager dbManager = new DBManager(new JdbcPropertiesDataSource());
//	
	
	private DBManager oaDBManager ;
	
	private DBManager dbManager;
	
	private boolean existStaticInfo(){
		String sql = "select count(*) from oa_static_info";
		return dbManager.exist(sql);
	}
	
	private void updateStaticInfo(String path) throws Exception{
		String sql = "update oa_static_info set export_path = ?";
		dbManager.update(sql, new Object[]{path});
	}
	
	private void addStaticInfo(String path) throws Exception{
		String sql = "insert into oa_static_info(export_path) values(?)";
		dbManager.update(sql, new Object[]{path});
	}
	
	private void saveExportPath(String path) throws Exception{
		if(existStaticInfo()){
			updateStaticInfo(path);
		}else{
			addStaticInfo(path);
		}
	}
	
	@SuppressWarnings("rawtypes")
	private List getRestrainList() throws Exception{
		String sql = "select * from restrain";
		return oaDBManager.queryForList(sql, true);
	}
	
	@SuppressWarnings("rawtypes")
	private List getMenus(Integer restrainId) throws Exception{
		String sql = "select m.* from restrain_menu rm inner join menu m on rm.menu_id = m.id where restrain_id = ?";
		return dbManager.queryForList(sql, new Object[]{restrainId}, true);
	}
	
	@SuppressWarnings("rawtypes")
	public void execute(String path) throws Exception{
		
		saveExportPath(path);

		List restrainList = this.getRestrainList();
		for(int i=0;i<restrainList.size();i++){
			Map map = (Map) restrainList.get(i);
			Integer id = (Integer) map.get("id");
			List menuList = this.getMenus(id);
			Map<String,Object> root = new HashMap<String,Object>();
			root.put("menuList", menuList);
			PageMaker.makePage("D:\\template", "index.jsp", "GBK", root, path+"\\index_"+id+".jsp");
		}
		
	}
	
	public Map<String,Object> getStaticInfo() throws Exception{
		String sql = "select * from oa_static_info";
		return dbManager.queryForMap(sql,true);
	}

}
