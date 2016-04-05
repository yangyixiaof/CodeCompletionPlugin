package cn.yyx.contentassist.codesynthesis;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codepredict.PredictMetaInfo;
import cn.yyx.contentassist.codesynthesis.flowline.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.typeutil.MethodTypeSignature;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeCheckHelper;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeResolver;
import cn.yyx.contentassist.codeutils.identifier;
import cn.yyx.contentassist.codeutils.referedExpression;
import cn.yyx.contentassist.codeutils.type;
import cn.yyx.contentassist.commonutils.AdditionalInfo;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.CheckUtil;
import cn.yyx.contentassist.commonutils.RefAndModifiedMember;
import cn.yyx.contentassist.commonutils.SimilarityHelper;
import cn.yyx.contentassist.commonutils.StringUtil;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.TypeCheck;
import cn.yyx.contentassist.specification.MembersOfAReference;
import cn.yyx.contentassist.specification.MethodMember;
import cn.yyx.contentassist.specification.SearchSpecificationOfAReference;
import cn.yyx.contentassist.specification.SpecificationHelper;

public class CodeSynthesisHelper {
	
	public static boolean HandleBreakContinueCodeSynthesis(identifier id, CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai, String wheretp)
	{
		StringBuilder fin = new StringBuilder(wheretp);
		CSNode csn = new CSNode(CSNodeType.TempUsed);
		boolean conflict = id.HandleCodeSynthesis(squeue, expected, handler, csn, null);
		if (conflict)
		{
			return true;
		}
		fin.append(wheretp + " " + csn.GetFirstDataWithoutTypeCheck());
		CSNode cs = new CSNode(CSNodeType.WholeStatement);
		cs.AddPossibleCandidates(fin.toString(), null);
		squeue.add(cs);
		return false;
	}
	
	public static String GenerateDimens(int count)
	{
		StringBuilder sb = new StringBuilder("");
		for (int i=0;i<count;i++)
		{
			sb.append("[]");
		}
		return sb.toString();
	}
	
	public static void HandleVarRefCodeSynthesis(Map<String, String> po, CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai)
	{
		if (ai != null && ai.getDirectlyMemberHint() != null)
		{
			String hint = ai.getDirectlyMemberHint();
			RefAndModifiedMember ramm = SpecificationHelper.GetMostLikelyRef(handler.getContextHandler(), po, hint, ai.isDirectlyMemberIsMethod());
			String ref = ramm.getRef();
			String member = ramm.getMember();
			String membertype = ramm.getMembertype();
			Class<?> c = TypeResolver.ResolveType(membertype, handler.getContextHandler().getJavacontext());
			TypeCheck tc = new TypeCheck();
			tc.setExpreturntype(membertype);
			tc.setExpreturntypeclass(c);
			result.AddOneData(ref + "." + member, tc);
		}
		else
		{
			Set<String> codes = po.keySet();
			Iterator<String> citr = codes.iterator();
			while (citr.hasNext())
			{
				String code = citr.next();
				String type = po.get(code);
				Class<?> c = TypeResolver.ResolveType(type, handler.getContextHandler().getJavacontext());
				TypeCheck tc = new TypeCheck();
				tc.setExpreturntype(type);
				tc.setExpreturntypeclass(c);
				result.AddOneData(code, tc);
			}
		}
	}
	
	public static void HandleIntersectionOrUnionType(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai, List<type> tps, String concator)
	{
		Iterator<type> itr = tps.iterator();
		type tp = itr.next();
		CSNode tp1 = new CSNode(CSNodeType.TempUsed);
		tp.HandleCodeSynthesis(squeue, expected, handler, tp1, ai);
		while (itr.hasNext())
		{
			type ttp = itr.next();
			CSNode tp2 = new CSNode(CSNodeType.TempUsed);
			ttp.HandleCodeSynthesis(squeue, expected, handler, tp2, ai);
			CSNode mgd = new CSNode(CSNodeType.TempUsed);
			mgd.setDatas(CSNodeHelper.ConcatTwoNodesDatas(tp1, tp2, concator, -1));
			tp1 = mgd;
		}
		result.setDatas(tp1.getDatas());
	}

	public static List<FlowLineNode<CSFlowLineData>> HandleMethodSpecificationInfer(CSFlowLineQueue squeue,
			CSMethodStatementHandler smthandler, String spechint, String beforemethodexp) {
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		MembersOfAReference res = SearchSpecificationOfAReference.SearchFunctionSpecificationByPrefix(spechint, squeue.GetLastHandler().getContextHandler().getJavacontext(), null);
		List<MethodMember> mms = res.getMmlist();
		Iterator<MethodMember> itr = mms.iterator();
		String cmp = StringUtil.GetContentBehindFirstWhiteSpace(spechint);
		while (itr.hasNext())
		{
			MethodMember mm = itr.next();
			String methodname = mm.getName();
			double sim = SimilarityHelper.ComputeTwoStringSimilarity(cmp, methodname);
			if (sim > PredictMetaInfo.MethodSimilarityThreshold)
			{
				MethodTypeSignature mts = TypeCheckHelper.TranslateMethodMember(mm, squeue.GetLastHandler().getContextHandler().getJavacontext());
				int id = squeue.GenerateNewNodeId();
				result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(id, smthandler.getSete(), ((beforemethodexp == null || beforemethodexp.equals("")) ? methodname : beforemethodexp + "." + methodname), null, mts.getReturntype(), false, squeue.GetLastHandler()), smthandler.getProb()));
				smthandler.AddMethodTypeSigById(id, mts);
			}
		}
		return result;
	}
	
	public static List<FlowLineNode<CSFlowLineData>> HandleClassInvokeCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler, referedExpression rexp)
			throws CodeSynthesisException {
		CheckUtil.CheckStatementHandlerIsMethodStatementHandler(smthandler);
		CSMethodStatementHandler realhandler = (CSMethodStatementHandler) smthandler;
		String mcode = realhandler.getMethodname();
		String rexpcode = null;
		if (rexp != null)
		{
			List<FlowLineNode<CSFlowLineData>> ls = rexp.HandleCodeSynthesis(squeue, realhandler);
			rexpcode = ls.get(0).getData().getData();
			mcode = rexpcode + "." + mcode;
		}
		return CodeSynthesisHelper.HandleMethodSpecificationInfer(squeue, realhandler, mcode, rexpcode);
	}
	
	/*public static boolean HandleMethodSpecificationInfer(CodeSynthesisQueue squeue, Stack<TypeCheck> expected, SynthesisHandler handler,
			CSNode result, AdditionalInfo ai, String spechint)
	{
		MembersOfAReference res = SearchSpecificationOfAReference.SearchFunctionSpecificationByPrefix(spechint, handler.getContextHandler().getJavacontext(), null);
		List<MethodMember> mms = res.getMmlist();
		Iterator<MethodMember> itr = mms.iterator();
		String cmp = StringUtil.GetContentBehindFirstWhiteSpace(spechint);
		while (itr.hasNext())
		{
			MethodMember mm = itr.next();
			String methodname = mm.getName();
			double sim = SimilarityHelper.ComputeTwoStringSimilarity(cmp, methodname);
			if (sim > 0.8)
			{
				result.AddOneData(spechint, TypeCheckHelper.TranslateMethodMember(mm, handler.getContextHandler().getJavacontext()));
			}
		}
		return false;
	}*/
	
}