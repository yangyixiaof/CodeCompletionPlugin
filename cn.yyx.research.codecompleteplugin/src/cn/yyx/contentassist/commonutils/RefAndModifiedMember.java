package cn.yyx.contentassist.commonutils;

public class RefAndModifiedMember {
	
	private String ref = null;
	private String member = null;
	private String membertype = null;
	// private MethodMember maxMm = null;
	
	public RefAndModifiedMember(String ref, String member, String membertype) { // , MethodMember maxMm
		this.setRef(ref);
		this.setMember(member);
		this.setMembertype(membertype);
		// this.setMaxMm(maxMm);
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

	public String getMembertype() {
		return membertype;
	}

	public void setMembertype(String membertype) {
		this.membertype = membertype;
	}

	/*public MethodMember getMaxMm() {
		return maxMm;
	}

	public void setMaxMm(MethodMember maxMm) {
		this.maxMm = maxMm;
	}*/
	
}