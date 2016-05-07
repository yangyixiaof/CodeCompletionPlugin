package cn.yyx.contentassist.codesynthesis.data;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.research.language.simplified.JDTManager.ScopeOffsetRefHandler;

public class CSVariableDeclarationData extends CSFlowLineData {

	public CSVariableDeclarationData(Integer id, Sentence sete, String data, CCType dcls, boolean haspre,
			boolean hashole, TypeComputationKind pretck, TypeComputationKind posttck, SynthesisHandler handler) {
		super(id, sete, data, dcls, haspre, hashole, pretck, posttck, handler);
	}

	public CSVariableDeclarationData(CSFlowLineData cd) {
		super(cd.getId(), cd.getSete(), cd.getData(), cd.getDcls(), cd.isHaspre(), cd.isHashole(), cd.getPretck(),
				cd.getPosttck(), cd.getHandler());
	}

	@SuppressWarnings("unchecked")
	@Override
	public CSFlowLineData Merge(String prefix, String concator, CSFlowLineData d2, String postfix,
			CSFlowLineQueue squeue, CSStatementHandler smthandler, TypeComputationKind oneafter,
			TypeComputationKind beforetwo) throws CodeSynthesisException {
		CSFlowLineData pd = super.Merge(prefix, concator, d2, postfix, squeue, smthandler, oneafter, beforetwo);
		CSExtraData pdextra = pd.getExtraData();
		FlowLineNode<CSFlowLineData> lt = (FlowLineNode<CSFlowLineData>) pdextra.GetExtraData(CSDataMetaInfo.LastNode);
		CSVariableHolderExtraInfo cvhei = (CSVariableHolderExtraInfo) pdextra
				.GetExtraData(CSDataMetaInfo.VariableHolders);
		List<String> varocs = new LinkedList<String>();
		varocs.add(cvhei.getVarname());
		varocs.addAll(cvhei.getVars());
		List<Class<?>> judge = new LinkedList<Class<?>>();
		judge.add(cvhei.getCls());
		judge.addAll(cvhei.getClss());
		Class<?> oc = null;
		Iterator<Class<?>> itr = judge.iterator();
		while (itr.hasNext()) {
			Class<?> c = itr.next();
			if (c != null) {
				if (oc == null) {
					oc = c;
				} else {
					if (!((oc.isAssignableFrom(c)) || (c.isAssignableFrom(oc)))) {
						throw new CodeSynthesisException(
								"VariableDeclarationData can not handle all these collected result types.");
					}
				}
			}
		}
		String detp = getData();
		if (oc != null) {
			detp = oc.getName();
		}
		SynthesisHandler hd = lt.getData().getHandler();
		ScopeOffsetRefHandler sc = hd.getScopeOffsetRefHandler();
		ScopeOffsetRefHandler clonedsc = null;
		try {
			clonedsc = (ScopeOffsetRefHandler) sc.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
			System.err.println("What the fuck! Clone ScopeOffsetRefHandler run into error.");
			System.exit(1);
		}
		Iterator<String> vitr = varocs.iterator();
		while (vitr.hasNext()) {
			String vs = vitr.next();
			clonedsc.NewDeclaredVariable(vs, detp, smthandler.getAoi().isInFieldLevel());
		}
		return pd;
	}

}
