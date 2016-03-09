package cn.yyx.contentassist.codeutils;

public class primitiveType extends type{
	
	String text = null;
	
	public primitiveType(String text) {
		this.text = text;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof primitiveType)
		{
			if (text.equals(((primitiveType) t).text))
			{
				return true;
			}
			if (text.equals("float") || text.equals("double"))
			{
				String tts = ((primitiveType) t).text;
				if (tts.equals("float") || tts.equals("double"))
				{
					return true;
				}
			}
			if (text.equals("byte") || text.equals("short") || text.equals("int") || text.equals("long"))
			{
				String tts = ((primitiveType) t).text;
				if (tts.equals("byte") || tts.equals("short") || tts.equals("int") || tts.equals("long"))
				{
					return true;
				}
			}
		}
		return false;
	}
	
	@Override
	public double Similarity(OneCode t) {
		if (t instanceof primitiveType)
		{
			if (text.equals(((primitiveType) t).text))
			{
				return 1;
			}
			if (text.equals("float") || text.equals("double"))
			{
				String tts = ((primitiveType) t).text;
				if (tts.equals("float") || tts.equals("double"))
				{
					return 1;
				}
			}
			if (text.equals("byte") || text.equals("short") || text.equals("int") || text.equals("long"))
			{
				String tts = ((primitiveType) t).text;
				if (tts.equals("byte") || tts.equals("short") || tts.equals("int") || tts.equals("long"))
				{
					return 1;
				}
			}
		}
		return 0;
	}
	
}