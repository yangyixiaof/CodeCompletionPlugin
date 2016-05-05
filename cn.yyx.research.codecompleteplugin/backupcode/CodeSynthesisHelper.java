package cn.yyx.contentassist.codesynthesis;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codepredict.PredictMetaInfo;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSPrData;
import cn.yyx.contentassist.codesynthesis.data.CSPsData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSMethodStatementHandler;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.MethodTypeSignature;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeCheckHelper;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeResolver;
import cn.yyx.contentassist.codeutils.OneCode;
import cn.yyx.contentassist.codeutils.argumentList;
import cn.yyx.contentassist.codeutils.identifier;
import cn.yyx.contentassist.codeutils.referedExpression;
import cn.yyx.contentassist.codeutils.type;
import cn.yyx.contentassist.commonutils.NameConvention;
import cn.yyx.contentassist.commonutils.RefAndModifiedMember;
import cn.yyx.contentassist.commonutils.SimilarityHelper;
import cn.yyx.contentassist.commonutils.StringUtil;
import cn.yyx.contentassist.specification.FieldMember;
import cn.yyx.contentassist.specification.MembersOfAReference;
import cn.yyx.contentassist.specification.MethodMember;
import cn.yyx.contentassist.specification.SearchSpecificationOfAReference;
import cn.yyx.contentassist.specification.SpecificationHelper;
import cn.yyx.contentassist.specification.TypeMember;

public class CodeSynthesisHelper {
	
