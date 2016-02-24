package cn.yyx.research.language.JDTManager;

public class LineManager {
	
	int maxline = 0;
	
	public int NewLine()
	{
		maxline++;
		return maxline;
	}
	
	public int GetCurrentLine()
	{
		return maxline;
	}
	
}
