package cn.yyx.contentassist.codesynthesis;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import cn.yyx.contentassist.codesynthesis.CSNode;
import cn.yyx.contentassist.codesynthesis.typeutil.TypeCheckHelper;
import cn.yyx.contentassist.commonutils.CSNodeType;
import cn.yyx.contentassist.commonutils.MapHelper;
import cn.yyx.contentassist.commonutils.TypeCheck;

public class CSNodeHelper {
	
	public static CSNode ConcatTwoNodes(CSNode one, CSNode two, String connector, int maxsize)
	{
		CSNode cs = new CSNode(CSNodeType.HalfFullExpression);
		Map<String, TypeCheck> po = ConcatTwoNodesDatas(one, two, connector, maxsize);
		cs.setDatas(po);
		cs.MergeCSNodeNonDataContent(one, two);
		return cs;
	}
	
	public static Map<String, TypeCheck> ConcatTwoNodesDatas(CSNode one, CSNode two, String connector, int maxsize)
	{
		if (connector == null) {
			connector = "";
		}
		Map<String, TypeCheck> result = new TreeMap<String, TypeCheck>();
		String onepre = one.getPrefix();
		String onepost = one.getPostfix();
		String twopre = two.getPrefix();
		String twopost = two.getPostfix();
		Set<String> codes = one.getDatas().keySet();
		if (codes == null || codes.size() == 0)
		{
			return MapHelper.CloneDatas(two.getDatas());
		}
		Iterator<String> c1itr = codes.iterator();
		while (c1itr.hasNext())
		{
			String c1 = c1itr.next();
			TypeCheck tc1 = one.getDatas().get(c1);
			String c1res = onepre + c1 + onepost;
			Set<String> code2s = two.getDatas().keySet();
			Iterator<String> c2itr = code2s.iterator();
			while (c2itr.hasNext())
			{
				String c2 = c2itr.next();
				String c2res = twopre + c2 + twopost;
				String res = c1res + connector + c2res;
				result.put(res, tc1);
			}
		}
		return result;
	}

	public static Map<String, TypeCheck> ConcatTwoNodesDatasWithTypeChecking(CSNode one, CSNode two, int i) {
		Map<String, TypeCheck> result = new TreeMap<String, TypeCheck>();
		String onepre = one.getPrefix();
		String onepost = one.getPostfix();
		String twopre = two.getPrefix();
		String twopost = two.getPostfix();
		Set<String> codes = one.getDatas().keySet();
		Iterator<String> c1itr = codes.iterator();
		while (c1itr.hasNext())
		{
			String c1 = c1itr.next();
			TypeCheck tc1 = one.getDatas().get(c1);
			String c1res = onepre + c1 + onepost;
			Set<String> code2s = two.getDatas().keySet();
			Iterator<String> c2itr = code2s.iterator();
			while (c2itr.hasNext())
			{
				String c2 = c2itr.next();
				TypeCheck tc2 = two.getDatas().get(c2);
				String c2res = twopre + c2 + twopost;
				if (TypeCheckHelper.CanBeMutualCast(tc1, tc2))
				{
					String res = c1res + c2res;
					result.put(res, tc1);
				}
			}
		}
		return result;
	}

	public static void HandleTypeByTypeCodes(CSNode result) {
		// TODO Auto-generated method stub
		
	}
	
}