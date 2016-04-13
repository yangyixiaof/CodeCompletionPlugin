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
	
	public static YJCache<Class<?>> classcache = new YJCache<Class<?>>();
	
	public static Class<?> ResolveType(String type, JavaContentAssistInvocationContext javacontext)
	{
		Class<?> cls = classcache.GetCachedContent(type);
		if (cls != null)
		{
			return cls;
		}
		List<TypeMember> tmlist = SearchSpecificationOfAReference.SearchTypeSpecificationByPrefix(type, javacontext, null);
		// int trysize = Math.min(MaxTryTimes, tmlist.size());
		if (tmlist == null || tmlist.size() == 0)
		{
			return Object.class;
		}
		return tmlist.get(0).getTypeclass();
	}
	
	public static List<Class<?>> ResolveType(List<String> types, JavaContentAssistInvocationContext javacontext)
	{
		List<Class<?>> result = new LinkedList<Class<?>>();
		Iterator<String> itr = types.iterator();
		while (itr.hasNext())
		{
			String tp = itr.next();
			result.add(ResolveType(tp, javacontext));
		}
		return result;
	}
	
}