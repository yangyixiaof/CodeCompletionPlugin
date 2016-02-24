package cn.yyx.research.language.Utility;

public class CamelCaseSplitter {
	
	public static String[] SplitWord(String word)
	{
		return word.split("(?<!(^|[A-Z0-9]))(?=[A-Z0-9])|(?<!(^|[^A-Z]))(?=[0-9])|(?<!(^|[^0-9]))(?=[A-Za-z])|(?<!^)(?=[A-Z][a-z])");
	}
	
	public static String SplitWordF(String word, String prefix)
	{
		StringBuilder cnt = new StringBuilder("");
		String[] words = SplitWord(word);
		int offset = words.length-1;
		for(int i = 0; i < offset; i++)
		{
			cnt.append(prefix+words[i]).append(" ");
		}
		cnt.append(prefix+words[offset]);
		return cnt.toString();
	}
	
	/*public static void main(String[] args) {
		System.out.println(CamelCaseSplitter.SplitWordF("adREsdWsdEdfds"));
	}*/
	
}