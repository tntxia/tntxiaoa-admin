package com.tntxia.oa.admin.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.tntxia.dbmanager.DBManager;
import com.tntxia.oa.admin.entity.ZTreeNode;
import com.tntxia.oa.admin.service.RightGroupService;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.PageBean;
import com.tntxia.web.mvc.WebRuntime;

public class RestrainAction extends BaseAction {
	
	private DBManager dbManager = this.getDBManager("oa");
	
	private DBManager dbManagerBack = this.getDBManager("oa_back");
	
	// 权限组的服务
	private RightGroupService rightGroupService = new RightGroupService();
	
	public Map<String,Object> add(WebRuntime runtime) throws Exception{
		String name = runtime.getParam("name");
		String sql = "insert into restrain(restrain_name) values(?)";
		dbManager.update(sql, new Object[] {name});
		return this.success();
	}
	
	public Map<String,Object> del(WebRuntime runtime) throws Exception{
		String id = runtime.getParam("id");
		String sql = "delete from restrain where id = ?";
		dbManager.update(sql, new Object[] {id});
		return this.success();
	}
	
	@SuppressWarnings("rawtypes")
	public Map<String,Object> list(WebRuntime runtime) throws Exception{
		PageBean pageBean = runtime.getPageBean(20);
		String sql = "select top "+pageBean.getTop()+" * from restrain";
		List list = dbManager.queryForList(sql, true);
		int count = dbManager.queryForInt("select count(*) from restrain");
		return this.getPagingResult(list, pageBean, count);
	}
	
	@SuppressWarnings("rawtypes")
	public List<ZTreeNode> getRightZTreeNode(WebRuntime runtime) throws Exception {
		
		String id = runtime.getParam("id");
		
		List<ZTreeNode> res = new ArrayList<ZTreeNode>();
		
		List list = rightGroupService.listAll();
		
		for(int i=0;i<list.size();i++) {
			Map<String,Object> map = (Map<String,Object>) list.get(i);
			ZTreeNode groupNode = new ZTreeNode();
			groupNode.setId(String.valueOf((Integer) map.get("id")));
			groupNode.setPid("0");
			groupNode.setName((String)map.get("name"));
			groupNode.setOpen(true);
			res.add(groupNode);
		}
		
		List roleRightList = this.getRoleRightList(id);
		
		
		String sql = "select * from user_right";
		List rightList = dbManagerBack.queryForList(sql, true);
		for(int i=0;i<rightList.size();i++) {
			Map<String,Object> map = (Map<String,Object>) rightList.get(i);
			ZTreeNode rightNode = new ZTreeNode();
			Integer rightId = (Integer)map.get("id");
			rightNode.setId("right_"+ rightId );
			rightNode.setPid(String.valueOf(map.get("group_id")));
			rightNode.setName((String)map.get("name"));
			rightNode.setOpen(true);
			if(isNodeChecked(roleRightList, rightId)) {
				rightNode.setChecked(true);
			}
			res.add(rightNode);
		}
		
		return res;
	}
	
	@SuppressWarnings("rawtypes")
	private boolean isNodeChecked(List rightList, Integer id) {
		for(int i=0;i<rightList.size();i++) {
			Map<String,Object> map = (Map<String,Object>) rightList.get(i);
			Integer rightId = (Integer) map.get("id");
			if(rightId.equals(id)) {
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("rawtypes")
	private List getRoleRightList(String id) throws Exception {
		
		String sql = "select r.id from user_right r ,role_right_rel rel where r.id = rel.rightId and roleId = ?";
		return dbManagerBack.queryForList(sql, new Object[] {id},true);
		
	}
	
	public Map<String,Object> delRoleRightList(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		String sql = "delete from role_right_rel rel where id = ?";
		dbManagerBack.update(sql, new Object[] {id});
		return this.success();
		
	}
	
	public Map<String,Object> addRoleRight(WebRuntime runtime) throws Exception {
		String id = runtime.getParam("id");
		String rightIds = runtime.getParam("rightIds");
		String sql = "delete from role_right_rel where roleId = ?";
		dbManagerBack.update(sql,new Object[]{id});
		for(String rightId : rightIds.split(",")) {
			sql = "insert into role_right_rel(roleId,rightId) values(?,?)";
			dbManagerBack.update(sql, new Object[] {id,rightId});
		}
		
		return this.success();
		
	}

}
