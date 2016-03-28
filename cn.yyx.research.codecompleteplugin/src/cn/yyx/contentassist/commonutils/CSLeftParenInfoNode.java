package cn.yyx.contentassist.commonutils;

public class CSLeftParenInfoNode extends CSNode{
	
	int times = -1;
	
	public CSLeftParenInfoNode(int times) {
		super(CSNodeType.HelpInfo);
		this.times = times;
	}
	
}