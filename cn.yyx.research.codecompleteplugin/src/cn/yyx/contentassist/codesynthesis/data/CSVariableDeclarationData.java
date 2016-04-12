package cn.yyx.contentassist.codesynthesis.data;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codepredict.Sentence;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.commonutils.SynthesisHandler;

public class CSVariableDeclarationData extends CSFlowLineData{
	
	public CSVariableDeclarationData(Integer id, Sentence sete, String data, Class<?> dcls, boolean haspre, boolean hashole, TypeComputationKind pretck, TypeComputationKind posttck, SynthesisHandler handler) {
		super(id, sete, data, dcls, haspre, hashole, pretck, posttck, handler);
	}
	
	public CSVariableDeclarationData(CSFlowLineData cd) {
		super(cd.getId(), cd.getSete(), cd.getData(), cd.getDcls(), cd.isHaspre(), cd.isHashole(), cd.getPretck(), cd.getPosttck(), cd.getHandler());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public CSFlowLineData Merge(String prefix, String concator, CSFlowLineData d2, String postfix,
			CSFlowLineQueue squeue, CSStatementHandler smthandler, TypeComputationKind oneafter,
			TypeComputationKind beforetwo) throws CodeSynthesisException {
		CSFlowLineData pd = super.Merge(prefix, concator, d2, postfix, squeue, smthandler, oneafter, beforetwo);
		CSExtraData pdextra = pd.getExtraData();
		FlowLineNode<CSFlowLineData> lt = (FlowLineNode<CSFlowLineData>) pdextra.GetExtraData(CSDataMetaInfo.LastNode);
		 = pdextra.GetExtraData(CSDataMetaInfo.VariableHolders);
	}
	
}
