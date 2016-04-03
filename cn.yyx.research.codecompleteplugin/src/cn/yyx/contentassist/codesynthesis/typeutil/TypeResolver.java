package cn.yyx.contentassist.codesynthesis.typeutil;

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
	
}