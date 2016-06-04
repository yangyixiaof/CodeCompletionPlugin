package cn.yyx.contentassist.jdtastvisitor;

import java.util.Map;
import java.util.TreeMap;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.LambdaExpression;

import cn.yyx.contentassist.codecompletion.CodeCompletionMetaInfo;
import cn.yyx.contentassist.commonutils.ASTOffsetInfo;
import cn.yyx.research.language.simplified.JDTHelper.SimplifiedCodeGenerateASTVisitor;
import cn.yyx.research.language.simplified.JDTHelper.SimplifiedFieldProcessASTVisitor;

public class PartialProcessVisitor extends SimplifiedCodeGenerateASTVisitor {
	
	int offset = -1;
	ASTOffsetInfo aoi = null;
	
	Map<Integer, Boolean> continuerecord = new TreeMap<Integer, Boolean>();
	
	public PartialProcessVisitor(int offset, ASTOffsetInfo aoi) {
		this.offset = offset;
		this.aoi = aoi;
	}
	
	@Override
	public boolean preVisit2(ASTNode node) {
		//if (offset > node.getStartPosition())
		//{
			fotp.PostIsBegin(node);
		//}
		boolean couldcontinue = couldContinue(node);
		RecordCouldContinue(node, couldcontinue);
		if (NeedSpecialTreat(node))
		{
			if (couldcontinue)
			{
				EnterBlock(node);
			}
		}
		
		// set the field of if in field aoi.
		if (node instanceof AbstractTypeDeclaration)
		{
			if (couldcontinue)
			{
				SimpleBodysHelper.JudgeIfInFieldLevel((AbstractTypeDeclaration)node, offset, aoi);
			}
		}
		return couldcontinue;
	}
	
	@Override
	protected SimplifiedFieldProcessASTVisitor GenerateSimplifiedFieldProcessASTVisitor(ASTNode node) {
		return new SimplifiedFieldProcessASTVisitor(this, node);
	}
	
	@Override
	public void postVisit(ASTNode node) {
		// System.err.println("node:"+node);
		//if (offset > node.getStartPosition() + node.getLength())
		//{
			fotp.PreIsOver(node);
		//}
		if (NeedSpecialTreat(node))
		{
			if (GetCouldContinue(node))
			{
				if (!IsEnclosed(node))
				{
					ExitBlock(node);
				}
			}
		}
	}

	/*@Override
	protected void ExitCodeSwitchScope(ASTNode node)
	{
		if (!IsEnclosed(node))
		{
			super.ExitCodeSwitchScope(node);
		}
	}*/
	
	private void RecordCouldContinue(ASTNode node, boolean couldcontinue)
	{
		if (NeedSpecialTreat(node))
		{
			int hashcode = node.hashCode();
			if (continuerecord.containsKey(hashcode))
			{
				if (CodeCompletionMetaInfo.DebugMode)
				{
					System.err.println("What the fuck, continuerecord key conflicts.");
					System.exit(1);
				}
				throw new Error("What the fuck, continuerecord key conflicts.");
			}
			continuerecord.put(hashcode, couldcontinue);
		}
	}
	
	private boolean GetCouldContinue(ASTNode node)
	{
		int hashcode = node.hashCode();
		return continuerecord.get(hashcode);
	}
	
	private boolean couldContinue(ASTNode node)
	{
		boolean supres = true;
		Boolean forbid = runforbid.GetNodeHelp(node.hashCode());
		if (forbid != null && forbid == true)
		{
			supres = supres && false;
		}
		boolean nowres = false;
		int tstart = node.getStartPosition();
		int tend = tstart + node.getLength();
		if (offset < tstart)
		{
			if ((node instanceof FieldDeclaration))
			{
				nowres = true;
			}
		}
		if (offset >= tend)
		{
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
			nowres = true;
			if (node instanceof AnonymousClassDeclaration || node instanceof LambdaExpression)
			{
				aoi.setInAnonymousClass(true);
			}
		}
		boolean couldcontinue = nowres && supres;
		return couldcontinue;
	}
	
	private boolean IsEnclosed(ASTNode node)
	{
		int tstart = node.getStartPosition();
		int tend = tstart + node.getLength();
		if ((offset >= tstart) && (offset < tend))
		{
			return true;
		}
		return false;
	}
	
}