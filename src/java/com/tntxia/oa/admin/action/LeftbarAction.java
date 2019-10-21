package com.tntxia.oa.admin.action;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.tntxia.web.mvc.BaseAction;
import com.tntxia.web.mvc.WebRuntime;

public class LeftbarAction extends BaseAction {
	
	@SuppressWarnings("rawtypes")
	public List execute(WebRuntime runtime) throws Exception {
		ServletContext context = runtime.getServletContext();
		
		File leftbarFile = new File(context.getRealPath("/WEB-INF/config/leftbar.xml"));
		SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(leftbarFile);
        
        Element root = document.getRootElement();
        
        List list = root.selectNodes("bars/bar");
        
        List<Map<String,Object>> bars = new ArrayList<Map<String,Object>>();
        
        for(int i=0;i<list.size();i++){
        	Map<String,Object> map = new HashMap<String,Object>();
        	Element element = (Element) list.get(i);
        	map.put("text", element.attributeValue("name"));
        	
        	List<Map<String,Object>> buttons = new ArrayList<Map<String,Object>>();
        	List buttonList = element.selectNodes("button");
        	for(int j=0;j<buttonList.size();j++){
        		Element el = (Element) buttonList.get(j);
        		Map<String,Object> buttonMap = new HashMap<String,Object>();
        		String url = el.attributeValue("url");
        		String target = el.attributeValue("target");
        		buttonMap.put("url", url);
        		buttonMap.put("text", el.getText());
        		buttonMap.put("target", target);
        		
        		buttons.add(buttonMap);
        	}
        	map.put("buttons", buttons);
        	
        	bars.add(map);
        }
		return bars;
	}

}
