package cn.yyx.contentassist.jdtastvisitor;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnonymousClassDeclaration;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.DoStatement;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.LambdaExpression;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.Type;

import cn.yyx.contentassist.codecompletion.CodeCompletionMetaInfo;
import cn.yyx.contentassist.commonutils.ASTOffsetInfo;
import cn.yyx.research.language.Utility.StringUtil;
import cn.yyx.research.language.simplified.JDTHelper.SimplifiedCodeGenerateASTVisitor;
import cn.yyx.research.language.simplified.JDTHelper.SimplifiedFieldProcessASTVisitor;
import cn.yyx.research.language.simplified.JDTManager.TypeUtil;

public class PartialProcessVisitor extends SimplifiedCodeGenerateASTVisitor {
	
	int offset = -1;
	ASTOffsetInfo aoi = null;
	
	Map<Integer, Boolean> continuerecord = new TreeMap<Integer, Boolean>();
	
	Queue<BreakRuleRange> brrlist = new LinkedList<BreakRuleRange>();
	
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
		
		// set do-while break rule range.
		if (node instanceof DoStatement)
		{
			if (couldcontinue)
			{
				DoStatement don = (DoStatement)node;
				brrlist.add(new BreakRuleRange(don.getExpression().getStartPosition(), don.getExpression().getStartPosition() + don.getLength()));
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
		if (node instanceof MethodDeclaration && couldcontinue)
		{
			Type rt2 = ((MethodDeclaration)node).getReturnType2();
			if (rt2 != null)
			{
				String rtcode = TypeUtil.TypeCode(rt2, true);
				int ednum = ((MethodDeclaration)node).getExtraDimensions();
				if (ednum > 0) {
					rtcode += StringUtil.GenerateDuplicates("[]", ednum);
				}
				aoi.setMethodDeclarationReturnType(rtcode);
			}
		}
		if (node instanceof LambdaExpression && couldcontinue)
		{
			aoi.setMethodDeclarationReturnType(null);
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
		
		if (node instanceof DoStatement)
		{
			if (GetCouldContinue(node))
			{
				brrlist.poll();
			}
		}
		
		DeleteCouldContinue(node);
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
	
	private void DeleteCouldContinue(ASTNode node)
	{
		int hashcode = node.hashCode();
		continuerecord.remove(hashcode);
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
		if (CheckBreakRule(node))
		{
			nowres = true;
		}
		boolean couldcontinue = nowres && supres;
		return couldcontinue;
	}
	
	private boolean CheckBreakRule(ASTNode node)
	{
		Iterator<BreakRuleRange> itr = brrlist.iterator();
		while (itr.hasNext())
		{
			BreakRuleRange brr = itr.next();
			int start = node.getStartPosition();
			int end = start + node.getLength();
			if (start >= brr.getStartpos() && end <= brr.getStoppos())
			{
				return true;
			}
		}
		return false;
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
	
	class BreakRuleRange
	{
		private int startpos = 0;
		private int stoppos = 0;
		
		public BreakRuleRange(int startpos, int stoppos) {
			this.setStartpos(startpos);
			this.setStoppos(stoppos);
		}

		public int getStartpos() {
			return startpos;
		}

		public void setStartpos(int startpos) {
			this.startpos = startpos;
		}

		public int getStoppos() {
			return stoppos;
		}

		public void setStoppos(int stoppos) {
			this.stoppos = stoppos;
		}
		
	}
	
}