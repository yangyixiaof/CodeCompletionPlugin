package cn.yyx.contentassist.codeutils;

import java.util.Stack;

import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNode;
import cn.yyx.contentassist.commonutils.CodeSynthesisQueue;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;

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

	@Override
	public boolean HandleCodeSynthesis(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai) {
		TypeCheck tc = new TypeCheck();
		tc.setExpreturntype(text);
		try {
			tc.setExpreturntypeclass(Class.forName(text));
		} catch (ClassNotFoundException e) {
			System.err.println("Unrecognized Primitive Type:" + text);
			System.exit(1);
			e.printStackTrace();
		}
		result.AddOneData(text, tc);
		return false;
	}
	
}