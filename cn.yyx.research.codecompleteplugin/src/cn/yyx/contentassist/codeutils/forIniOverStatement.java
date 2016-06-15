package cn.yyx.contentassist.codeutils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineBackTraceGenerationHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineHelper;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSForIniOverProperty;
import cn.yyx.contentassist.codesynthesis.data.CSForProperty;
import cn.yyx.contentassist.codesynthesis.data.DataStructureSignalMetaInfo;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.PrimitiveTypeConflict;
import cn.yyx.contentassist.codesynthesis.typeutil.SameTypeConflictException;
import cn.yyx.contentassist.commonutils.BackSearchResult;

public class forIniOverStatement extends rawForIniOverStatement implements SWrapper{
	
	statement smt = null;
	
	public forIniOverStatement(statement smt, String smtcode) {
		super(smtcode);
		this.smt = smt;
	}
	
	@Override
	public boolean CouldThoughtSame(OneCode t) {
		if (t instanceof forIniOverStatement)
		{
			return smt.CouldThoughtSame(((forIniOverStatement)t).smt);
		}
		if (t instanceof rawForIniOverStatement)
		{
			return true;
		}
		if (t instanceof statement)
		{
			return smt.CouldThoughtSame(t);
		}
		return false;
	}

	@Override
	public double Similarity(OneCode t) {
		if (t instanceof forIniOverStatement)
		{
			return smt.Similarity(((forIniOverStatement)t).smt);
		}
		if (t instanceof rawForIniOverStatement)
		{
			return 0.5;
		}
		if (t instanceof statement)
		{
			return smt.Similarity(t);
		}
		return 0;
	}
	
	@Override
	public List<FlowLineNode<CSFlowLineData>> HandleCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler)
			throws CodeSynthesisException {
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		Stack<Integer> signals = new Stack<Integer>();
		signals.push(DataStructureSignalMetaInfo.CommonForInitWaitingOver);
		BackSearchResult br = squeue.BackSearchForTheNextOfSpecialClass(CSForProperty.class, signals);
		if (!br.isValid())
		{
			throw new CodeSynthesisException("no for before ini over?");
		}
		List<FlowLineNode<CSFlowLineData>> smtls = smt.HandleCodeSynthesis(squeue, smthandler);
		smtls = CSFlowLineHelper.ConcateOneFlowLineList(null, smtls, ";");
		if (smtls == null || smtls.size() == 0)
		{
			return null;
		}
		Iterator<FlowLineNode<CSFlowLineData>> itr = smtls.iterator();
		boolean succeed = false;
		while (itr.hasNext())
		{
			FlowLineNode<CSFlowLineData> smtln = itr.next();
			CSFlowLineData smtdata = smtln.getData();
			if (!br.isSelfisneeded())
			{
				try {
					CSFlowLineBackTraceGenerationHelper.GenerateNotYetAddedSynthesisCode(squeue, smthandler, smtln, br.getCnode());
				} catch (SameTypeConflictException e) {
					if (succeed) {
						continue;
					} else {
						throw e;
					}
				} catch (PrimitiveTypeConflict e) {
					continue;
				}
			}
			smtdata.setCsep(new CSForIniOverProperty(null));
			result.add(smtln);
			succeed = true;
		}
		return result;
	}

	@Override
	public statement GetContent() {
		return smt;
	}
	
}