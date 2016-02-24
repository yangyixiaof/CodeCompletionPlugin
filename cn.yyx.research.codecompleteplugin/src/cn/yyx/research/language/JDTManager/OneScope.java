package cn.yyx.research.language.JDTManager;

public class OneScope implements Comparable<OneScope>{
	
	private Integer ID = -1;
	private Integer level = -1;
	
	public OneScope(int ID, int level) {
		this.setID(ID);
		this.setLevel(level);
	}
	
	public Integer getID() {
		return ID;
	}
	
	public void setID(Integer iD) {
		ID = iD;
	}
	
	@Override
	public int compareTo(OneScope o) {
		return ID.compareTo(o.getID());
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
	
}