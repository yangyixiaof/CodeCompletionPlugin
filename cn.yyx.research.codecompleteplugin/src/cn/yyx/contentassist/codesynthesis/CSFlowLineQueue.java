package cn.yyx.contentassist.codesynthesis;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.data.CSCommonOverProperty;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSForIniOverProperty;
import cn.yyx.contentassist.codesynthesis.data.CSLambdaData;
import cn.yyx.contentassist.codesynthesis.data.CSLambdaEndProperty;
import cn.yyx.contentassist.codesynthesis.data.CSVariableDeclarationData;
import cn.yyx.contentassist.codesynthesis.data.CSVariableHolderData;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.commonutils.CheckUtil;
import cn.yyx.contentassist.commonutils.SynthesisHandler;
import cn.yyx.contentassist.commonutils.VariableHT;

public class CSFlowLineQueue {
	
	private FlowLineNode<CSFlowLineData> last = null;
	
	/*protected CSFlowLineQueue() {
		// only can be invoked from subclass.
	}*/
	// private String recenttype = null;
	// private Class<?> recenttypeclass = null;
	
	public CSFlowLineQueue(FlowLineNode<CSFlowLineData> last) {
		this.setLast(last);
	}
	
	public SynthesisHandler GetLastHandler()
	{
		return getLast().getData().getHandler();
	}
	
	public int GenerateNewNodeId()
	{
		CheckUtil.CheckNotNull(getLast(), "the 'last' member of CSFlowLineQueue is null, serious error, the system will exit.");
		return getLast().getData().getSynthesisCodeManager().GenerateNextLevelId();
	}

	public FlowLineNode<CSFlowLineData> getLast() {
		return last;
	}

	public void setLast(FlowLineNode<CSFlowLineData> last) {
		this.last = last;
	}
	
	public FlowLineNode<CSFlowLineData> SearcheForRecentVHolderNode() {
		FlowLineNode<CSFlowLineData> tmp = last;
		while (tmp != null)
		{
			CSFlowLineData tmpdata = tmp.getData();
			if (tmpdata instanceof CSVariableDeclarationData)
			{
				break;
			}
			if (tmpdata instanceof CSVariableHolderData)
			{
				return tmp;
			}
			tmp = tmp.getPrev();
		}
		return null;
	}

	public FlowLineNode<CSFlowLineData> SearcheForRecentVariableDeclaredNode() {
		FlowLineNode<CSFlowLineData> tmp = last;
		while (tmp != null)
		{
			CSFlowLineData tmpdata = tmp.getData();
			if (tmpdata instanceof CSVariableDeclarationData)
			{
				return tmp;
			}
			tmp = tmp.getPrev();
		}
		return null;
	}

	public List<String> SearchForVariableDeclareHolderNames() {
		List<String> result = new LinkedList<String>();
		FlowLineNode<CSFlowLineData> tmp = last;
		while (tmp != null)
		{
			CSFlowLineData tmpdata = tmp.getData();
			if (tmpdata instanceof CSVariableHolderData)
			{
				result.add(0, ((CSVariableHolderData) tmpdata).getVarname());
			}
			if (tmpdata instanceof CSVariableDeclarationData)
			{
				return result;
			}
			tmp = tmp.getPrev();
		}
		return null;
	}

	/*public FlowLineNode<CSFlowLineData> BackSearchForSpecialClass(Class<?> cls) throws CodeSynthesisException {
		Stack<Integer> signals = new Stack<Integer>();
		return BackSearchForSpecialClass(cls, signals);
	}*/
	
	public FlowLineNode<CSFlowLineData> BackSearchForHead()
	{
		FlowLineNode<CSFlowLineData> tmp = last;
		FlowLineNode<CSFlowLineData> pretmp = null;
		while (tmp != null)
		{
			pretmp = tmp;
			tmp = tmp.getPrev();
		}
		return pretmp;
	}
	
	public FlowLineNode<CSFlowLineData> BackSearchForSpecialClass(Class<?> cls,
			Stack<Integer> signals) throws CodeSynthesisException {
		FlowLineNode<CSFlowLineData> tmp = last;
		while (tmp != null)
		{
			CSFlowLineData tmpdata = tmp.getData();
			tmpdata.HandleStackSignal(signals);
			if (tmpdata.HasSpecialProperty(cls) && signals.isEmpty())
			{
				return tmp;
			}
			tmp = tmp.getPrev();
		}
		return null;
	}
	
