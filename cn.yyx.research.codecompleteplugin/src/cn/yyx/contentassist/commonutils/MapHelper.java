package cn.yyx.contentassist.commonutils;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class MapHelper {
	
	public static Map<String, TypeCheck> CloneDatas(Map<String, TypeCheck> datas)
	{
		Map<String, TypeCheck> res = new TreeMap<String, TypeCheck>();
		Set<String> codes = datas.keySet();
		Iterator<String> citr = codes.iterator();
		while (citr.hasNext())
		{
			String code = citr.next();
			TypeCheck tc = datas.get(code);
			res.put(code, tc);
		}
		return res;
	}
	
}