package cn.yyx.research.language.JDTManager;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

import cn.yyx.research.language.Utility.MyLogger;

public class ScopeDataManager {

	// DataScope is reverse order.
	Map<String, LinkedList<DataScopeInfo>> mDataScopeMap = new TreeMap<String, LinkedList<DataScopeInfo>>();

	// All Scope Data is positive order.
	Map<OneScope, LinkedList<String>> mFieldScopeDataMap = new TreeMap<OneScope, LinkedList<String>>();
	Map<OneScope, LinkedList<String>> mFinalFieldScopeDataMap = new TreeMap<OneScope, LinkedList<String>>();

	Map<OneScope, LinkedList<String>> mCommonScopeDataMap = new TreeMap<OneScope, LinkedList<String>>();
	Map<OneScope, LinkedList<String>> mFinalCommonScopeDataMap = new TreeMap<OneScope, LinkedList<String>>();

	Map<OneScope, LinkedList<String>> mFieldScopeTypeMap = new TreeMap<OneScope, LinkedList<String>>();
	Map<OneScope, LinkedList<String>> mFinalFieldScopeTypeMap = new TreeMap<OneScope, LinkedList<String>>();

	Map<OneScope, LinkedList<String>> mCommonScopeTypeMap = new TreeMap<OneScope, LinkedList<String>>();
	Map<OneScope, LinkedList<String>> mFinalCommonScopeTypeMap = new TreeMap<OneScope, LinkedList<String>>();

	EnteredScopeStack classstack = new EnteredScopeStack();

	VDataPool fvdp = new VDataPool();
	VDataPool cvdp = new VDataPool();

	VDataPool ffvdp = new VDataPool();
	VDataPool fcvdp = new VDataPool();

	public ScopeDataManager() {
	}

	private void CheckTypeNotNull(String type) {
		if (type == null) {
			MyLogger.Error("The type in AddDataNewlyUsed must not be null, serious error. The system will exit.");
			System.exit(1);
		}
	}

	private void CheckTypeMustNull(String type) {
		if (type != null) {
			MyLogger.Error("The type in AddDataNewlyUsed must be null, serious error. The system will exit.");
			System.exit(1);
		}
	}

	// only declare add new things call this method.
	private void DataScopeAndScopeDataMapCode(String data, String type, boolean isfinal, OneScope oscope,
			boolean isfield, Map<OneScope, LinkedList<String>> fieldScopeDataMap,
			Map<OneScope, LinkedList<String>> fieldScopeTypeMap) {
		LinkedList<DataScopeInfo> list = mDataScopeMap.get(data);

		/*
		 * if (data.equals("a")) { MyLogger.Info("a's list:" + list + " " +
		 * (list != null ? list.size() : 0) + " type:" + type); }
		 */

		if (list == null) {
			list = new LinkedList<DataScopeInfo>();
			mDataScopeMap.put(data, list);
		}
		if (list.size() > 0) {
			DataScopeInfo firstone = list.get(0);

			/*
			 * if (type.equals("Map<Integer,String>") && data.equals("a")) {
			 * MyLogger.Info(
			 * "firstone.getOscope().getID() == oscope.getID():" +
			 * (firstone.getOscope().getID() == oscope.getID()) +
			 * "isfinal:"+isfinal + "isfield:" + isfield +
			 * " firstone.isField():"+firstone.isField()); }
			 */

			if (firstone.getOscope().getID() == oscope.getID() && firstone.isField() == isfield
					&& firstone.isFinal() == isfinal) {
				return;
			}
		}

		list.add(0, new DataScopeInfo(oscope, data, type, isfinal, isfield));
		LinkedList<String> datalist = fieldScopeDataMap.get(oscope);
		if (datalist == null) {
			datalist = new LinkedList<String>();
			fieldScopeDataMap.put(oscope, datalist);
		}
		datalist.add(data);
		LinkedList<String> typelist = fieldScopeTypeMap.get(oscope);
		if (typelist == null) {
			typelist = new LinkedList<String>();
			fieldScopeTypeMap.put(oscope, typelist);
		}
		typelist.add(type);
	}

