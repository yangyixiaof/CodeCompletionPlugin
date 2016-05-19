package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.CCType;

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

	/*@Override
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
	}*/

	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		try {
			CCType cct = new CCType(ParsePrimitiveType(text), text);
			result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), text, cct, false, false, null, null, squeue.GetLastHandler()), smthandler.getProb()));
		} catch (CodeSynthesisException e) {
			throw new CodeSynthesisException("Primitive type can not be resolved:" + text);
		}
		return result;
	}
	
	private Class<?> ParsePrimitiveType(String text) throws CodeSynthesisException
	{
		text = text.trim();
		if (text.equals("int"))
		{
			return int.class;
		}
		if (text.equals("float"))
		{
			return float.class;
		}
		if (text.equals("double"))
		{
			return double.class;
		}
		if (text.equals("boolean"))
		{
			return boolean.class;
		}
		if (text.equals("byte"))
		{
			return byte.class;
		}
		if (text.equals("short"))
		{
			return short.class;
		}
		if (text.equals("long"))
		{
			return long.class;
		}
		if (text.equals("char"))
		{
			return char.class;
		}
		throw new CodeSynthesisException("What the fuck? Primitive type can not be parsed.");
	}
	
}