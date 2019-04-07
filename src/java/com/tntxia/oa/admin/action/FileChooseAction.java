package com.tntxia.oa.admin.action;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.tntxia.oa.admin.entity.FileBean;
import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class FileChooseAction extends BaseAction {
	
	public List<FileBean> root(WebRuntime runtime){
		
		
		List<FileBean> res = new ArrayList<FileBean>();
		File[] files = File.listRoots();
		
		for(File file : files){
			
			String path = file.getPath();
			
			FileBean fileBean = new FileBean();
			fileBean.setRoot(true);
			fileBean.setName(path.substring(0, 1)+"盘");
			fileBean.setAbsolutePath(file.getAbsolutePath());
			res.add(fileBean);
			
		}
		
		return res;
		
	}
	
	public List<FileBean> dir(WebRuntime runtime){
		
		String parent = runtime.getParam("parent");
		
		List<FileBean> res = new ArrayList<FileBean>();
		
		
			File parentFile = new File(parent);
			File[] files = parentFile.listFiles();
			for(File file : files){
				FileBean fileBean = new FileBean();
				fileBean.setDir(file.isDirectory());
				fileBean.setName(file.getName());
				fileBean.setAbsolutePath(file.getAbsolutePath());
				res.add(fileBean);
			}
		
		
		return res;
		
	}

}
