package cn.yyx.contentassist.jdtastvisitor;

import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.BodyDeclaration;

import cn.yyx.contentassist.commonutils.ASTOffsetInfo;

public class SimpleBodysHelper {
	
	@SuppressWarnings("unchecked")
	public static void JudgeIfInFieldLevel(AbstractTypeDeclaration at, int offset, ASTOffsetInfo aoi)
	{
		List<BodyDeclaration> bds = at.bodyDeclarations();
		Iterator<BodyDeclaration> itr = bds.iterator();
		while (itr.hasNext())
		{
			BodyDeclaration bd = itr.next();
			int tstart = bd.getStartPosition();
			int tend = tstart + bd.getLength();
			if (offset < tstart)
			{
				// field.
				aoi.setInFieldLevel(true);
				break;
			}
			if (offset <= tend)
			{
				break;
			}
		}
	}
	
}