	public FlowLineNode<CSFlowLineData> BackSearchForTheNextOfSpecialClass(Class<?> cls,
			Stack<Integer> signals) throws CodeSynthesisException {
		FlowLineNode<CSFlowLineData> tmp = last;
		FlowLineNode<CSFlowLineData> tmpnext = null;
		while (tmp != null)
		{
			CSFlowLineData tmpdata = tmp.getData();
			tmpdata.HandleStackSignal(signals);
			if (tmpdata.HasSpecialProperty(cls) && signals.isEmpty())
			{
				return tmpnext;
			}
			tmpnext = tmp;
			tmp = tmp.getPrev();
		}
		return null;
	}
	
	private String SearchForVarableDeclarationType(FlowLineNode<CSFlowLineData> tfln)
	{
		FlowLineNode<CSFlowLineData> tmp = tfln;
		while (tmp != null)
		{
			CSFlowLineData tmpdata = tmp.getData();
			if (tmpdata instanceof CSVariableDeclarationData)
			{
				return ((CSVariableDeclarationData)tmpdata).getTypecode();
			}
			FlowLineNode<CSFlowLineData> bs = tmpdata.getSynthesisCodeManager().getBlockstart();
			if (bs != null)
			{
				tmp = bs;
			}
			tmp = tmp.getPrev();
		}
		new Exception("There is no variable declaration data before variableHolder?").printStackTrace();
		System.exit(1);
		return null;
	}
	
	private void HandleVarNameRms(String tp, String varname, Map<String, String> tpvarname, Map<String, Integer> tpremains, final int off)
	{
		if (!tpremains.containsKey(tp))
		{
			tpremains.put(tp, off);
		}
		int tpoff = tpremains.get(tp);
		if (tpoff == 0)
		{
			tpvarname.put(tp, varname);
		}
		tpoff--;
		tpremains.put(tp, tpoff);
	}
	
	/**
	 * return value is the level outer should handle.
	 * @param scope
	 * @return
	 */
	public VariableHT BackSearchHandleLambdaScope(int scope, int off)
	{
		// TODO
		
		Map<String, String> tpvarname = new TreeMap<String, String>();
		Map<String, Integer> tpremains = new TreeMap<String, Integer>();
		
		boolean vhbarrierdestroy = false;
		
		FlowLineNode<CSFlowLineData> tmp = last;
		while (tmp != null)
		{
			boolean shouldoperate = false;
			if (scope == 0)
			{
				shouldoperate = true;
			}
			CSFlowLineData tmpdata = tmp.getData();
			/*if (tmpdata.HasSpecialProperty(CSLambdaData.class))
			{
				scope--;
				
			}*/
			if (tmpdata.HasSpecialProperty(CSLambdaEndProperty.class))
			{
				FlowLineNode<CSFlowLineData> bs = tmpdata.getSynthesisCodeManager().getBlockstart();
				if (bs != null) {
					tmp = bs;
				}
			}
			if (shouldoperate)
			{
				// only use tmpdata.
				if (tmpdata.HasSpecialProperty(CSForIniOverProperty.class) || tmpdata.HasSpecialProperty(CSCommonOverProperty.class))
				{
					vhbarrierdestroy = true;
				}
				if (tmpdata instanceof CSVariableHolderData && vhbarrierdestroy)
				{
					String tp = SearchForVarableDeclarationType(tmp);
					String varname = ((CSVariableHolderData)tmpdata).getVarname();
					HandleVarNameRms(tp, varname, tpvarname, tpremains, off);
				}
				if (tmpdata instanceof CSVariableHolderData && !vhbarrierdestroy)
				{
					vhbarrierdestroy = true;
				}
				if (tmpdata instanceof CSLambdaData)
				{
					List<String> tps = ((CSLambdaData)tmpdata).getDeclares();
					if (tps != null && tps.size() > 0)
					{
						Iterator<String> itr = tps.iterator();
						while (itr.hasNext())
						{
							String ttp = itr.next();
							String[] tpss = ttp.split(" ");
							HandleVarNameRms(tpss[0], tpss[1], tpvarname, tpremains, off);
						}
					}
					scope--;
					if (scope < 0)
					{
						break;
					}
				}
			}
			tmp = tmp.getPrev();
		}
		return new VariableHT(tpvarname, tpremains, scope);
	}
	
