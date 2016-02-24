package cn.yyx.research.language.JDTManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import cn.yyx.research.language.Utility.CorpusContentPair;

public class OtherCodeManager {
	
	private Map<String, String> othercodemap = new TreeMap<String, String>();
	
	public OtherCodeManager() {
	}
	
	public void AppendOtherCode(String key, String value)
	{
		String ocode = getOtherCodeMap().get(key);
		if (ocode == null)
		{
			ocode = "";
		}
		// ocode.equals("") || 
		String trimvalue = value.trim();
		if (trimvalue.equals("."))
		{
			ocode += trimvalue;
		}
		else
		{
			ocode += " " + trimvalue;
		}
		getOtherCodeMap().put(key, ocode);
	}
	
	public ArrayList<CorpusContentPair> GetOtherGeneratedCode() {
		ArrayList<CorpusContentPair> result = new ArrayList<CorpusContentPair>();
		Set<String> corpuses = getOtherCodeMap().keySet();
		Iterator<String> itr = corpuses.iterator();
		while (itr.hasNext())
		{
			String corpus = itr.next();
			String content = getOtherCodeMap().get(corpus);
			CorpusContentPair ccp = new CorpusContentPair(corpus, content);
			result.add(ccp);
		}
		return result;
	}

	public Map<String, String> getOtherCodeMap() {
		return othercodemap;
	}

	public void setOthercodemap(Map<String, String> othercodemap) {
		this.othercodemap = othercodemap;
	}
	
}