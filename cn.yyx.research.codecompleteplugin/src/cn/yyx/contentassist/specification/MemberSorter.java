package cn.yyx.contentassist.specification;

public class MemberSorter implements Comparable<MemberSorter>{
	
	private Double similarity = new Double(0);
	private String memberSig = null;
	private Object member = null;
	
	public MemberSorter(Double similarity, String membersig, Object member) {
		this.setSimilarity(similarity);
		this.setMemberSig(membersig);
		this.setMember(member);
	}

	public double getSimilarity() {
		return similarity;
	}

	public void setSimilarity(double similarity) {
		this.similarity = similarity;
	}

	public Object getMember() {
		return member;
	}

	public void setMember(Object member) {
		this.member = member;
	}

	@Override
	public int compareTo(MemberSorter o) {
		int simcomp = similarity.compareTo(o.similarity);
		if (simcomp == 0)
		{
			return memberSig.compareTo(o.memberSig);
		}
		return -simcomp;
	}

	public String getMemberSig() {
		return memberSig;
	}

	public void setMemberSig(String memberSig) {
		this.memberSig = memberSig;
	}
	
}