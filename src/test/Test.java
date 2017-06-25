
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;

import com.alibaba.fastjson.JSON;


public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("success", true);
		System.out.println(JSON.toJSONString(map));
		
		String ext = FilenameUtils.getExtension("1.txt");
		System.out.println(ext);
		
	}

}
