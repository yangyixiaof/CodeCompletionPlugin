package cn.yyx.contentassist.codesynthesis.data;

public class CSArrayAccessStartData extends CSFlowLineData{
	
	public CSArrayAccessStartData(CSFlowLineData dt) {
		super(dt.getId(), dt.getSete(), dt.getData(), dt.getDcls(), dt.isHaspre(), dt.isHashole(), dt.getPretck(), dt.getPosttck(), dt.getHandler());
	}
	
}