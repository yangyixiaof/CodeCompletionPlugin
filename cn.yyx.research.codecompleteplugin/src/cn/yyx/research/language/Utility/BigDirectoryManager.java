package cn.yyx.research.language.Utility;

import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

public class BigDirectoryManager {
	
	static Map<String, BigDirectory> cps = new TreeMap<String, BigDirectory>();
	
	public static BigDirectory GetBigDirectory(String bdname)
	{
		if (!cps.containsKey(bdname))
		{
			try {
				BigDirectory bd = new BigDirectory(bdname);
				cps.put(bdname, bd);
			} catch (Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		return cps.get(bdname);
	}
	
	public static void WriteCorpus(ArrayList<CorpusContentPair> corpuses)
	{
		for (CorpusContentPair ccp : corpuses)
		{
			BigDirectory bd = GetBigDirectory(ccp.getCorpus());
			bd.AppendOneContentToTheBigFile(ccp.getContent());
		}
	}
}
