package cn.yyx.contentassist.codesynthesis;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import cn.yyx.contentassist.codecompletion.CodeCompletionMetaInfo;
import cn.yyx.contentassist.codecompletion.PredictMetaInfo;
import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.data.CSEnterParamInfoProperty;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSMethodStatementHandler;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.InferredCCType;
import cn.yyx.contentassist.codesynthesis.typeutil.MethodTypeSignature;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeCheckHelper;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeResolver;
import cn.yyx.contentassist.codeutils.OneCode;
import cn.yyx.contentassist.codeutils.argumentList;
import cn.yyx.contentassist.codeutils.firstArgReferedExpression;
import cn.yyx.contentassist.codeutils.identifier;
import cn.yyx.contentassist.codeutils.type;
import cn.yyx.contentassist.commonutils.NameConvention;
import cn.yyx.contentassist.commonutils.RefAndModifiedMember;
import cn.yyx.contentassist.commonutils.SignalHelper;
import cn.yyx.contentassist.commonutils.SimilarityHelper;
import cn.yyx.contentassist.specification.FieldMember;
import cn.yyx.contentassist.specification.MethodMember;
import cn.yyx.contentassist.specification.SearchSpecificationOfAReference;
import cn.yyx.contentassist.specification.SpecificationHelper;
import cn.yyx.contentassist.specification.TypeMember;

public class CodeSynthesisHelper {
	
