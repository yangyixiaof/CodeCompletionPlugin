package cn.yyx.research.language.JDTManager;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeMap;

import cn.yyx.research.language.Utility.MyLogger;

public class JCScope {
	
	// Integer ID = -1;
	// Integer Level = -1;
	String description = null;
	
	// real data in reverse order.
	TreeMap<String, LinkedList<String>> dataInOrder = new TreeMap<String, LinkedList<String>>();
	
	// records the order of the data. To speed up the search.
	TreeMap<String, TreeMap<String, Integer>> dataOrder = new TreeMap<String, TreeMap<String, Integer>>();
	
	// To speed up search.
	TreeMap<String, Integer> allDataNum = new TreeMap<String, Integer>();
	// int allDataNum = -1;
	
	public JCScope() {
		// allDataNum = 0;
	}
	
	public JCScope(int id, int level) {
		// this.ID = id;
		// this.Level = level;
		// allDataNum = 0;
	}
	
	public void PushNewlyAssignedData(String data, String type)
	{
		TreeMap<String, Integer> dataorder = dataOrder.get(type);
		if (dataorder == null)
		{
			dataorder = new TreeMap<String, Integer>();
			dataOrder.put(type, dataorder);
		}
		Integer dorder = dataorder.get(data);
		LinkedList<String> datainorder = dataInOrder.get(type);
		if (datainorder == null)
		{
			datainorder = new LinkedList<String>();
			dataInOrder.put(type, datainorder);
		}
		Integer anum = allDataNum.get(type);
		if (anum == null)
		{
			anum = 0;
			allDataNum.put(type, anum);
		}
		if (dorder == null)
		{
			// newly added data, not exists before.
			datainorder.add(0, data);
			dataorder.put(data, anum);
			anum++;
			allDataNum.put(type, anum);
		}
		else
		{
			/*if (type.equals("int"))
			{
				MyLogger.Info("prebegin:now used data:"+data);
				MyLogger.Info("all num of type int:" + allDataNum.get(type));
				Set<String> keys = dataorder.keySet();
				Iterator<String> itr = keys.iterator();
				while (itr.hasNext())
				{
					String dta = itr.next();
					MyLogger.Info("data:" + dta + "; order:" + dataorder.get(dta));
				}
				MyLogger.Info("preend.");
			}*/
			
			int allnum = allDataNum.get(type);
			Iterator<String> itr = datainorder.iterator();
			int idx = 0;
			int dindex = allnum - dorder - 1;
			while (itr.hasNext() && idx != dindex)
			{
				String idata = itr.next();
				dataorder.put(idata, dataorder.get(idata)-1);
				idx++;
			}
			dataorder.put(data, anum-1);
			datainorder.remove(dindex);
			datainorder.add(0, data);
			
			/*if (type.equals("int"))
			{
				MyLogger.Info("postbegin:now used data:"+data);
				MyLogger.Info("all num of type int:" + allDataNum.get(type));
				Set<String> keys = dataorder.keySet();
				Iterator<String> itr2 = keys.iterator();
				while (itr2.hasNext())
				{
					String dta = itr2.next();
					MyLogger.Info("data:" + dta + "; order:" + dataorder.get(dta));
				}
				MyLogger.Info("postend.");
			}*/
		}
		
	}
	
	public Integer GetExactOffset(String data, String type)
	{
		TreeMap<String, Integer> dataorder = dataOrder.get(type);
		if (dataorder == null)
		{
			if (Character.isLowerCase(data.charAt(0))==true)
			{
				MyLogger.Error("Warning data: " + data + " : " + description + " : here is JCScope, the type of data is not declared or assigned. The system will exit. This has be improved in the future to get better compatibility.");
			}
			return null;
		}
		Integer order = dataorder.get(data);
		if (order == null)
		{
			if (Character.isLowerCase(data.charAt(0))==true)
			{
				MyLogger.Error("Warning data: " + data + " : " + description + " : here is JCScope, the data is not declared or assigned. The system will exit. This has be improved in the future to get better compatibility.");
			}
			return null;
		}
		int maxOffset = allDataNum.get(type)-1;
		return order-maxOffset;
	}
	
	public void ClearAll()
	{
		dataInOrder.clear();
		dataOrder.clear();
		// set all num to 0.
		// allDataNum = 0;
		allDataNum.clear();
	}
	
	private boolean CheckTwoListMustBeBothNullOrNotNull(LinkedList<String> A, LinkedList<String> B)
	{
		boolean error = false;
		if (A == null && B != null)
		{
			error = true;
		}
		if (A != null && B == null)
		{
			error = true;
		}
		if (error)
		{
			MyLogger.Error("The two list must be both null or not null.");
			System.exit(1);
		}
		boolean shouldrun = true;
		if (A == null)
		{
			shouldrun = false;
		}
		return shouldrun;
	}
	
	public void ResetScope(LinkedList<String> orderedData, LinkedList<String> orderedType) {
		ClearAll();
		boolean shouldcontinue = CheckTwoListMustBeBothNullOrNotNull(orderedData, orderedType);
		if (!shouldcontinue)
		{
			return;
		}
		Iterator<String> itr = orderedData.iterator();
		Iterator<String> typeitr = orderedType.iterator();
		while (itr.hasNext())
		{
			String key = itr.next();
			String type = typeitr.next();
			PushNewlyAssignedData(key, type);
		}
	}
	
	public void SetDescription(String description)
	{
		this.description = description;
	}
	
}