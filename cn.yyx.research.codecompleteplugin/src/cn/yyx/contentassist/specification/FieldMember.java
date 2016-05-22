package cn.yyx.contentassist.specification;

public class FieldMember {
	
	private String name = null;
	private String type = null;
	private String whereDeclared = null;
	
	public FieldMember(String name, String type, String whereDeclared) {
		this.name = name;
		this.type = type;
		this.whereDeclared = whereDeclared;
	}
	
	@Override
	public String toString() {
		return getName() + "#" + getType() + "#" + getWhereDeclared();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getWhereDeclared() {
		return whereDeclared;
	}

	public void setWhereDeclared(String whereDeclared) {
		this.whereDeclared = whereDeclared;
	}
	
}