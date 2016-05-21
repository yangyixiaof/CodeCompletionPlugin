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
import cn.yyx.contentassist.commonutils.YJCache;
import cn.yyx.contentassist.parsehelper.ComplexParser;

public class TypeResolver {
	
	public static final int MaxTryTimes = 3;
	
	public static YJCache<LinkedList<CCType>> classcache = new YJCache<LinkedList<CCType>>();
	
	// TODO all specification must be sorted according to their own feature.
	
	/**
	 * this could return an empty list.
	 * @param type
	 * @param squeue
	 * @param smthandler
	 * @return
	 */
	public static LinkedList<CCType> ResolveType(String type, CSFlowLineQueue squeue, CSStatementHandler smthandler) {
		type tp = ComplexParser.GetType(type);
		LinkedList<CCType> clss = classcache.GetCachedContent(type);
		if (clss != null) {
			return clss;
		}
		LinkedList<CCType> res = new LinkedList<CCType>();
		List<FlowLineNode<CSFlowLineData>> tpls = null;
		try {
			tpls = tp.HandleCodeSynthesis(squeue, smthandler);
		} catch (CodeSynthesisException e) {
			e.printStackTrace();
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