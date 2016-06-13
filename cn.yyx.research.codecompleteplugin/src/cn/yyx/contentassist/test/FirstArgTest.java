package cn.yyx.contentassist.test;

public class FirstArgTest {
	
	public void SpecialTestFirstArg()
	{
		LCSTest lcs = new LCSTest();
		System.out.println(lcs);
		System.out.append("ads").flush();
	}
	
	public static void main(String[] args) {
		try {
			
			System.out.println(Class.forName("HTM.LCSTest"));
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
}