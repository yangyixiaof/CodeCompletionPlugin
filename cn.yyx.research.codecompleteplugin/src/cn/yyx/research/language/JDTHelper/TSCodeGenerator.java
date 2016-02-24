package cn.yyx.research.language.JDTHelper;

public class TSCodeGenerator {
	
	StringBuilder result = new StringBuilder("");
	
	public TSCodeGenerator() {
	}
	
	public void AppendOneToken(String token)
	{
		AssertNoSpace(token);
		result.append(" "+token);
	}
	
	public String toString()
	{
		return result.toString();
	}
	
	private void AssertNoSpace(String token)
	{
		if (token.contains(" "))
		{
			System.err.println("Traning Token can not contain white space. The whole program will exit.");
			System.exit(1);
		}
	}
	
}