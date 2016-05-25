package cn.yyx.contentassist.commonutils;

import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSPrProperty;
import cn.yyx.contentassist.codesynthesis.data.CSPsProperty;

public class SignalHelper {
	
	/**
	 * invoker must be method related statement or onecode.
	 * @param squeue
	 * @return
	 */
	public static boolean HasEmBeforeMethod(CSFlowLineQueue squeue)
	{
		CSFlowLineData tlast = squeue.getLast().getData();
		boolean hasem = false;
		if ((tlast.HasSpecialProperty(CSPsProperty.class)) || (tlast.HasSpecialProperty(CSPrProperty.class)))
		{
			hasem = true;
		}
		return hasem;
	}
	
}