	public static String GenerateBlockCode(CSStatementHandler smthandler)
	{
		return "{\n"+smthandler.getAoi().getIndent()+"	\n"+smthandler.getAoi().getIndent()+"}";
	}
	
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
		// CCType cct = new CCType(void.class, "void");
		result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), fin.toString(), null, null, squeue.GetLastHandler()), smthandler.getProb()));
		return result;
	}
	
	public static List<FlowLineNode<CSFlowLineData>> HandleVarRefCodeSynthesis(Map<String, String> po, CSFlowLineQueue squeue, CSStatementHandler smthandler)
	{
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		Set<String> types = po.keySet();
		Iterator<String> titr = types.iterator();
		while (titr.hasNext())
		{
			String type = titr.next();
			String code = po.get(type);
			LinkedList<CCType> cls = TypeResolver.ResolveType(type, squeue, smthandler);
			Iterator<CCType> clsitr = cls.iterator();
			int total = 0;
			while (clsitr.hasNext())
			{
				CCType cct = clsitr.next();
				total++;
				if (total > PredictMetaInfo.MaxTypeSpecificationSize)
				{
					break;
				}
				result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), code, cct, null, squeue.GetLastHandler()), smthandler.getProb()));
			}
		}
		return result;
	}
	
	public static List<FlowLineNode<CSFlowLineData>> HandleVarRefInferredMethodReference(Map<String, String> po, CSFlowLineQueue squeue, CSStatementHandler smthandler, String reservedword, List<FlowLineNode<CSFlowLineData>> expectedinfer)
	{
		return HandleVarRefInferredContent(po, squeue, smthandler, reservedword, expectedinfer, "::", true);
	}
	
	public static List<FlowLineNode<CSFlowLineData>> HandleVarRefInferredField(Map<String, String> po, CSFlowLineQueue squeue, CSStatementHandler smthandler, String reservedword, List<FlowLineNode<CSFlowLineData>> expectedinfer)
	{
		return HandleVarRefInferredContent(po, squeue, smthandler, reservedword, expectedinfer, ".", false);
	}
	
	private static List<FlowLineNode<CSFlowLineData>> HandleVarRefInferredContent(Map<String, String> po, CSFlowLineQueue squeue, CSStatementHandler smthandler, String reservedword, List<FlowLineNode<CSFlowLineData>> expectedinfer, String concator, boolean ismethod)
	{
		// this function doesn't need to handle CSMethodStatementHandler, CSMethodStatementHandler has been handled in method invocation.
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		if (reservedword != null && !reservedword.equals(""))
		{
			Map<String, String> npo = new TreeMap<String, String>();
			Set<String> pokeys = po.keySet();
			Iterator<String> citr = pokeys.iterator();
			while (citr.hasNext())
			{
				String type = citr.next();
				String code = po.get(type);
				if (TypeCheckHelper.IsInferredType(type))
				{
					result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), code + "." + reservedword + concator + expectedinfer.get(0).getData().getData(), new InferredCCType(), null, squeue.GetLastHandler()), smthandler.getProb()));
					continue;
				}
				code += "." + reservedword;
				npo.put(type, code);
			}
			po = npo;
		}
		RefAndModifiedMember ramm = SpecificationHelper.GetMostLikelyRef(squeue.GetLastHandler().getContextHandler(), po, expectedinfer.get(0).getData().getData(), ismethod, concator);
		if (ramm != null)
		{
			String ref = ramm.getRef();
			String member = ramm.getMember();
			String membertype = ramm.getMembertype();
			LinkedList<CCType> cls = TypeResolver.ResolveType(membertype, squeue, smthandler);
			Iterator<CCType> clsitr = cls.iterator();
			while (clsitr.hasNext())
			{
				CCType cct = clsitr.next();
				result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), ref + concator + member, cct, null, squeue.GetLastHandler()), smthandler.getProb()));
			}
		}
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
			ls = CSFlowLineHelper.ForwardConcate(null, ls, concator, tmpls, null, squeue, smthandler, null);
		}
		return ls;
	}
	
	private static List<FlowLineNode<CSFlowLineData>> HandleMethodSpecificationInfer(CSFlowLineQueue squeue,
			CSStatementHandler smthandler, String spechint, String beforemethodexp, String keywordfull, Map<String, MethodTypeSignature> mts) {
		String addition = keywordfull == null ? "" : keywordfull;
		
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		
		// debugging code.
		if (CodeCompletionMetaInfo.DebugMode)
		{
		//	SearchSpecificationOfAReference.SearchFunctionSpecificationByPrefix(spechint, squeue.GetLastHandler().getContextHandler().getJavacontext());
		}
		
		List<MethodMember> res = SearchSpecificationOfAReference.SearchMethodSpecificationByPrefix(spechint, squeue.GetLastHandler().getContextHandler().getJavacontext());
		Iterator<MethodMember> itr = res.iterator();
		// String cmp = StringUtil.GetContentBehindFirstWhiteSpace(spechint);
		while (itr.hasNext())
		{
			MethodMember mm = itr.next();
			String methodname = mm.getName();
			MethodTypeSignature mtsone = MethodTypeSignature.TranslateMethodMember(mm, squeue, smthandler);
			int id = squeue.GenerateNewNodeId();
			List<CCType> rtclss = mtsone.getReturntype();
			Iterator<CCType> rcitr = rtclss.iterator();
			while (rcitr.hasNext())
			{
				CCType rc = rcitr.next();
				result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(id, smthandler.getSete(), ((beforemethodexp == null || beforemethodexp.equals("")) ? addition + methodname : beforemethodexp + "." + addition + methodname), rc, null, squeue.GetLastHandler()), smthandler.getProb()));
			}
			mts.put(id+"", mtsone);
		}
		return result;
	}
	
	public static List<FlowLineNode<CSFlowLineData>> HandleClassInvokeCodeSynthesis(CSFlowLineQueue squeue, CSStatementHandler smthandler, firstArgReferedExpression rexp, String between, String methodname, Map<String, MethodTypeSignature> mts)
			throws CodeSynthesisException {
		// CheckUtil.CheckStatementHandlerIsMethodStatementHandler(smthandler);
		// CSMethodStatementHandler realhandler = (CSMethodStatementHandler) smthandler;
		// String mcode = realhandler.getMethodname();
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		String mcodelater = ((between == null || between.equals("")) ? "" : between) + methodname;
		if (rexp != null)
		{
			List<FlowLineNode<CSFlowLineData>> ls = rexp.HandleCodeSynthesis(squeue, smthandler);
			if (ls == null || ls.size() == 0)
			{
				return null;
			}
			// Solved. here should not just get the first element. such as commonFieldRef, they can not distinguish their priority.
			Iterator<FlowLineNode<CSFlowLineData>> itr = ls.iterator();
			String rexpcodepre = null;
			while (itr.hasNext())
			{
				FlowLineNode<CSFlowLineData> fln = itr.next();
				String rexpcode = fln.getData().getData();
				if (!rexpcode.equals(rexpcodepre))
				{
					String mcode = rexpcode + "." + mcodelater;
					List<FlowLineNode<CSFlowLineData>> ts = CodeSynthesisHelper.HandleMethodSpecificationInfer(squeue, smthandler, mcode, rexpcode, between, mts);
					if (ts == null || ts.size() == 0) {
						if (fln.getData().getDcls() instanceof InferredCCType)
						{
							result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), mcode, new InferredCCType(), null, squeue.GetLastHandler()), smthandler.getProb()));
						}
					} else {
						result.addAll(ts);
					}
				}
				rexpcodepre = rexpcode;
			}
			return result;
		}
		else
		{
			return CodeSynthesisHelper.HandleMethodSpecificationInfer(squeue, smthandler, mcodelater, null, between, mts);
		}
	}
	
	public static List<FlowLineNode<CSFlowLineData>> HandleFieldSpecificationInfer(List<FlowLineNode<CSFlowLineData>> tmp, List<FlowLineNode<CSFlowLineData>> idls, CSFlowLineQueue squeue, CSStatementHandler smthandler, String concator) throws CodeSynthesisException
	{
		List<FlowLineNode<CSFlowLineData>> result = new LinkedList<FlowLineNode<CSFlowLineData>>();
		Iterator<FlowLineNode<CSFlowLineData>> itr = tmp.iterator();
		while (itr.hasNext())
		{
			FlowLineNode<CSFlowLineData> fln = itr.next();
			CSFlowLineData flndata = fln.getData();
			if (TypeCheckHelper.IsInferredType(flndata.getDcls()))
			{
				result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), flndata.getData() + concator + idls.get(0).getData().getData(), new InferredCCType(), null, squeue.GetLastHandler()), fln.getProbability()));
				continue;
			}
			List<FieldMember> fmm = SearchSpecificationOfAReference.SearchFieldSpecificationByPrefix(fln.getData().getData() + concator, squeue.GetLastHandler().getContextHandler().getJavacontext());
			Iterator<FieldMember> fitr = fmm.iterator();
			while (fitr.hasNext())
			{
				FieldMember fm = fitr.next();
				String cmp = fm.getName();
				Iterator<FlowLineNode<CSFlowLineData>> iditr = idls.iterator();
				while (iditr.hasNext())
				{
					FlowLineNode<CSFlowLineData> id = iditr.next();
					String cmped = id.getData().getData();
					if (SimilarityHelper.ComputeTwoStringSimilarity(cmp, cmped) > PredictMetaInfo.TwoStringSimilarThreshold)
					{
						result.add(CSFlowLineHelper.ConcateTwoFlowLineNode(null, fln, concator, id, null, squeue, smthandler, null));
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
			List<TypeMember> tps = SearchSpecificationOfAReference.SearchFieldClassMemberSpecificationByPrefix(rawtype, squeue.GetLastHandler().getContextHandler().getJavacontext());
			Iterator<TypeMember> tpitr = tps.iterator();
			int tpspesize = 0;
			while (tpitr.hasNext())
			{
				tpspesize++;
				if (tpspesize > PredictMetaInfo.MaxTypeSpecificationSize)
				{
					break;
				}
				TypeMember tp = tpitr.next();
				//if (SimilarityHelper.ComputeTwoStringSimilarity(rawtype, tp.getTypeclass().getSimpleName()) > PredictMetaInfo.TwoStringSimilarThreshold)
				//{
				result.add(new FlowLineNode<CSFlowLineData>(new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), tp.getType(), new CCType(tp), null, squeue.GetLastHandler()), smthandler.getProb()));
				//}
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
	
	public static List<FlowLineNode<CSFlowLineData>> HandleMethodInvocation(CSFlowLineQueue squeue, CSStatementHandler smthandler, argumentList arglist, String methodnamepara, OneCode methodnameoc, boolean hasem) throws CodeSynthesisException
	{
		CSMethodStatementHandler csmsh = new CSMethodStatementHandler(smthandler, SignalHelper.HasEmBeforeMethod(squeue));
		csmsh.setNextstart(squeue.getLast());
		
		// set method name.
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
		// List<FlowLineNode<CSFlowLineData>> alls = arglist.HandleCodeSynthesis(squeue, csmsh);
		List<FlowLineNode<CSFlowLineData>> alls = arglist.HandleMethodIntegrationCodeSynthesis(squeue, csmsh, methodname, hasem);
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
		FlowLineNode<CSFlowLineData> mf = csmsh.getMostfar();
		if (mf != null && !(mf.getData().HasSpecialProperty(CSEnterParamInfoProperty.class)))
		{
			System.err.println("Error! EnterParam not the start of the method.");
			throw new CodeSynthesisException("Error! EnterParam not the start of the method.");
		}
		// return ListHelper.AddExtraPropertyToAllListNodes(alls, new CSMethodInvocationProperty(hasem));
		return alls;
	}
	
	private static List<FlowLineNode<CSFlowLineData>> HandleInferredContent(CSFlowLineQueue squeue, CSStatementHandler smthandler, List<FlowLineNode<CSFlowLineData>> infermain, List<FlowLineNode<CSFlowLineData>> expectedinfer, String inferoperator) throws CodeSynthesisException
	{
		List<FlowLineNode<CSFlowLineData>> ls = CodeSynthesisHelper.HandleFieldSpecificationInfer(infermain, expectedinfer, squeue, smthandler, inferoperator);
		// Remaining. this may be removed to get exact result.
		/*if (ls.size() == 0)
		{
			return CSFlowLineHelper.ForwardConcate(null, infermain, inferoperator, expectedinfer, null, squeue, smthandler, null);
		}*/
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