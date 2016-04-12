package cn.yyx.contentassist.codesynthesis;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputationKind;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeComputer;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeConflictException;

public class CSFlowLineStamp {

	List<FlowLineNode<CSFlowLineData>> parnodes = new LinkedList<FlowLineNode<CSFlowLineData>>();

	public void AddOneParNode(FlowLineNode<CSFlowLineData> fln) {
		parnodes.add(fln);
	}

	public Iterator<FlowLineNode<CSFlowLineData>> Iterator() {
		return parnodes.iterator();
	}

	public int Size() {
		return parnodes.size();
	}
	
	public CSFlowLineStamp ForwardMerge(CSFlowLineStamp t, String prefix, String concator, String postfix, CSFlowLineQueue squeue, CSStatementHandler smthandler, Integer structsignal, TypeComputationKind oneafter, TypeComputationKind beforetwo) throws CodeSynthesisException {
		if (parnodes.size() == 0) {
			if (t == null || t.Size() == 0) {
				return null;
			} else {
				if (prefix != null && !prefix.equals(""))
				{
					// testing
					System.err.println("Warning: concate two list and one is null but prefix is not empty.");
					return null;
				}
				else
				{
					CheckConcator(concator);
					return CSFlowLineHelper.ConcateOneFLStamp(concator, t, postfix);
				}
			}
		} else {
			if (t == null || t.Size() == 0) {
				if (postfix != null && !postfix.equals(""))
				{
					// testing
					System.err.println("Warning: concate two list and one is null but prefix is not empty.");
					return null;
				}
				else
				{
					CheckConcator(concator);
					return CSFlowLineHelper.ConcateOneFLStamp(prefix, this, concator);
				}
			} else {
				CSFlowLineStamp result = new CSFlowLineStamp();
				Iterator<FlowLineNode<CSFlowLineData>> itr1 = parnodes.iterator();
				while (itr1.hasNext()) {
					FlowLineNode<CSFlowLineData> fln1 = itr1.next();
					Iterator<FlowLineNode<CSFlowLineData>> itr2 = t.parnodes.iterator();
					while (itr2.hasNext()) {
						FlowLineNode<CSFlowLineData> fln2 = itr2.next();
						FlowLineNode<CSFlowLineData> tmp = null;
						try {
							tmp = ConcateTwoFlowLineNode(prefix, fln1, concator, fln2, postfix, squeue, smthandler,
									structsignal, oneafter, beforetwo);
						} catch (TypeConflictException e) {
							e.printStackTrace();
							continue;
						}
						result.AddOneParNode(tmp);
					}
				}
				if (result.Size() == 0) {
					return null;
				}
				return result;
			}
		}
	}

	protected FlowLineNode<CSFlowLineData> ConcateTwoFlowLineNode(String prefix, FlowLineNode<CSFlowLineData> one,
			String concator, FlowLineNode<CSFlowLineData> two, String postfix, 
			CSFlowLineQueue squeue, CSStatementHandler smthandler, Integer structsignal, TypeComputationKind oneafter, TypeComputationKind beforetwo) throws CodeSynthesisException {
		CSFlowLineData d1 = one.getData();
		String str1 = d1.getData();
		CSFlowLineData d2 = two.getData();
		String str2 = d2.getData();
		Class<?> clz = null;
		if (oneafter == null || oneafter == TypeComputationKind.NotSureOptr || oneafter == TypeComputationKind.NoOptr) {
			oneafter = d1.getPosttck();
		}
		if (beforetwo == null || beforetwo == TypeComputationKind.NotSureOptr || beforetwo == TypeComputationKind.NoOptr) {
			beforetwo = d2.getPretck();
		}
		TypeComputationKind tck = TypeComputer.ChooseOne(oneafter, beforetwo);
		clz = TypeComputer.ComputeType(d1.getDcls(), d2.getDcls(), tck);
		String cnctcnt = (prefix == null ? "" : prefix) + str1 + (concator == null ? "" : concator) + str2
				+ (postfix == null ? "" : postfix);
		double cnctprob = one.getProbability() + two.getProbability();
		FlowLineNode<CSFlowLineData> cncted = new FlowLineNode<CSFlowLineData>(
				new CSFlowLineData(squeue.GenerateNewNodeId(), smthandler.getSete(), cnctcnt, clz,
						d1.isHaspre(), d2.isHashole(), d2.getPretck(), d2.getPosttck(), d1.getHandler()), cnctprob);
		return cncted;
	}

	protected void CheckConcator(String concator) {
		if (concator != null && !concator.equals("")) {
			System.err
					.println("No another part, but the concator has real values. Serious error, the system will exit.");
			System.exit(1);
		}
	}
	
}