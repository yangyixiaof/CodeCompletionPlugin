package cn.yyx.contentassist.codesynthesis;

import java.util.Map;
import java.util.TreeMap;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSDirectLambdaHandler;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.commonutils.VariableHT;
import cn.yyx.research.language.simplified.JDTManager.OffsetOutOfScopeException;
import cn.yyx.research.language.simplified.JDTManager.ScopeOffsetResult;

public class CSVarRefHelper {
	
	// Map<String, String>
	public static ScopeOffsetResult GetAllTypeVariablePair(CSFlowLineQueue squeue, CSStatementHandler smthandler, int scope, int off) throws CodeSynthesisException
	{
		VariableHT vht = null;
		VariableHT directvht = null;
		if (smthandler instanceof CSDirectLambdaHandler)
		{
			directvht = squeue.DirectHandleLambdaScope((CSDirectLambdaHandler)smthandler, scope, off);
			scope--;
		}
		if (!(smthandler.getAoi().isInFieldLevel())) // scope == 0 && 
		{
			vht = squeue.BackSearchHandleLambdaScope(scope, off);
		}
		Map<String, String> pores1 = new TreeMap<String, String>();
		Map<String, Long> pores2 = new TreeMap<String, Long>();
		ScopeOffsetResult pofield = null;
		ScopeOffsetResult po = null;
		try {
			pofield = squeue.GetLastHandler().getScopeOffsetRefHandler().HandleFieldVariableRef(null, -1, scope, off);
		} catch (OffsetOutOfScopeException e) {
			// e.printStackTrace();
			// throw new CodeSynthesisException(e.getMessage());
		}
		try {
			if (vht != null) {
				po = squeue.GetLastHandler().getScopeOffsetRefHandler().HandleCommonVariableRef(vht.getTpremains(), vht.getTrimedscope(), vht.getTrimedscope(), off);
			} else {
				po = squeue.GetLastHandler().getScopeOffsetRefHandler().HandleCommonVariableRef(null, -1, scope, off);
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
			pores1.putAll(po.getSor());
			pores2.putAll(po.getSol());
		}
		if (vht != null)
		{
			pores1.putAll(vht.getTpvarname());
		}
		if (directvht != null)
		{
			pores1.putAll(directvht.getTpvarname());
		}
		// po.putAll(pofield);
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
		if (smthandler.getAoi().isInFieldLevel()) // scope == 0 && 
		{
			vht = squeue.BackSearchHandleLambdaScope(scope, off);
		}
		Map<String, String> pores1 = new TreeMap<String, String>();
		Map<String, Long> pores2 = new TreeMap<String, Long>();
		ScopeOffsetResult povar = null;
		ScopeOffsetResult po = null;
		try {
			povar = squeue.GetLastHandler().getScopeOffsetRefHandler().HandleCommonVariableRef(null, -1, scope, off);
		} catch (OffsetOutOfScopeException e) {
			// e.printStackTrace();
			// throw new CodeSynthesisException(e.getMessage());
		}
		try {
			if (vht != null) {
				po = squeue.GetLastHandler().getScopeOffsetRefHandler().HandleFieldVariableRef(vht.getTpremains(), vht.getTrimedscope(), vht.getTrimedscope(), off);
			} else {
				po = squeue.GetLastHandler().getScopeOffsetRefHandler().HandleFieldVariableRef(null, -1, scope, off);
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
			pores1.putAll(po.getSor());
			pores2.putAll(po.getSol());
		}
		if (vht != null)
		{
			// vht.getTpvarname(), 
			pores1.putAll(vht.getTpvarname());
		}
		if (directvht != null)
		{
			pores1.putAll(directvht.getTpvarname());
		}
		// po.putAll(povar);
		/*if (vht.getHoldername() != null)
		{
			po.put(vht.getHoldertype(), vht.getHoldername());
		}*/
		return new ScopeOffsetResult(pores1, pores2);
	}
	
}