package cn.yyx.contentassist.codesynthesis.typeutil;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codeutils.type;
import cn.yyx.contentassist.codeutils.virtualInferredType;
import cn.yyx.contentassist.parsehelper.ComplexParser;

public class TypeResolver {
	
	// public static final int MaxTryTimes = 3;
	// public static YJCache<LinkedList<CCType>> classcache = new YJCache<LinkedList<CCType>>();
	
	public static char seed = 'A';
	
	/**
	 * this could return an empty list.
	 * @param type
	 * @param squeue
	 * @param smthandler
	 * @return
	 */
	public static LinkedList<CCType> ResolveType(String type, CSFlowLineQueue squeue, CSStatementHandler smthandler) {
		// debugging code, not remove.
		if (type.startsWith("Map<?"))
		{
			System.err.println("debug:is at Map<?.");
		}
		type tp = ComplexParser.GetType(type);
		LinkedList<CCType> res = new LinkedList<CCType>();
		List<FlowLineNode<CSFlowLineData>> tpls = null;
		try {
			if (tp instanceof virtualInferredType) {
				tpls = ((virtualInferredType) tp).HandleVirtualInferredTypeCodeSynthesis(squeue, smthandler, seed);
				seed++;
				if (seed > 'Z')
				{
					seed = 'A';
				}
			} else {
				tpls = tp.HandleCodeSynthesis(squeue, smthandler);
			}
		} catch (CodeSynthesisException e) {
			e.printStackTrace();
			System.exit(1);
		}
		if (tpls != null) {
			Iterator<FlowLineNode<CSFlowLineData>> itr = tpls.iterator();
			while (itr.hasNext()) {
				FlowLineNode<CSFlowLineData> fln = itr.next();
				CSFlowLineData fdata = fln.getData();
				res.add(fdata.getDcls());
			}
		}
		return res;
	}

	public static List<LinkedList<CCType>> ResolveType(List<String> types, CSFlowLineQueue squeue,
			CSStatementHandler smthandler) {
		List<LinkedList<CCType>> result = new LinkedList<LinkedList<CCType>>();
		Iterator<String> itr = types.iterator();
		while (itr.hasNext()) {
			String tp = itr.next();
			result.add(ResolveType(tp, squeue, smthandler));
		}
		return result;
	}

}