package cn.yyx.contentassist.commonutils;

import cn.yyx.contentassist.codesynthesis.CSFlowLineQueue;
import cn.yyx.contentassist.codesynthesis.data.CSFlowLineData;
import cn.yyx.contentassist.codesynthesis.data.CSPrData;
import cn.yyx.contentassist.codesynthesis.data.CSPsData;

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
		if ((tlast instanceof CSPsData) || (tlast instanceof CSPrData))
		{
			hasem = true;
		}
		return hasem;
	}
	
}
