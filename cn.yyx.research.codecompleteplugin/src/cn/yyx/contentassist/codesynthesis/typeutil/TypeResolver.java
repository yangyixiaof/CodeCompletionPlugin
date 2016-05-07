package cn.yyx.contentassist.codesynthesis.typeutil;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;

import cn.yyx.contentassist.commonutils.YJCache;
import cn.yyx.contentassist.specification.SearchSpecificationOfAReference;
import cn.yyx.contentassist.specification.TypeMember;

public class TypeResolver {
	
	public static final int MaxTryTimes = 3;
	
	public static YJCache<CCType> classcache = new YJCache<CCType>();
	
	// Class<?>
	public static CCType ResolveType(String type, JavaContentAssistInvocationContext javacontext)
	{
		CCType cls = classcache.GetCachedContent(type);
		if (cls != null)
		{
			return cls;
		}
		List<TypeMember> tmlist = SearchSpecificationOfAReference.SearchTypeSpecificationByPrefix(type, javacontext, null);
		if (tmlist == null || tmlist.size() == 0)
		{
			CCType cct = new CCType();
			cct.AddPossibleClass(Object.class, "Object");
			return cct;
		}
		return new CCType(tmlist);
	}
	
	public static List<CCType> ResolveType(List<String> types, JavaContentAssistInvocationContext javacontext)
	{
		List<CCType> result = new LinkedList<CCType>();
		Iterator<String> itr = types.iterator();
		while (itr.hasNext())
		{
			String tp = itr.next();
			result.add(ResolveType(tp, javacontext));
		}
		return result;
	}
	
}