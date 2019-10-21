package com.tntxia.oa.admin.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.admin.entity.ZTreeNode;
import com.tntxia.oa.admin.service.MenuService;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class RestrainMenuAction extends BaseAction{
	
	private DBManager oaDBManager = this.getDBManager("oa");
	
	private DBManager dbManager = this.getDBManager("oa_back");
	
	private MenuService menuService = new MenuService();
	
	public Map<String,Object> list(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		String sql = "select * from restrain";
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("rows", oaDBManager.queryForList(sql, true));
		return res;
	}
	
	private void clearMenu(String id) throws Exception{
		String sql = "delete from restrain_menu where restrain_id = ?";
		dbManager.update(sql, new Object[]{id});
		
	}
	
	public Map<String,Object> setMenu(WebRuntime runtime) throws Exception{
		
		String id = runtime.getParam("id");
		String[] menus = runtime.getParam("menus").split(",");
		clearMenu(id);
		
		String sql = "insert into restrain_menu(restrain_id,menu_id) values(?,?)";
		
		for(String menu:menus){
			dbManager.update(sql,new Object[]{id,menu});
		}
		
		Map<String, Object> res = new HashMap<String, Object>();
		res.put("success", true);
		return res;
	}
	
	@SuppressWarnings("rawtypes")
	public List<ZTreeNode> getMenuZTreeNode(WebRuntime runtime) throws Exception{
		
		String id = runtime.getParam("id");
		
		List<ZTreeNode> res = new ArrayList<ZTreeNode>();
		
		List menus = menuService.list();
		
		for(int i=0;i<menus.size();i++) {
			Map map = (Map) menus.get(i);
			ZTreeNode treeNode = new ZTreeNode();
			treeNode.setId((String)map.get("id"));
			treeNode.setName((String) map.get("name"));
			treeNode.setPid((String) map.get("pid"));
			res.add(treeNode);
		}
		
		String sql = "select * from restrain_menu where restrain_id = ?";
		
		List list = dbManager.queryForList(sql,new Object[]{id},true);
		
		List<Integer> ids = new ArrayList<Integer>();
		for(int i=0;i<list.size();i++){
			Map item = (Map) list.get(i);
			ids.add((Integer) item.get("menu_id"));
		}
		
		for(ZTreeNode node : res) {
			String nodeId = node.getId();
			for(Integer menuId : ids) {
				if(String.valueOf(menuId).equals(nodeId)) {
					node.setChecked(true);
					break;
				}
			}
		}
		
		return res;
	}

}
