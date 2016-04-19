package cn.yyx.contentassist.codeutils;

import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;

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
			result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), text, Class.forName(text), false, false, null, null, squeue.GetLastHandler()), smthandler.getProb()));
		} catch (ClassNotFoundException e) {
			throw new CodeSynthesisException("Primitive type can not be resolved:" + text);
		}
		return result;
	}
	
}