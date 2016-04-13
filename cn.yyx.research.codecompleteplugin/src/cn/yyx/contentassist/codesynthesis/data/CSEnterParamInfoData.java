package cn.yyx.contentassist.codesynthesis.data;

public class CSEnterParamInfoData extends CSFlowLineData{
	
	private int times = -1;
	
	public CSEnterParamInfoData(int times, CSFlowLineData dt) {
		super(dt.getId(), dt.getSete(), dt.getData(), dt.getDcls(), dt.isHaspre(), dt.isHashole(), dt.getPretck(), dt.getPosttck(), dt.getHandler());
		this.times = times;
	}
	
	/*public CSEnterParamInfoData(int times, SynthesisHandler handler) {
		super(handler);
		this.times = times;
	}*/

	public int getTimes() {
		return times;
	}

	public void setTimes(int times) {
		this.times = times;
	}
	
}