	public void AddDataNewlyUsed(String data, String type, boolean isfinal, boolean isfielddeclare,
			boolean iscommondeclare, boolean isFieldUpdate, boolean isCommonUpdate) {
		// VDataPoolManager vdpm =
		// KindLibrary.ChooseManagerAccordingToKind(kind, vhcpm, vlpm, vvopm);
		OneScope oscope = null;
		VDataPool use = null;
		if (isfinal) {
			if (isfielddeclare) {
				oscope = classstack.peek();
				CheckTypeNotNull(type);
				use = ffvdp;
				DataScopeAndScopeDataMapCode(data, type, isfinal, oscope, isfielddeclare, mFinalFieldScopeDataMap,
						mFinalFieldScopeTypeMap);
			}
			if (iscommondeclare) {
				oscope = classstack.peek();
				CheckTypeNotNull(type);
				use = fcvdp;
				DataScopeAndScopeDataMapCode(data, type, isfinal, oscope, isfielddeclare, mFinalCommonScopeDataMap,
						mFinalCommonScopeTypeMap);
			}
			if (isFieldUpdate) {
				LinkedList<DataScopeInfo> list = mDataScopeMap.get(data);
				if (list != null) {
					DataScopeInfo dscopeinfo = GetDataScopeInfoBySearch(list, true, true, true);
					if (dscopeinfo != null) {
						oscope = dscopeinfo.getOscope();
						CheckTypeMustNull(type);
						type = dscopeinfo.getType();
						use = ffvdp;
					} else {
						MyLogger.Error("Debugging dscope null: data:" + data + "; isFieldUpdate : " + isFieldUpdate);
					}
				}
			}
			if (isCommonUpdate) {
				LinkedList<DataScopeInfo> list = mDataScopeMap.get(data);
				if (list != null) {
					DataScopeInfo dscopeinfo = GetDataScopeInfoBySearch(list, true, false, true);
					oscope = dscopeinfo.getOscope();
					CheckTypeMustNull(type);
					type = dscopeinfo.getType();
					use = fcvdp;
				}
			}
		} else {
			if (isfielddeclare) {
				//MyLogger.Info("fielddeclare:data:"+data+";type:"+type+";isfielddeclare:"+isfielddeclare+";isfinal:"+isfinal);
				/*if (data.equals("arr"))
				{
					new Exception().printStackTrace();
				}*/
				
				oscope = classstack.peek();
				CheckTypeNotNull(type);
				use = fvdp;
				DataScopeAndScopeDataMapCode(data, type, isfinal, oscope, isfielddeclare, mFieldScopeDataMap,
						mFieldScopeTypeMap);
			}
			if (iscommondeclare) {
				// MyLogger.Info("commondeclare:data:"+data+";type:"+type+";isfielddeclare:"+isfielddeclare+";isfinal:"+isfinal);
				oscope = classstack.peek();
				CheckTypeNotNull(type);
				use = cvdp;
				DataScopeAndScopeDataMapCode(data, type, isfinal, oscope, isfielddeclare, mCommonScopeDataMap,
						mCommonScopeTypeMap);
			}
			if (isFieldUpdate) {
				LinkedList<DataScopeInfo> list = mDataScopeMap.get(data);
				if (list != null) {
					DataScopeInfo dscopeinfo = GetDataScopeInfoBySearch(list, false, true, true);
					if (dscopeinfo != null) {
						oscope = dscopeinfo.getOscope();
						CheckTypeMustNull(type);
						type = dscopeinfo.getType();
						use = fvdp;
					} else {
						MyLogger.Error("Debugging dscope null: data:" + data + "; isFieldUpdate : " + isFieldUpdate);
					}
				}
			}
			if (isCommonUpdate) {
				LinkedList<DataScopeInfo> list = mDataScopeMap.get(data);
				if (list != null) {
					DataScopeInfo dscopeinfo = GetDataScopeInfoBySearch(list, false, false, true);
					oscope = dscopeinfo.getOscope();
					CheckTypeMustNull(type);
					type = dscopeinfo.getType();
					use = cvdp;
				}
			}
		}
		if (use == null) {
			LinkedList<DataScopeInfo> list = mDataScopeMap.get(data);
			if (list == null || list.size() == 0) {
				if (Character.isLowerCase(data.charAt(0)) == true) {
					MyLogger.Error("Warning Data: " + data + "; Not declared field?");
				}
				// new Exception().printStackTrace();
				// System.exit(1);
				return;
			} else {
				DataScopeInfo dscopeinfo = GetDataScopeInfoBySearch(list, false, false, false);
				use = GetRealUseDataPool(dscopeinfo);
				oscope = dscopeinfo.getOscope();
				CheckTypeMustNull(type);
				type = dscopeinfo.getType();
			}
		}
		// MyLogger.Error("newly added data: " + data);
		use.NewlyAssignedData(oscope, data, type);
	}

