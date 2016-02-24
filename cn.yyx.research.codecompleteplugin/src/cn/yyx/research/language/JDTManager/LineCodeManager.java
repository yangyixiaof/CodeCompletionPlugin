package cn.yyx.research.language.JDTManager;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class LineCodeManager {
	
	Map<Integer, String> mLineCodeMap = new TreeMap<Integer, String>();
	
	public LineCodeManager() {
	}
	
	public void AddLineCode(int linenumber, String generatedCode)
	{
		mLineCodeMap.put(linenumber, generatedCode);
	}
	
	public String GetLineCode(int linenumber)
	{
		return mLineCodeMap.get(linenumber);
	}
	
	public String GetGeneratedCode()
	{
		StringBuilder result = new StringBuilder("");
		Set<Integer> keys = mLineCodeMap.keySet();
		Iterator<Integer> kitr = keys.iterator();
		int preline = 0;
		while (kitr.hasNext())
		{
			int line = kitr.next();
			if (line - preline > 1)
			{
				System.err.println("Not sequent codes. Program will exit. Line:"+line+";PreLine:"+preline);
				//System.exit(1);
			}
			preline = line;
			String lcode = mLineCodeMap.get(line);
			result.append(line + ":" + lcode + "\n");
		}
		return result.toString();
	}
	
}