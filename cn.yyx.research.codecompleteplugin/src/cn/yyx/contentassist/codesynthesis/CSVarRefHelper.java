package cn.yyx.contentassist.codesynthesis;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSDirectLambdaHandler;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.commonutils.VariableHT;
import cn.yyx.research.language.simplified.JDTManager.OffsetOutOfScopeException;
import cn.yyx.research.language.simplified.JDTManager.ScopeOffsetResult;
import cn.yyx.research.language.simplified.JDTManager.UniqueOrder;

public class CSVarRefHelper {
	
	// Map<String, String>
	public static ScopeOffsetResult GetAllCommonTypeVariablePair(CSFlowLineQueue squeue, CSStatementHandler smthandler, int scope, int off) throws CodeSynthesisException
	{
		VariableHT vht = null;
		VariableHT directvht = null;
		if (smthandler instanceof CSDirectLambdaHandler)
		{
			directvht = squeue.DirectHandleLambdaScope((CSDirectLambdaHandler)smthandler, scope, off);
			scope--;
		}
		int trimscope = squeue.BackSearchHandleLambdaScopeAndForScopeAndInnerDeclaration(scope);
		if (!(smthandler.getAoi().isInFieldLevel())) // scope == 0 && 
		{
			vht = squeue.BackSearchHandleLambdaScopeAndForScopeAndInnerDeclaration(scope, off);
		}
		Map<String, String> pores1 = new TreeMap<String, String>();
		Map<String, Long> pores2 = new TreeMap<String, Long>();
		ScopeOffsetResult pofield = null;
		ScopeOffsetResult po = null;
		try {
			pofield = squeue.GetLastHandler().getScopeOffsetRefHandler().HandleFieldVariableRef(null, -1, trimscope, off); // scope
		} catch (OffsetOutOfScopeException e) {
			// e.printStackTrace();
			// throw new CodeSynthesisException(e.getMessage());
		}
		try {
			if (vht != null) {
				po = squeue.GetLastHandler().getScopeOffsetRefHandler().HandleCommonVariableRef(vht.getTpremains(), vht.getTrimedscope(), vht.getTrimedscope(), off);
			} else {
				po = squeue.GetLastHandler().getScopeOffsetRefHandler().HandleCommonVariableRef(null, -1, trimscope, off); // scope
			}
		} catch (OffsetOutOfScopeException e) {
			// e.printStackTrace();
			// throw new CodeSynthesisException(e.getMessage());
		}
		if (pofield != null)
		{
			pores1.putAll(pofield.getSor());
			pores2.putAll(pofield.getSol());
		}
		if (po != null)
		{
			po.IncreaseOrder(UniqueOrder.CurrentOrder()/2);
			pores1.putAll(po.getSor());
			pores2.putAll(po.getSol());
		}
		if (vht != null)
		{
			IteratePutOrdInPores1AndPutNullInPores2(vht.getTpvarname(), pores1, pores2);
		}
		if (directvht != null)
		{
			IteratePutOrdInPores1AndPutNullInPores2(directvht.getTpvarname(), pores1, pores2);
		}
		return new ScopeOffsetResult(pores1, pores2);
	}
	
	// Map<String, String>
	public static ScopeOffsetResult GetAllFieldTypeVariablePair(CSFlowLineQueue squeue, CSStatementHandler smthandler, int scope, int off) throws CodeSynthesisException
	{
		VariableHT vht = null;
		VariableHT directvht = null;
		if (smthandler instanceof CSDirectLambdaHandler)
		{
			directvht = squeue.DirectHandleLambdaScope((CSDirectLambdaHandler)smthandler, scope, off);
			scope--;
		}
		int trimscope = squeue.BackSearchHandleLambdaScopeAndForScopeAndInnerDeclaration(scope);
		if (smthandler.getAoi().isInFieldLevel()) // scope == 0 && 
		{
			vht = squeue.BackSearchHandleLambdaScopeAndForScopeAndInnerDeclaration(scope, off);
		}
		Map<String, String> pores1 = new TreeMap<String, String>();
		Map<String, Long> pores2 = new TreeMap<String, Long>();
		ScopeOffsetResult povar = null;
		ScopeOffsetResult po = null;
		try {
			povar = squeue.GetLastHandler().getScopeOffsetRefHandler().HandleCommonVariableRef(null, -1, trimscope, off); // scope
		} catch (OffsetOutOfScopeException e) {
			// e.printStackTrace();
			// throw new CodeSynthesisException(e.getMessage());
		}
		try {
			if (vht != null) {
				po = squeue.GetLastHandler().getScopeOffsetRefHandler().HandleFieldVariableRef(vht.getTpremains(), vht.getTrimedscope(), vht.getTrimedscope(), off);
			} else {
				po = squeue.GetLastHandler().getScopeOffsetRefHandler().HandleFieldVariableRef(null, -1, trimscope, off); // scope
			}
		} catch (OffsetOutOfScopeException e) {
			// e.printStackTrace();
			// throw new CodeSynthesisException(e.getMessage());
		}
		if (povar != null)
		{
			pores1.putAll(povar.getSor());
			pores2.putAll(povar.getSol());
		}
		if (po != null)
		{
			po.IncreaseOrder(UniqueOrder.CurrentOrder()/2);
			pores1.putAll(po.getSor());
			pores2.putAll(po.getSol());
		}
		if (vht != null)
		{
			IteratePutOrdInPores1AndPutNullInPores2(vht.getTpvarname(), pores1, pores2);
		}
		if (directvht != null)
		{
			IteratePutOrdInPores1AndPutNullInPores2(directvht.getTpvarname(), pores1, pores2);
		}
		return new ScopeOffsetResult(pores1, pores2);
	}
	
	private static void IteratePutOrdInPores1AndPutNullInPores2(Map<String, String> tpvarname, Map<String, String> pores1,
			Map<String, Long> pores2) {
		Set<String> keys = tpvarname.keySet();
		Iterator<String> kitr = keys.iterator();
		while (kitr.hasNext())
		{
			String tp = kitr.next();
			String var = tpvarname.get(tp);
			pores1.put(tp, var);
			pores2.put(tp, null);
		}
	}
	
}