	public DataScopeInfo GetDataScopeInfoBySearch(LinkedList<DataScopeInfo> list, boolean isfinal, boolean isfield,
			boolean fieldMust) {
		Iterator<DataScopeInfo> itr = list.iterator();
		while (itr.hasNext()) {
			DataScopeInfo dscopeinfo = itr.next();
			if (dscopeinfo.isFinal() != isfinal) {
				continue;
			}
			if (fieldMust) {
				if (dscopeinfo.isField() != isfield) {
					continue;
				}
			}
			return dscopeinfo;
		}
		return null;
	}

	public String GetDataOffsetInfo(String data, boolean isFieldUseOrUpdate, boolean isCommonUseOrUpdate) {
		/*if (data.equals("arr"))
		{
			MyLogger.Info("==================ddfdf==================");
		}*/
		DataScopeInfo firstinfo = GetLastClassIdInList(data);
		if (firstinfo != null) {
			/*
			 * if (data.equals("a")) { MyLogger.Info(
			 * "Get a:isFieldUseOrUpdate:" + isFieldUseOrUpdate+
			 * " isCommonUseOrUpdate:"+isCommonUseOrUpdate); }
			 */
			LinkedList<DataScopeInfo> list = mDataScopeMap.get(data);
			VDataPool use = null;
			DataScopeInfo nowinfo = null;
			Iterator<DataScopeInfo> itr = list.iterator();
			while (itr.hasNext()) {
				nowinfo = itr.next();
				if (isFieldUseOrUpdate) {
					if (nowinfo.isField() == false) {
						continue;
					}
				}
				if (isCommonUseOrUpdate) {
					if (nowinfo.isField() == true) {
						continue;
					}
				}
				
				// MyLogger.Info("nowinfo isfield:"+nowinfo.isField()+";isfinal:"+nowinfo.isFinal());
				
				use = GetRealUseDataPool(nowinfo);
				break;
			}
			OneScope dataScope = nowinfo.getOscope();
			String type = nowinfo.getType();
			if (use == null)
			{
				MyLogger.Error("Debuging use is null: data is:" +data);
				return null;
			}
			Integer exactoffset = use.GetExactDataOffsetInDataOwnScope(dataScope, data, type);
			if (exactoffset == null || classstack.getSize() == 0) {
				if (classstack.getSize() == 0) {
					MyLogger.Error("What the fuck, data does not have scope? The system will exit.");
					System.exit(1);
				}
				return null;
			}
			OneScope currentscope = classstack.getScope(classstack.getSize() - 1);
			String indicator = nowinfo.isFinal() ? (nowinfo.isField() ? "D" : "X") : (nowinfo.isField() ? "F" : "C");
			return "$" + indicator + Math.abs(dataScope.getLevel() - currentscope.getLevel())
					+ GCodeMetaInfo.OffsetSpiliter + OffsetLibrary.GetOffsetDescription(exactoffset);
		} else {
			if (Character.isLowerCase(data.charAt(0)) == true) {
				MyLogger.Error("Warning Data: " + data + "; Not declared field?" + " ;isFieldUseOrUpdate:"
						+ isFieldUseOrUpdate + " ;isCommonUseOrUpdate:" + isCommonUseOrUpdate);
			}
			// new Exception().printStackTrace();
			// System.exit(1);
		}
		return null;
	}

	private VDataPool GetRealUseDataPool(DataScopeInfo firstinfo) {
		VDataPool use = null;
		if (firstinfo.isField() && firstinfo.isFinal()) {
			use = ffvdp;
		}
		if (firstinfo.isField() && !firstinfo.isFinal()) {
			use = fvdp;
		}
		if (!firstinfo.isField() && firstinfo.isFinal()) {
			use = fcvdp;
		}
		if (!firstinfo.isField() && !firstinfo.isFinal()) {
			use = cvdp;
		}
		return use;
	}

