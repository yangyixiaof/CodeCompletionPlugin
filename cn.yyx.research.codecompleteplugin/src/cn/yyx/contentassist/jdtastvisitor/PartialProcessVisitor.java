package cn.yyx.contentassist.jdtastvisitor;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;

import cn.yyx.contentassist.commonutils.ASTOffsetInfo;
import cn.yyx.research.language.simplified.JDTHelper.SimplifiedCodeGenerateASTVisitor;

public class PartialProcessVisitor extends SimplifiedCodeGenerateASTVisitor {
	
	int offset = -1;
	ASTOffsetInfo aoi = null;
	
	public PartialProcessVisitor(int offset, ASTOffsetInfo aoi) {
		this.offset = offset;
		this.aoi = aoi;
	}
	
	@Override
	public boolean preVisit2(ASTNode node) {
		boolean supres = super.preVisit2(node);
		boolean nowres = true;
		int tstart = node.getStartPosition();
		int tend = tstart + node.getLength();
		if (offset < tstart)
		{
			nowres = false;
			if ((node instanceof FieldDeclaration))
			{
				nowres = true;
			}
		}
		if (offset >= tend)
		{
			nowres = false;
			if (!(node instanceof BodyDeclaration))
			{
				nowres = true;
			}
			if ((node instanceof FieldDeclaration) || (node instanceof EnumConstantDeclaration))
			{
				nowres = true;
			}
		}
		if ((offset >= tstart) && (offset < tend))
		{
			if (node instanceof AnonymousClassDeclaration)
			{
				aoi.setInAnonymousClass(true);
			}
		}
		boolean couldcontinue = nowres && supres;
		if (node instanceof AbstractTypeDeclaration)
		{
			if (couldcontinue)
			{
				SimpleBodysHelper.JudgeIfInFieldLevel((AbstractTypeDeclaration)node, offset, aoi);
			}
		}
		return couldcontinue;
	}
	
	
	
}