package cn.yyx.contentassist.codeutils;

public class idRawLetter extends identifier{
	
	String text = null;
	
	public idRawLetter(String text) {
		this.text = text;
	}

	@Override
	public boolean CouldThoughtSame(OneCode t) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		// TODO Auto-generated method stub
		return 0;
	}

}
