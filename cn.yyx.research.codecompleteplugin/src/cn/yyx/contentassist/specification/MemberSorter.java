package cn.yyx.contentassist.specification;

public class MemberSorter implements Comparable<MemberSorter>{
	
	private double similarity = 0;
	private Object member = null;
	
	public MemberSorter(double similarity, Object member) {
		this.setSimilarity(similarity);
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
		return ((Double)(-similarity)).compareTo((Double)(-o.similarity));
	}
	
}