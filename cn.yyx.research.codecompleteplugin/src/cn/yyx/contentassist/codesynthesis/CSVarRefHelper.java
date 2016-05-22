package cn.yyx.contentassist.codesynthesis;

import java.util.Map;

import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.commonutils.VariableHT;

public class CSVarRefHelper {
	
	public static Map<String, String> GetAllTypeVariablePair(CSFlowLineQueue squeue, CSStatementHandler smthandler, int scope, int off)
	{
		Map<String, String> po = squeue.GetLastHandler().getScopeOffsetRefHandler().HandleCommonVariableRef(scope, off);
		if (scope == 0 && !(smthandler.getAoi().isInFieldLevel()))
		{
			VariableHT vht = squeue.BackSearchForLastIthVariableHolderAndTypeDeclaration(off);
			if (vht != null)
			{
				po.put(vht.getHoldertype(), vht.getHoldername());
			}
		}
		return po;
	}
	
	public static Map<String, String> GetAllFieldTypeVariablePair(CSFlowLineQueue squeue, CSStatementHandler smthandler, int scope, int off)
	{
		Map<String, String> po = squeue.GetLastHandler().getScopeOffsetRefHandler().HandleFieldVariableRef(scope, off);
		if (scope == 0 && smthandler.getAoi().isInFieldLevel())
		{
			VariableHT vht = squeue.BackSearchForLastIthVariableHolderAndTypeDeclaration(off);
			if (vht != null)
			{
				po.put(vht.getHoldertype(), vht.getHoldername());
			}
		}
		return po;
	}
	
}
