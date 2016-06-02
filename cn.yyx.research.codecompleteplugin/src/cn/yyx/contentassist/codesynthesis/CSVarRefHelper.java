package cn.yyx.contentassist.codesynthesis;

import java.util.Map;

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
			vht = squeue.BackSearchForLastIthVariableHolderAndTypeDeclaration(scope, off);
		}
		Map<String, String> pofield = null;
		Map<String, String> po = null;
		try {
			pofield = squeue.GetLastHandler().getScopeOffsetRefHandler().HandleFieldVariableRef(vht.getHoldertype(), vht.getAllvh(), scope, off);
			po = squeue.GetLastHandler().getScopeOffsetRefHandler().HandleCommonVariableRef(vht.getHoldertype(), vht.getAllvh(), scope, off);
		} catch (OffsetOutOfScopeException e) {
			// e.printStackTrace();
			throw new CodeSynthesisException(e.getMessage());
		}
		po.putAll(pofield);
		po.putAll(vht.getOhs());
		return po;
	}
	
	public static Map<String, String> GetAllFieldTypeVariablePair(CSFlowLineQueue squeue, CSStatementHandler smthandler, int scope, int off) throws CodeSynthesisException
	{
		VariableHT vht = null;
		if (smthandler.getAoi().isInFieldLevel()) // scope == 0 && 
		{
			vht = squeue.BackSearchForLastIthVariableHolderAndTypeDeclaration(scope, off);
		}
		Map<String, String> povar = null;
		Map<String, String> po = null;
		try {
			povar = squeue.GetLastHandler().getScopeOffsetRefHandler().HandleCommonVariableRef(vht.getHoldertype(), vht.getAllvh(), scope, off);
			po = squeue.GetLastHandler().getScopeOffsetRefHandler().HandleFieldVariableRef(vht.getHoldertype(), vht.getAllvh(), scope, off);
		} catch (OffsetOutOfScopeException e) {
			// e.printStackTrace();
			throw new CodeSynthesisException(e.getMessage());
		}
		po.putAll(povar);
		if (vht.getHoldername() != null)
		{
			po.put(vht.getHoldertype(), vht.getHoldername());
		}
		return po;
	}
	
}