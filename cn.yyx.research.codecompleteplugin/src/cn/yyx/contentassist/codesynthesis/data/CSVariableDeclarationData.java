package cn.yyx.contentassist.codesynthesis.data;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.statementhandler.CSStatementHandler;
import cn.yyx.contentassist.codesynthesis.typeutil.CCType;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.TypeComputationKind;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.research.language.simplified.JDTManager.ScopeOffsetRefHandler;

public class CSVariableDeclarationData extends CSFlowLineData {
	
	/*public CSVariableDeclarationData(Integer id, Sentence sete, String data, CCType dcls, boolean haspre,
	 *		boolean hashole, TypeComputationKind pretck, TypeComputationKind posttck, SynthesisHandler handler) {
	 *	super(id, sete, data, dcls, haspre, hashole, pretck, posttck, handler);
	}*/
	
	private String typecode = null;
	
	public CSVariableDeclarationData(String typecode, CSFlowLineData cd) {
		super(cd.getId(), cd.getSete(), cd.getData(), cd.getDcls(), cd.isHaspre(), cd.isHashole(), 
				cd.getTck(), cd.getHandler());
		this.setTypecode(typecode);
		this.setCsep(cd.getCsep());
		this.setScm(cd.getSynthesisCodeManager());
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public CSFlowLineData Merge(String prefix, String concator, CSFlowLineData d2, String postfix,
			CSFlowLineQueue squeue, CSStatementHandler smthandler, TypeComputationKind tck) throws CodeSynthesisException {
		CSFlowLineData pd = super.Merge(prefix, concator, d2, postfix, squeue, smthandler, tck);
		CSExtraData pdextra = pd.getExtraData();
		FlowLineNode<CSFlowLineData> lt = (FlowLineNode<CSFlowLineData>) pdextra.GetExtraData(CSDataMetaInfo.LastNode);
		CSVariableHolderExtraInfo cvhei = (CSVariableHolderExtraInfo) pdextra
				.GetExtraData(CSDataMetaInfo.VariableHolders);
		if (lt == null)
		{
			System.err.println("Last node in extradata is null? What the fuck?");
			System.exit(1);
		}
		if (cvhei != null)
		{
			List<String> varocs = new LinkedList<String>();
			varocs.add(cvhei.getVarname());
			if (cvhei.getVars() != null)
			{
				varocs.addAll(cvhei.getVars());
			}
			List<CCType> judge = new LinkedList<CCType>();
			judge.add(cvhei.getCls());
			if (cvhei.getClss() != null)
			{
				judge.addAll(cvhei.getClss());
			}
			
			// set oc.
			CCType oc = null;
			Iterator<CCType> itr = judge.iterator();
			while (itr.hasNext()) {
				CCType cct = itr.next();
				Class<?> c = cct.getCls();
				if (c != null) {
					if (oc == null) {
						oc = cct;
					} else {
						if (!((oc.getCls().isAssignableFrom(c)) || (c.isAssignableFrom(oc.getCls())))) {
							throw new CodeSynthesisException(
									"VariableDeclarationData can not handle all these collected result types.");
						}
					}
				}
			}
			
			// set scope offset handler.
			String detp = getData();
			if (oc != null) {
				detp = oc.getClstr();
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
			if (!detp.equals(getData()))
			{
				ModifyPrefixedType(pd, detp);
			}
		}
		return pd;
	}
	
	private void ModifyPrefixedType(CSFlowLineData pd, String changetotypecode)
	{
		String pddata = pd.getData();
		if (!pddata.startsWith(typecode))
		{
			System.err.println("What the fuck, variable declaration code not start with its typecode?");
			System.exit(1);
		}
		String pdnewdata = changetotypecode + pddata.substring(typecode.length());
		pd.setData(pdnewdata);
	}

	public String getTypecode() {
		return typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}
	
}