package cn.yyx.contentassist.codesynthesis.data;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.flowline.FlowLineNode;
import cn.yyx.contentassist.commonutils.ClassInstanceOfUtil;

public class CSExtraData implements CSSelfClosedMergable{
	
	Map<String, Object> extras = null;
	
	public CSExtraData() {
	}
	
	public void AddExtraData(String key, Object data)
	{
		if (extras == null)
		{
			extras = new TreeMap<String, Object>();
		}
		extras.put(key, data);
	}
	
	public Object GetExtraData(String key)
	{
		if (extras == null)
		{
			return null;
		}
		return extras.get(key);
	}
	
	public boolean IsEmpty()
	{
		return extras == null || extras.size() == 0;
	}
	
	@Override
	public Object SelfClosedMerge(Object tobj) throws CodeSynthesisException {
		CSExtraData t = (CSExtraData)tobj;
		if (IsEmpty())
		{
			return t;
		}
		if (t.IsEmpty())
		{
			return this;
		}
		// both have values.
		CSExtraData result = new CSExtraData();
		Set<String> keys = extras.keySet();
		Iterator<String> kitr = keys.iterator();
		while (kitr.hasNext())
		{
			String key = kitr.next();
			Object v = extras.get(key);
			Object tv = t.extras.get(key);
			if (tv != null)
			{
				if (ClassInstanceOfUtil.ObjectInstanceOf(v, CSSelfClosedMergable.class))
				{
					if (!(tv.getClass().equals(v.getClass())))
					{
						throw new CodeSynthesisException("the corresponding class can not be multi casted.");
					}
					else
					{
						result.AddExtraData(key, ((CSSelfClosedMergable) v).SelfClosedMerge(tv));
					}
				}
				else
				{
					if (tv instanceof FlowLineNode)
					{
						// directly add the later.
						result.AddExtraData(key, tv);
					} else {
						throw new CodeSynthesisException("the corresponding tv can not be merged. tv class:" + tv.getClass());
					}
				}
			}
			else
			{
				result.AddExtraData(key, v);
			}
		}
		Set<String> tkeys = t.extras.keySet();
		Iterator<String> titr = tkeys.iterator();
		while (titr.hasNext())
		{
			String tkey = titr.next();
			if (!extras.containsKey(tkey))
			{
				Object tv = t.extras.get(tkey);
				result.AddExtraData(tkey, tv);
			}
		}
		return result;
	}
	
	@Override
	public String toString() {
		return "extras:" + extras.toString();
	}
	
}