package cn.yyx.contentassist.codesynthesis;

import java.util.Map;
import java.util.TreeMap;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.commonutils.VariableHT;
import cn.yyx.research.language.simplified.JDTManager.OffsetOutOfScopeException;

public class CSVarRefHelper {
	
	public static Map<String, String> GetAllTypeVariablePair(CSFlowLineQueue squeue, CSStatementHandler smthandler, int scope, int off) throws CodeSynthesisException
	{
		VariableHT vht = null;
		if (!(smthandler.getAoi().isInFieldLevel())) // scope == 0 && 
		{
			vht = squeue.BackSearchHandleLambdaScope(scope, off);
		}
		Map<String, String> pofield = null;
		Map<String, String> pores = new TreeMap<String, String>();
		Map<String, String> po = null;
		try {
			pofield = squeue.GetLastHandler().getScopeOffsetRefHandler().HandleFieldVariableRef(null, -1, scope, off);
			if (vht != null) {
				po = squeue.GetLastHandler().getScopeOffsetRefHandler().HandleCommonVariableRef(vht.getTpremains(), vht.getTrimedscope(), vht.getTrimedscope(), off);
			} else {
				po = squeue.GetLastHandler().getScopeOffsetRefHandler().HandleCommonVariableRef(null, -1, scope, off);
			}
		} catch (OffsetOutOfScopeException e) {
			// e.printStackTrace();
			throw new CodeSynthesisException(e.getMessage());
		}
		if (pofield != null)
		{
			pores.putAll(pofield);
		}
		if (po != null)
		{
			pores.putAll(po);
		}
		if (vht != null)
		{
			pores.putAll(vht.getTpvarname());
		}
		// po.putAll(pofield);
		return pores;
	}
	
	public static Map<String, String> GetAllFieldTypeVariablePair(CSFlowLineQueue squeue, CSStatementHandler smthandler, int scope, int off) throws CodeSynthesisException
	{
		VariableHT vht = null;
		if (smthandler.getAoi().isInFieldLevel()) // scope == 0 && 
		{
			vht = squeue.BackSearchHandleLambdaScope(scope, off);
		}
		Map<String, String> povar = null;
		Map<String, String> pores = new TreeMap<String, String>();
		Map<String, String> po = null;
		try {
			povar = squeue.GetLastHandler().getScopeOffsetRefHandler().HandleCommonVariableRef(null, -1, scope, off);
			if (vht != null) {
				po = squeue.GetLastHandler().getScopeOffsetRefHandler().HandleFieldVariableRef(vht.getTpremains(), vht.getTrimedscope(), vht.getTrimedscope(), off);
			} else {
				po = squeue.GetLastHandler().getScopeOffsetRefHandler().HandleFieldVariableRef(null, -1, scope, off);
			}
		} catch (OffsetOutOfScopeException e) {
			// e.printStackTrace();
			throw new CodeSynthesisException(e.getMessage());
		}
		if (povar != null)
		{
			pores.putAll(povar);
		}
		if (po != null)
		{
			pores.putAll(po);
		}
		if (vht != null)
		{
			// vht.getTpvarname(), 
			pores.putAll(vht.getTpvarname());
		}
		// po.putAll(povar);
		/*if (vht.getHoldername() != null)
		{
			po.put(vht.getHoldertype(), vht.getHoldername());
		}*/
		return pores;
	}
	
}