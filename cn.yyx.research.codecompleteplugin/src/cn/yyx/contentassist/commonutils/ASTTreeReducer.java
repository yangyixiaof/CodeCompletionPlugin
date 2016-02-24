package cn.yyx.contentassist.commonutils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;

public class ASTTreeReducer {
	
	@SuppressWarnings("unchecked")
	public static AbstractTypeDeclaration GetSimplifiedContent(List<AbstractTypeDeclaration> types, int offset, ASTOffsetInfo aoi)
	{
		for (AbstractTypeDeclaration at : types)
		{
			int tstart = at.getStartPosition();
			int tend = tstart + at.getLength();
			if (offset >= tstart && offset < tend)
			{
				List<BodyDeclaration> bds = at.bodyDeclarations();
				OneClassNecessaryContentRetain(bds, offset, at, aoi);
				return at;
			}
			// System.out.println("type:"+at.getName().toString()+";start pos:"+start+";endpos:"+end+";invokeoffset:"+offset);
		}
		return null;
	}
	
	private static void OneClassNecessaryContentRetain(List<BodyDeclaration> bds, int offset, AbstractTypeDeclaration parent, ASTOffsetInfo aoi)
	{
		Iterator<BodyDeclaration> itr2 = bds.iterator();
		boolean onlyRetainField = false;
		Object retainRef = null;
		while (itr2.hasNext())
		{
			BodyDeclaration bd = itr2.next();
			if (offset < bd.getStartPosition())
			{
				// field.
				onlyRetainField = true;
				aoi.setInFieldLevel(true);
				break;
			}
			if (offset < bd.getStartPosition() + bd.getLength())
			{
				// be caught.
				retainRef = bd;
				if (IsNotCommonClassDeclaration(bd) && !IsRawBlockBody(bd))
				{
					System.err.println("Not supportted completion type.");
					new Exception("Not supportted completion type.").printStackTrace();
				}
				break;
			}
		}
		Iterator<BodyDeclaration> itr3 = bds.iterator();
		while (itr3.hasNext())
		{
			BodyDeclaration bd = itr3.next();
			if (onlyRetainField && IsRawBlockBody(bd))
			{
				bd.delete();
			}
			if (!onlyRetainField && IsRawBlockBody(bd))
			{
				if (bd != retainRef)
				{
					bd.delete();
				}
				else
				{
					List<ASTNode> needToDelete = new ArrayList<ASTNode>();
					bd.accept(new DetailedASTTreeReducerVisitor(offset, needToDelete, aoi));
					Iterator<ASTNode> itr = needToDelete.iterator();
					while (itr.hasNext())
					{
						ASTNode an = itr.next();
						an.delete();
					}
					needToDelete.clear();
				}
			}
		}
	}
	
	// include anonymous class declaration.
	private static boolean IsNotCommonClassDeclaration(BodyDeclaration bd)
	{
		if (bd instanceof AnnotationTypeMemberDeclaration || bd instanceof EnumConstantDeclaration || bd instanceof FieldDeclaration || bd instanceof Initializer || bd instanceof MethodDeclaration)
		{
			return true;
		}
		return false;
	}
	
	private static boolean IsRawBlockBody(BodyDeclaration bd)
	{
		if (bd instanceof Initializer || bd instanceof MethodDeclaration)
		{
			return true;
		}
		return false;
	}
	
}