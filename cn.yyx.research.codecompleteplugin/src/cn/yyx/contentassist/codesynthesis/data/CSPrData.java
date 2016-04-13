package cn.yyx.contentassist.codesynthesis.data;

public class CSPrData extends CSFlowLineData{
	
	public CSPrData(CSFlowLineData dt) {
		super(dt.getId(), dt.getSete(), dt.getData(), dt.getDcls(), dt.isHaspre(), dt.isHashole(), dt.getPretck(), dt.getPosttck(), dt.getHandler());
	}
	
}