	public static List<FlowLineNode<CSFlowLineData>> HandleBreakContinueCodeSynthesis(identifier id, CSFlowLineQueue squeue, CSStatementHandler smthandler,
			String whichprefix) throws CodeSynthesisException
	{
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		StringBuilder fin = new StringBuilder(whichprefix);
		if (id != null)
		{
			List<FlowLineNode<CSFlowLineData>> ls = id.HandleCodeSynthesis(squeue, smthandler);
			fin.append(" " + ls.get(0).getData().getData());
		}
		result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), fin.toString(), void.class, false, false, null, null, squeue.GetLastHandler()), smthandler.getProb()));
		return result;
	}
	
	public static List<FlowLineNode<CSFlowLineData>> HandleVarRefCodeSynthesis(Map<String, String> po, CSFlowLineQueue squeue, CSStatementHandler smthandler)
	{
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		Set<String> codes = po.keySet();
		Iterator<String> citr = codes.iterator();
		while (citr.hasNext())
		{
			String code = citr.next();
			String type = po.get(code);
			Class<?> c = TypeResolver.ResolveType(type, squeue.GetLastHandler().getContextHandler().getJavacontext());
			result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), code, c, false, false, null, null, squeue.GetLastHandler()), smthandler.getProb()));
		}
		return result;
	}
	
	public static List<FlowLineNode<CSFlowLineData>> HandleVarRefInferredField(Map<String, String> po, CSFlowLineQueue squeue, CSStatementHandler smthandler, String reservedword, List<FlowLineNode<CSFlowLineData>> expectedinfer)
	{
		// this function doesn't need to handle CSMethodStatementHandler, CSMethodStatementHandler has been handled in method invocation.
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		// if ((smthandler instanceof CSFieldAccessStatementHandler) || (smthandler instanceof CSMethodStatementHandler) || (smthandler instanceof CSMethodReferenceStatementHandler))
		// {
			boolean ismethod = false;
			String hint = null;
			String concator = null;
			if (smthandler instanceof CSFieldAccessStatementHandler)
			{
				hint = ((CSFieldAccessStatementHandler) smthandler).getField();
			}
			if (smthandler instanceof CSMethodStatementHandler)
			{
				hint = ((CSMethodStatementHandler)smthandler).getMethodname();
				ismethod = true;
				concator = ".";
			}
			if (smthandler instanceof CSMethodReferenceStatementHandler)
			{
				hint = ((CSMethodReferenceStatementHandler)smthandler).getField();
				ismethod = true;
				concator = "::";
			}
			RefAndModifiedMember ramm = SpecificationHelper.GetMostLikelyRef(squeue.GetLastHandler().getContextHandler(), po, hint, ismethod, concator);
			String ref = ramm.getRef();
			String member = ramm.getMember();
			String membertype = ramm.getMembertype();
			Class<?> c = TypeResolver.ResolveType(membertype, squeue.GetLastHandler().getContextHandler().getJavacontext());
			FlowLineNode<CSFlowLineData> sqdata = new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), ref + "." + member, c, false, false, null, null, squeue.GetLastHandler()), smthandler.getProb());
			if (smthandler instanceof CSMethodStatementHandler)
			{
				MethodMember mm = ramm.getMaxMm();
				MethodTypeSignature mts = MethodTypeSignature.GenerateMethodTypeSignature(mm, squeue.GetLastHandler().getContextHandler().getJavacontext());
				((CSMethodStatementHandler)smthandler).AddMethodTypeSigById(sqdata.getData().getId(), mts);
			}
			result.add(sqdata);
		// }
		return result;
	}
	
	public static List<FlowLineNode<CSFlowLineData>> HandleMultipleConcateType(CSFlowLineQueue squeue, CSStatementHandler smthandler, List<type> tps, String concator) throws CodeSynthesisException
	{
		Iterator<type> itr = tps.iterator();
		type tp = itr.next();
		List<FlowLineNode<CSFlowLineData>> ls = tp.HandleCodeSynthesis(squeue, smthandler);
		while (itr.hasNext())
		{
			type ttp = itr.next();
			List<FlowLineNode<CSFlowLineData>> tmpls = ttp.HandleCodeSynthesis(squeue, smthandler);
			ls = CSFlowLineHelper.ForwardMerge(null, ls, concator, tmpls, null, squeue, smthandler, null, null);
		}
		return ls;
	}
	
	public static List<FlowLineNode<CSFlowLineData>> HandleMethodSpecificationInfer(CSFlowLineQueue squeue,
			CSStatementHandler smthandler, String spechint, String beforemethodexp, Map<String, MethodTypeSignature> mts) {
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
				MethodTypeSignature mtsone = TypeCheckHelper.TranslateMethodMember(mm, squeue.GetLastHandler().getContextHandler().getJavacontext());
				int id = squeue.GenerateNewNodeId();
				result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(id, smthandler.getSete(), ((beforemethodexp == null || beforemethodexp.equals("")) ? methodname : beforemethodexp + "." + methodname), mtsone.getReturntype(), false, false, null, null, squeue.GetLastHandler()), smthandler.getProb()));
				mts.put(id+"", mtsone);
			}
		}
		return result;
	}
	
	public static List<FlowLineNode<CSFlowLineData>> HandleClassInvokeCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler, referedExpression rexp, String between, String methodname, Map<String, MethodTypeSignature> mts)
			throws CodeSynthesisException {
		// CheckUtil.CheckStatementHandlerIsMethodStatementHandler(smthandler);
		// CSMethodStatementHandler realhandler = (CSMethodStatementHandler) smthandler;
		// String mcode = realhandler.getMethodname();
		String rexpcode = null;
		String mcode = methodname;
		if (rexp != null)
		{
			List<FlowLineNode<CSFlowLineData>> ls = rexp.HandleCodeSynthesis(squeue, smthandler);
			// TODO here should not just get the first element. such as commonFieldRef, they can not distinguish their priority.
			rexpcode = ls.get(0).getData().getData();
			mcode = rexpcode + "." + ((between == null || between.equals("")) ? "" : between) + mcode;
		}
		return CodeSynthesisHelper.HandleMethodSpecificationInfer(squeue, smthandler, mcode, rexpcode, mts);
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
	
	public static List<FlowLineNode<CSFlowLineData>> HandleTypeSpecificationInfer(List<FlowLineNode<CSFlowLineData>> tmp, List<FlowLineNode<CSFlowLineData>> tpls, CSFlowLineQueue squeue, CSStatementHandler smthandler) throws CodeSynthesisException
	{
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		Iterator<FlowLineNode<CSFlowLineData>> itr = tmp.iterator();
		while (itr.hasNext())
		{
			FlowLineNode<CSFlowLineData> fln = itr.next();
			List<TypeMember> tmm = SearchSpecificationOfAReference.SearchTypeSpecificationByPrefix(fln.getData().getData() + ".", squeue.GetLastHandler().getContextHandler().getJavacontext(), null);
			Iterator<TypeMember> titr = tmm.iterator();
			while (titr.hasNext())
			{
				TypeMember tm = titr.next();
				String cmp = tm.getType();
				Iterator<FlowLineNode<CSFlowLineData>> tpitr = tpls.iterator();
				while (tpitr.hasNext())
				{
					FlowLineNode<CSFlowLineData> tp = tpitr.next();
					String cmped = tp.getData().getData();
					if (SimilarityHelper.ComputeTwoStringSimilarity(cmp, cmped) > PredictMetaInfo.TwoStringSimilarThreshold)
					{
						result.add(CSFlowLineHelper.ConcateTwoFlowLineNode(null, fln, ".", tp, null, squeue, smthandler, null, null));
					}
				}
			}
		}
		return result;
	}
	
	public static List<FlowLineNode<CSFlowLineData>> HandleFieldSpecificationInfer(List<FlowLineNode<CSFlowLineData>> tmp, List<FlowLineNode<CSFlowLineData>> idls, CSFlowLineQueue squeue, CSStatementHandler smthandler, String concator) throws CodeSynthesisException
	{
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		Iterator<FlowLineNode<CSFlowLineData>> itr = tmp.iterator();
		while (itr.hasNext())
		{
			FlowLineNode<CSFlowLineData> fln = itr.next();
			List<FieldMember> fmm = SearchSpecificationOfAReference.SearchFieldSpecificationByPrefix(fln.getData().getData() + concator, squeue.GetLastHandler().getContextHandler().getJavacontext(), null);
			Iterator<FieldMember> fitr = fmm.iterator();
			while (fitr.hasNext())
			{
				FieldMember fm = fitr.next();
				String cmp = fm.getType();
				Iterator<FlowLineNode<CSFlowLineData>> iditr = idls.iterator();
				while (iditr.hasNext())
				{
					FlowLineNode<CSFlowLineData> id = iditr.next();
					String cmped = id.getData().getData();
					if (SimilarityHelper.ComputeTwoStringSimilarity(cmp, cmped) > PredictMetaInfo.TwoStringSimilarThreshold)
					{
						result.add(CSFlowLineHelper.ConcateTwoFlowLineNode(null, fln, ".", id, null, squeue, smthandler, null, null));
					}
				}
			}
		}
		return result;
	}
	
	public static List<FlowLineNode<CSFlowLineData>> HandleRawTypeSpecificationInfer(List<FlowLineNode<CSFlowLineData>> rawtypes, CSFlowLineQueue squeue, CSStatementHandler smthandler)
	{
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		Iterator<FlowLineNode<CSFlowLineData>> itr = rawtypes.iterator();
		while (itr.hasNext())
		{
			FlowLineNode<CSFlowLineData> fln = itr.next();
			String rawtype = fln.getData().getData();
			List<TypeMember> tps = SearchSpecificationOfAReference.SearchTypeSpecificationByPrefix(rawtype, squeue.GetLastHandler().getContextHandler().getJavacontext(), null);
			Iterator<TypeMember> tpitr = tps.iterator();
			while (tpitr.hasNext())
			{
				TypeMember tp = tpitr.next();
				if (SimilarityHelper.ComputeTwoStringSimilarity(rawtype, tp.getType()) > PredictMetaInfo.TwoStringSimilarThreshold)
				{
					result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), tp.getType(), tp.getClass(), false, false, null, null, squeue.GetLastHandler()), smthandler.getProb()));
				}
			}
		}
		return result;
	}
	
	public static void DirectlyGenerateNameOfType(List<FlowLineNode<CSFlowLineData>> tpls, CSFlowLineQueue squeue, CSStatementHandler smthandler)
	{
		Iterator<FlowLineNode<CSFlowLineData>> itr = tpls.iterator();
		while (itr.hasNext())
		{
			FlowLineNode<CSFlowLineData> fln = itr.next();
			CSFlowLineData data = fln.getData();
			String returntype = data.getData();
			String modifidedname = squeue.GetLastHandler().getScopeOffsetRefHandler().GenerateNewDeclaredVariable(NameConvention.GetAbbreviationOfType(returntype), returntype, null, smthandler.getAoi().isInFieldLevel());
			String modifieddata = returntype + " " + modifidedname;
			data.setData(modifieddata);
		}
	}
	
	public static List<FlowLineNode<CSFlowLineData>> HandleMethodInvocation(CSFlowLineQueue squeue, CSStatementHandler smthandler, argumentList arglist, String methodnamepara, OneCode methodnameoc) throws CodeSynthesisException
	{
		/*CSFlowLineData tlast = squeue.getLast().getData();
		boolean hasem = false;
		if ((tlast instanceof CSPsData) || (tlast instanceof CSPrData))
		{
			hasem = true;
		}*/
		String methodname = null;
		if (methodnameoc != null)
		{
			List<FlowLineNode<CSFlowLineData>> nls = methodnameoc.HandleCodeSynthesis(squeue, smthandler);
			methodname = nls.get(0).getData().getData();
		}
		else
		{
			methodname = methodnamepara;
		}
		CSMethodStatementHandler csmsh = new CSMethodStatementHandler(smthandler);
		csmsh.setNextstart(squeue.getLast());
		// List<FlowLineNode<CSFlowLineData>> alls = arglist.HandleCodeSynthesis(squeue, csmsh);
		List<FlowLineNode<CSFlowLineData>> alls = arglist.HandleMethodIntegrationCodeSynthesis(squeue, csmsh, methodname);
		/*
		 * CSMethodSignalHandleResult csmshr = squeue.BackSearchForMethodRelatedSignal();
		 * List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		 * Iterator<FlowLineNode<CSFlowLineData>> itr = alls.iterator();
		 * while (itr.hasNext())
		 * {
		 * 	FlowLineNode<CSFlowLineData> fln = itr.next();
		 * 	CSMethodInvocationData dt = new CSMethodInvocationData(csmshr.getFarem(), csmshr.getFaremused(), hasem, fln.getData());
		 * 	result.add(new FlowLineNode<CSFlowLineData>(dt, fln.getProbability()));
		 * }
		 * return result;*/
		return alls;
	}
	
	public static List<FlowLineNode<CSFlowLineData>> HandleInferredContent(CSFlowLineQueue squeue, CSStatementHandler smthandler, List<FlowLineNode<CSFlowLineData>> infermain, List<FlowLineNode<CSFlowLineData>> expectedinfer, String inferoperator) throws CodeSynthesisException
	{
		List<FlowLineNode<CSFlowLineData>> ls = CodeSynthesisHelper.HandleFieldSpecificationInfer(infermain, expectedinfer, squeue, smthandler, inferoperator);
		if (ls.size() == 0)
		{
			return CSFlowLineHelper.ForwardMerge(null, infermain, inferoperator, expectedinfer, null, squeue, smthandler, null, null);
		}
		return ls;
	}
	
	public static List<FlowLineNode<CSFlowLineData>> HandleInferredField(List<FlowLineNode<CSFlowLineData>> infermain, CSFlowLineQueue squeue,
			CSStatementHandler smthandler, String reservedword,
			List<FlowLineNode<CSFlowLineData>> expectedinfer) throws CodeSynthesisException 
	{
		if (reservedword != null && !reservedword.equals(""))
		{
			CSFlowLineHelper.ConcateOneFlowLineList(null, infermain, "."+reservedword);
		}
		return CodeSynthesisHelper.HandleInferredContent(squeue, smthandler, infermain, expectedinfer, ".");
	}
	
	public static List<FlowLineNode<CSFlowLineData>> HandleInferredMethodReference(List<FlowLineNode<CSFlowLineData>> infermain, CSFlowLineQueue squeue,
			CSStatementHandler smthandler, String reservedword,
			List<FlowLineNode<CSFlowLineData>> expectedinfer) throws CodeSynthesisException 
	{
		if (reservedword != null && !reservedword.equals(""))
		{
			CSFlowLineHelper.ConcateOneFlowLineList(null, infermain, "."+reservedword);
		}
		return CodeSynthesisHelper.HandleInferredContent(squeue, smthandler, infermain, expectedinfer, "::");
	}
	
	/*public static List<FlowLineNode<CSFlowLineData>> HandleFieldAccess(CSFlowLineQueue squeue, CSStatementHandler smthandler, OneCode rexp, String betweencontent, String fieldnamepara, OneCode field) throws CodeSynthesisException
	{
		if (field == null)
		{
			String bts = ((betweencontent != null && !betweencontent.equals("")) ? betweencontent  : "") + (((betweencontent != null && !betweencontent.equals("")) && (fieldnamepara != null && !fieldnamepara.equals("")))? "." : "" + fieldnamepara);
			// CSFieldAccessStatementHandler csfash = new CSFieldAccessStatementHandler(bts, smthandler);
			List<FlowLineNode<CSFlowLineData>> rels = rexp.HandleCodeSynthesis(squeue, smthandler);
			if (!bts.equals(""))
			{
				return CSFlowLineHelper.ConcateOneFlowLineList("", rels, "." + bts);
			}
			else
			{
				return rels;
			}
		}
		else
		{
			List<FlowLineNode<CSFlowLineData>> idls = field.HandleCodeSynthesis(squeue, smthandler);
			CSFieldAccessStatementHandler csfash = new CSFieldAccessStatementHandler(idls.get(0).getData().getData(), smthandler);
			List<FlowLineNode<CSFlowLineData>> rels = rexp.HandleCodeSynthesis(squeue, csfash);
			if (!(csfash.isFieldused()))
			{
				List<FlowLineNode<CSFlowLineData>> ls = CodeSynthesisHelper.HandleFieldSpecificationInfer(rels, idls, squeue, smthandler, ".");
				if (ls.size() == 0)
				{
					return CSFlowLineHelper.ForwardMerge(null, idls, ".", rels, null, squeue, smthandler, null, null);
				}
				return ls;
			}
			return rels;
		}
	}*/
	
}