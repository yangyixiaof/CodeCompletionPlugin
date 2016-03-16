package cn.yyx.contentassist.commonutils;

public class RefAndModifiedMember {
	
	private String ref = null;
	private String member = null;
	
	public RefAndModifiedMember(String ref, String member) {
		this.setRef(ref);
		this.setMember(member);
	}

	public String getRef() {
		return ref;
	}

	public void setRef(String ref) {
		this.ref = ref;
	}

	public String getMember() {
		return member;
	}

	public void setMember(String member) {
		this.member = member;
	}
	
}