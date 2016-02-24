package cn.yyx.contentassist.commonutils;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;

public class DetailedASTTreeReducerVisitor extends ASTVisitor{
	
	int offset = -1;
	List<ASTNode> needToDelete = null;
	ASTOffsetInfo aoi = null;
	
	public DetailedASTTreeReducerVisitor(int offset, List<ASTNode> needToDelete, ASTOffsetInfo aoi) {
		this.offset = offset;
		this.needToDelete = needToDelete;
		this.aoi = aoi;
	}
	
	@Override
	public boolean preVisit2(ASTNode node) {
		if (offset > node.getStartPosition() && (offset < node.getStartPosition() + node.getLength()))
		{
			if (node instanceof AnonymousClassDeclaration)
			{
				aoi.setInAnonymousClass(true);
			}
		}
		if (offset < node.getStartPosition())
		{
			needToDelete.add(node);
			return false;
		}
		return super.preVisit2(node);
	}
	
	@Override
	public void postVisit(ASTNode node) {
		super.postVisit(node);
	}
	
}