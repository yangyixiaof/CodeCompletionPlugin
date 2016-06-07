package cn.yyx.contentassist.codesynthesis.typeutil;

public class SameTypeConflictException extends TypeConflictException {
	
	private static final long serialVersionUID = 1L;
	
	private int prelength = -1;
	private CCType needclass = null;
	
	public SameTypeConflictException(String info, CCType ndcls) {
		super(info);
		this.setNeedclass(ndcls);
	}

	public int getPrelength() {
		return prelength;
	}

	public void setPrelength(int prelength) {
		this.prelength = prelength;
	}

	public CCType getNeedclass() {
		return needclass;
	}

	public void setNeedclass(CCType needclass) {
		this.needclass = needclass;
	}
	
}