	public void EnterBlock(int scopeid) {
		int level = classstack.getSize();
		OneScope oscope = classstack.PushBack(scopeid, level);

		fvdp.AScopeCreated(oscope);
		cvdp.AScopeCreated(oscope);

		ffvdp.AScopeCreated(oscope);
		fcvdp.AScopeCreated(oscope);
	}

	public void ExitBlock() {
		OneScope oscope = classstack.pop();

		fvdp.AScopeDestroyed(oscope);
		cvdp.AScopeDestroyed(oscope);

		ffvdp.AScopeDestroyed(oscope);
		fcvdp.AScopeDestroyed(oscope);

		LinkedList<String> datalist = null;
		datalist = mFieldScopeDataMap.remove(oscope);
		DeleteDataScopeOfScope(oscope, datalist);
		datalist = mFinalFieldScopeDataMap.remove(oscope);
		DeleteDataScopeOfScope(oscope, datalist);
		datalist = mCommonScopeDataMap.remove(oscope);
		DeleteDataScopeOfScope(oscope, datalist);
		datalist = mFinalCommonScopeDataMap.remove(oscope);
		DeleteDataScopeOfScope(oscope, datalist);

		datalist = mFieldScopeTypeMap.remove(oscope);
		datalist = mFinalFieldScopeTypeMap.remove(oscope);
		datalist = mCommonScopeTypeMap.remove(oscope);
		datalist = mFinalCommonScopeTypeMap.remove(oscope);
	}

	private void DeleteDataScopeOfScope(OneScope oscope, LinkedList<String> datalist) {
		DeleteDataScopeOfScope(oscope, datalist, false);
	}

	private void DeleteDataScopeOfScope(OneScope oscope, LinkedList<String> datalist, boolean deleteOnlyCommon) {
		if (datalist == null) {
			return;
		}
		Iterator<String> itr = datalist.iterator();
		while (itr.hasNext()) {
			String data = itr.next();
			LinkedList<DataScopeInfo> list = mDataScopeMap.get(data);
			while (list.size() > 0 && list.get(0).getOscope().getLevel() == oscope.getLevel()) {
				if (deleteOnlyCommon) {
					if (list.get(0).isField() == true) {
						break;
					}
				}
				list.removeFirst();
			}
		}
	}

	public void ResetCurrentClassField() {
		// just check now only one size.
		// CheckOnlyOneSize(mCommonScopeDataMap);

		// remove commons in datascope list, then clear common data map.
		LinkedList<String> datalist = null;
		Iterator<OneScope> itr2 = classstack.GetIterator();
		while (itr2.hasNext())
		{
			OneScope onescope = itr2.next();
			datalist = mCommonScopeDataMap.remove(onescope);
			DeleteDataScopeOfScope(onescope, datalist, true);
			datalist = mFinalCommonScopeDataMap.remove(onescope);
			DeleteDataScopeOfScope(onescope, datalist, true);
		}

		// then clear common type map.
		mCommonScopeTypeMap.clear();
		mFinalCommonScopeTypeMap.clear();

		cvdp.ClearAll();
		fcvdp.ClearAll();
		
		Iterator<OneScope> itr = classstack.GetIterator();
		while (itr.hasNext())
		{
			OneScope onescope = itr.next();
			fvdp.ResetClassScope(onescope, mFieldScopeDataMap.get(onescope), mFieldScopeTypeMap.get(onescope));
			ffvdp.ResetClassScope(onescope, mFinalFieldScopeDataMap.get(onescope),
					mFinalFieldScopeTypeMap.get(onescope));
		}
	}

	protected void CheckOnlyOneSize(Map<OneScope, LinkedList<String>> scopeDataMap) {
		if (scopeDataMap.size() > 1) {
			MyLogger.Error("ScopeData size:" + scopeDataMap.size()
					+ ". What the fuck, in first level method has two class levels? Serious error, the system will exit.");
			new Exception().printStackTrace();
			System.exit(1);
		}
	}

	// Due to list is reverse order, so which means first.
	private DataScopeInfo GetLastClassIdInList(String data) {
		LinkedList<DataScopeInfo> list = mDataScopeMap.get(data);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return null;
	}

	public boolean ContainsScope(Integer equid) {
		return classstack.isIdContained(equid);
	}

	public int GetFirstClassId() {
		return classstack.getScope(0).getID();
	}

}