	/*public VariableHT BackSearchForLastIthVariableHolderAndTypeDeclaration(int scope, int off) {
		
		
		FlowLineNode<CSFlowLineData> tmp = last;
		boolean vhbarrierdestroy = false;
		while (scope >= 0 && tmp != null)
		{
			CSFlowLineData tmpdata = tmp.getData();
			if (tmpdata.HasSpecialProperty(CSForIniOverProperty.class) || tmpdata.HasSpecialProperty(CSCommonOverProperty.class))
			{
				vhbarrierdestroy = true;
			}
			if (tmpdata instanceof CSVariableHolderData && vhbarrierdestroy)
			{
				String tp = SearchForVarableDeclarationType(tmp);
				String varname = ((CSVariableHolderData)tmpdata).getVarname();
				HandleVarNameRms(tp, varname, tpvarname, tpremains, off);
			}
			if (tmpdata instanceof CSVariableHolderData && !vhbarrierdestroy)
			{
				vhbarrierdestroy = true;
			}
			if (tmpdata instanceof CSLambdaData)
			{
				List<String> tps = ((CSLambdaData)tmpdata).getDeclares();
				Iterator<String> itr = tps.iterator();
				while (itr.hasNext())
				{
					String ttp = itr.next();
					String[] tpss = ttp.split(" ");
					HandleVarNameRms(tpss[0], tpss[1], tpvarname, tpremains, off);
				}
				scope--;
			}
			tmp = tmp.getPrev();
		}
		String vhtp = null;
		String vhne = null;
		FlowLineNode<CSFlowLineData> tmp = last;
		String recentvhne = null;
		int totalvh = 0;
		boolean barrierdestroy = false;
		// skip very close var holder.
		while (tmp != null)
		{
			CSFlowLineData tmpdata = tmp.getData();
			if (tmpdata.HasSpecialProperty(CSForIniOverProperty.class))
			{
				barrierdestroy = true;
			}
			if (tmpdata instanceof CSVariableHolderData && barrierdestroy)
			{
				totalvh++;
				if (off >= 0)
				{
					recentvhne = ((CSVariableHolderData)tmpdata).getVarname();
					off--;
					if (off < 0)
					{
						vhne = recentvhne;
					}
				}
			}
			 // the order of this if and above if can not be changed.
			if (tmpdata instanceof CSVariableHolderData && !barrierdestroy)
			{
				barrierdestroy = true;
			}
			if (tmpdata instanceof CSVariableDeclarationData)
			{
				CSVariableDeclarationData csvdd = (CSVariableDeclarationData)tmpdata;
				if (csvdd.getDcls() != null && csvdd.getDcls().getCls() != null)
				{
					vhtp = csvdd.getDcls().getCls().getSimpleName();
				}
				else
				{
					vhtp = ((CSVariableDeclarationData)tmpdata).getData();
				}
				break;
			}
			tmp = tmp.getPrev();
		}
		if (vhtp != null && totalvh > 0)
		{
			return new VariableHT(vhtp, vhne, totalvh);
		}
		//if ((vhtp == null && vhne != null) || (vhtp != null && vhne == null))
		//{
		//	throw new Error("Strange, has declarations but no holders or has holders but no declarations.");
		//}
		return new VariableHT(tpvarname, tpremains);
	}*/
	
	/*public FlowLineNode<CSFlowLineData> BackSearchForStructureSignal(int signal) {
		FlowLineNode<CSFlowLineData> tmp = last;
		while (tmp != null)
		{
			Integer struct = tmp.getData().getStructsignal();
			if (struct != null && struct == signal)
			{
				return tmp;
			}
		}
		return null;
	}

	public Class<?> getRecenttypeclass() {
		return recenttypeclass;
	}

	public void setRecenttypeclass(Class<?> recenttypeclass) {
		this.recenttypeclass = recenttypeclass;
	}

	public String getRecenttype() {
		return recenttype;
	}

	public void setRecenttype(String recenttype) {
		this.recenttype = recenttype;
	}*/
	
}