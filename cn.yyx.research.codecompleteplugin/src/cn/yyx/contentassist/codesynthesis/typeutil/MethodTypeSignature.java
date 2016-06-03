package cn.yyx.contentassist.codesynthesis.typeutil;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.specification.MethodMember;

public class MethodTypeSignature {
	
	private List<CCType> returntype = null;
	private List<LinkedList<CCType>> argtypes = null;
	
	private boolean lastHasVarArg = false;

	public static MethodTypeSignature TranslateMethodMember(MethodMember mm, CSFlowLineQueue squeue,
			CSStatementHandler smthandler)
	{
		boolean hasvararg = false;
		String rttype = mm.getReturntype();
		// tc.setExpreturntype(rttype);
		LinkedList<CCType> c = TypeResolver.ResolveType(rttype, squeue, smthandler);
		// tc.setExpreturntypeclass(c);
		LinkedList<String> tplist = mm.getArgtypelist();
		// tc.setExpargstypes(tplist);
		List<LinkedList<CCType>> tpclist = new LinkedList<LinkedList<CCType>>();
		Iterator<String> itr = tplist.iterator();
		while (itr.hasNext())
		{
			String tp = itr.next().trim();
			if (tp.trim().endsWith("..."))
			{
				hasvararg = true;
			}
			// boolean handlevararg = false;
			/*if (tp.endsWith("..."))
			{
				handlevararg = true;
				tp = tp.substring(0, tp.indexOf("...")).trim();
			}*/
			LinkedList<CCType> tpc = TypeResolver.ResolveType(tp, squeue, smthandler);
			/*LinkedList<CCType> result = tpc;
			if (handlevararg) {
				result = new LinkedList<CCType>();
				Iterator<CCType> tpcitr = tpc.iterator();
				while (tpcitr.hasNext())
				{
					CCType cct = tpcitr.next();
					result.add(new VarArgCCType(cct));
				}
			}*/
			tpclist.add(tpc);
		}
		// tc.setExpargstypesclasses(tpclist);
		MethodTypeSignature tc = new MethodTypeSignature(hasvararg, c, tpclist);
		return tc;
	}
	
	public MethodTypeSignature(boolean lastHasVarArg, List<CCType> returntype, List<LinkedList<CCType>> argtypes) {
		this.setReturntype(returntype);
		this.setArgtypes(argtypes);
		this.setLastHasVarArg(lastHasVarArg);
	}

	public List<CCType> getReturntype() {
		return returntype;
	}

	public void setReturntype(List<CCType> returntype) {
		this.returntype = returntype;
	}

	public List<LinkedList<CCType>> getArgtypes() {
		return argtypes;
	}

	public void setArgtypes(List<LinkedList<CCType>> argtypes) {
		this.argtypes = argtypes;
	}


	public boolean isLastHasVarArg() {
		return lastHasVarArg;
	}


	public void setLastHasVarArg(boolean lastHasVarArg) {
		this.lastHasVarArg = lastHasVarArg;
	}
	
}