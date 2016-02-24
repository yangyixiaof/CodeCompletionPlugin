package cn.yyx.research.language.JDTManager;

public class ReferenceHint
{
	private int DataType = -1;
	private int WayUse = -1;
	
	public ReferenceHint(int dataType, int wayUse) {
		DataType = dataType;
		WayUse = wayUse;
	}
	
	public int GetOverAllHint()
	{
		return DataType | WayUse;
	}
	
	public int getDataType() {
		return DataType;
	}
	public void setDataType(int dataType) {
		DataType = dataType;
	}
	public int getWayUse() {
		return WayUse;
	}
	public void setWayUse(int wayUse) {
		WayUse = wayUse;
	}
}