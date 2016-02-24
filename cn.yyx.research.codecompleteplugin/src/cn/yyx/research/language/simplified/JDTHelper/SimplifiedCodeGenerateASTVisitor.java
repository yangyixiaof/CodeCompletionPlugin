package cn.yyx.research.language.simplified.JDTHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.TreeMap;

import org.eclipse.jdt.core.dom.*;

import cn.yyx.research.language.JDTManager.FirstOrderTask;
import cn.yyx.research.language.JDTManager.FirstOrderTaskPool;
import cn.yyx.research.language.JDTManager.GCodeMetaInfo;
import cn.yyx.research.language.JDTManager.JCScope;
import cn.yyx.research.language.JDTManager.MethodWindow;
import cn.yyx.research.language.JDTManager.NodeCode;
import cn.yyx.research.language.JDTManager.NodeHelpManager;
import cn.yyx.research.language.JDTManager.OffsetLibrary;
import cn.yyx.research.language.JDTManager.OneJavaFileAnonymousClassesCode;
import cn.yyx.research.language.JDTManager.OneJavaFileCode;
import cn.yyx.research.language.JDTManager.OtherCodeManager;
import cn.yyx.research.language.JDTManager.ReferenceHintLibrary;
import cn.yyx.research.language.JDTManager.ScopeDataManager;
import cn.yyx.research.language.Utility.MyLogger;
import cn.yyx.research.language.simplified.JDTManager.AnonymousClassPoolInOneJavaFile;
import cn.yyx.research.language.simplified.JDTManager.ConflictASTNodeHashCodeError;
import cn.yyx.research.language.simplified.JDTManager.JavaCode;

public class SimplifiedCodeGenerateASTVisitor extends ASTVisitor {

	protected OtherCodeManager ocm = new OtherCodeManager();
	protected OneJavaFileCode ojfc = new OneJavaFileCode();
	protected JavaCode jc = ojfc;
	protected MethodWindow mw = new MethodWindow();
	protected AnonymousClassPoolInOneJavaFile acp = new AnonymousClassPoolInOneJavaFile();
	protected FirstOrderTaskPool fotp = new FirstOrderTaskPool();
	protected ScopeDataManager sdm = new ScopeDataManager();
	protected JCScope cjcs = new JCScope();
	protected JCScope ljcs = new JCScope();
	protected Integer FirstLevelClass = null;
	protected Stack<String> VeryRecentDeclaredType = new Stack<String>();
	protected boolean VeryRecentIsFieldDeclared = false;
	protected boolean VeryRecentNotGenerateCode = false;
	protected NodeHelpManager<Boolean> berefered = new NodeHelpManager<Boolean>();
	protected NodeHelpManager<Boolean> bereferedAlready = new NodeHelpManager<Boolean>();
	protected NodeHelpManager<String> referedcnt = new NodeHelpManager<String>();
	protected NodeHelpManager<Integer> referhint = new NodeHelpManager<Integer>();
	protected NodeHelpManager<Boolean> refernoline = new NodeHelpManager<Boolean>();
	protected NodeHelpManager<Boolean> runpermit = new NodeHelpManager<Boolean>();
	protected NodeHelpManager<Boolean> runforbid = new NodeHelpManager<Boolean>();
	protected NodeHelpManager<Boolean> typesimp = new NodeHelpManager<Boolean>();
	protected Map<Integer, Boolean> scopeck = new TreeMap<Integer, Boolean>();
	protected Stack<NodeCode> omcanonystack = new Stack<NodeCode>();
	protected Stack<Boolean> argmutiple = new Stack<Boolean>();
	protected NodeCode omc = new NodeCode(argmutiple);
	public static final int StrictedNameLength = 2;

	{
		cjcs.SetDescription("Class Declaration.");
		ljcs.SetDescription("Label Declaration.");
	}
	
	@Override
	public void preVisit(ASTNode node) {
		fotp.PostIsBegin(node);
		super.preVisit(node);
	}

	@Override
	public void postVisit(ASTNode node) {
		fotp.PreIsOver(node);
		super.postVisit(node);
	}

	@Override
	public boolean visit(ImportDeclaration node) {
		return false;
	}

	@Override
	public boolean visit(AnnotationTypeDeclaration node) {
		return false;
	}

	@Override
	public boolean visit(AnnotationTypeMemberDeclaration node) {
		return false;
	}

	@Override
	public boolean visit(PackageDeclaration node) {
		return false;
	}

	@Override
	public boolean visit(SingleMemberAnnotation node) {
		return false;
	}

	@Override
	public boolean visit(NormalAnnotation node) {
		return false;
	}

	@Override
	public boolean visit(MarkerAnnotation node) {
		return false;
	}

	@Override
	public boolean visit(ThisExpression node) {
		int nodehashcode = node.hashCode();
		if (NodeIsRefered(nodehashcode)) {
			String nodecode = "this";
			Name name = node.getQualifier();
			if (name != null) {
				if (name instanceof SimpleName) {
					nodecode += "." + name.toString();
				} else {
					nodecode += "." + ((QualifiedName) name).getName().toString();
				}
			}
			referedcnt.AddNodeHelp(nodehashcode, nodecode);
		}
		return false;
	}

	@Override
	public boolean visit(TagElement node) {
		return false;
	}

	@Override
	public boolean visit(Modifier node) {
		return false;
	}

	@Override
	public boolean visit(MethodRefParameter node) {
		return false;
	}

	@Override
	public boolean visit(MethodRef node) {
		return false;
	}

	@Override
	public boolean visit(MemberValuePair node) {
		return false;
	}

	@Override
	public boolean visit(MemberRef node) {
		return false;
	}

	@Override
	public boolean visit(LineComment node) {
		return false;
	}

	@Override
	public boolean visit(Javadoc node) {
		return false;
	}

	@Override
	public boolean visit(EmptyStatement node) {
		return false;
	}

	@Override
	public boolean visit(TextElement node) {
		return false;
	}

	@Override
	public boolean visit(Dimension node) {
		return false;
	}

	@Override
	public boolean visit(CompilationUnit node) {
		return super.visit(node);
	}

	@Override
	public boolean visit(BlockComment node) {
		return false;
	}

	@Override
	public boolean visit(AssertStatement node) {
		return false;
	}

	@Override
	public boolean visit(TryStatement node) {
		GenerateOneLine(GCodeMetaInfo.DescriptionHint + "try", false, false, false, true, null);
		ASTNode lastbeforefinally = node.getBody();
		if (node.catchClauses() != null && node.catchClauses().size() > 0) {
			lastbeforefinally = (ASTNode) node.catchClauses().get(node.catchClauses().size() - 1);
		}
		if (node.getFinally() != null) {
			AddFirstOrderTask(new FirstOrderTask(lastbeforefinally, null, node, true) {
				@Override
				public void run() {
					GenerateOneLine(GCodeMetaInfo.DescriptionHint + "finally", false, false, false, true, null);
				}
			});
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(TypeDeclarationStatement node) {
		return super.visit(node);
	}

	@Override
	public boolean visit(TypeParameter node) {
		return false;
	}

	@Override
	public boolean visit(TypeDeclaration node) {
		FlushCode();
		boolean inner = false;
		if (FirstLevelClass == null) {
			FirstLevelClass = node.hashCode();
		} else {
			inner = true;
		}
		EnterBlock(node);
		runforbid.AddNodeHelp(node.getName().hashCode(), true);
		String declareHint = inner ? GCodeMetaInfo.ClassInnerDeclarationHint : GCodeMetaInfo.ClassDeclarationHint;
		GenerateOneLine(declareHint + node.getName().toString(), false, false, false, true, null);
		SimplifiedFieldProcessASTVisitor sfpa = new SimplifiedFieldProcessASTVisitor(this);
		node.accept(sfpa);
		return super.visit(node);
	}

	@Override
	public void endVisit(TypeDeclaration node) {
		FlushCode();
		if (FirstLevelClass == node.hashCode()) {
			FirstLevelClass = null;
		}
		ExitBlock(node);
		runforbid.DeleteNodeHelp(node.getName().hashCode());
	}

	@Override
	public boolean visit(AnonymousClassDeclaration node) {
		// MyLogger.Info("AnonymousClassDeclaration Begin");
		// MyLogger.Info(node);
		// MyLogger.Info("AnonymousClassDeclaration End");
		EnterBlock(node);
		jc = acp.EnterAnonymousClass(mw);
		omcanonystack.push(omc);
		omc = new NodeCode(argmutiple);
		AnonymousClassDeclarationCodeFileAddMethodWindow();
		SimplifiedFieldProcessASTVisitor sfpa = new SimplifiedFieldProcessASTVisitor(this);
		node.accept(sfpa);
		return super.visit(node);
	}

	@Override
	public void endVisit(AnonymousClassDeclaration node) {
		FlushCode();
		omc = omcanonystack.pop();
		if (omc == null) {
			omc = (new NodeCode(argmutiple));
		}
		jc = ojfc;
		acp.ExitAnonymousClass();
		ExitBlock(node);
	}

	@Override
	public boolean visit(ExpressionStatement node) {
		return super.visit(node);
	}

	@Override
	public void endVisit(ExpressionStatement node) {
		Expression expr = node.getExpression();
		if (expr != null) {
			GenerateEndInfo(GCodeMetaInfo.DescriptionHint + GCodeMetaInfo.EndOfAStatement);
			// AppendEndInfoToLast(GCodeMetaInfo.EndOfAStatement);
		}
	}

	@Override
	public boolean visit(Initializer node) {
		// Do nothing now.
		// MyLogger.Info("Initializer:"+node);
		if (isFirstLevelASTNode(node) || ParentIsTypeDeclaration(node)) {
			if (omc != null && !omc.IsEmpty()) {
				PushMethodNodeCodeToJavaFileCode();
				ResetDLM();
			}
			omc = new NodeCode(argmutiple);
		}
		/*
		 * if (isFirstLevelASTNode(node)) { if (omc != null && !omc.IsEmpty()) {
		 * PushMethodNodeCodeToJavaFileCode(); } omc = new NodeCode(argmutiple);
		 * }
		 */
		GenerateOneLine(GCodeMetaInfo.Initializer + "InitialBlock", false, false, false, true, null);
		return super.visit(node);
	}

	@Override
	public void endVisit(Initializer node) {
		if (isFirstLevelASTNode(node)) {
			FlushCode();
		} else {
			OneSentenceEnd();
		}
	}

	@Override
	public boolean visit(Block node) {
		GenerateOneLine(GCodeMetaInfo.DescriptionHint + "{", false, false, false, true, null);
		return super.visit(node);
	}

	@Override
	public void endVisit(Block node) {
		GenerateOneLine(GCodeMetaInfo.DescriptionHint + "}", false, false, false, true, null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean visit(LambdaExpression node) {
		EnterBlock(node);
		SetVeryRecentNotGenerateCode(true);
		List<ASTNode> params = node.parameters();
		StringBuffer nodecode = new StringBuffer(GCodeMetaInfo.LambdaExpressionHint + "(");
		Iterator<ASTNode> itr = params.iterator();
		while (itr.hasNext()) {
			ASTNode para = itr.next();
			if (para instanceof VariableDeclarationFragment) {
				nodecode.append(GCodeMetaInfo.InferedType);
				SetVeryRecentDeclaredType(GCodeMetaInfo.HackedNoType);
			} else {
				nodecode.append(TypeCode(((SingleVariableDeclaration) para).getType(), true));
			}
			nodecode.append(',');
		}
		if (params.size() > 0) {
			nodecode.deleteCharAt(nodecode.length() - 1);
		}
		ASTNode body = node.getBody();
		String bodycode = "{}";
		if (body instanceof SimpleName || body instanceof QualifiedName) {
			bodycode = body.toString();
			runforbid.AddNodeHelp(body.hashCode(), true);
		} else {
			referhint.AddNodeHelp(body.hashCode(), ReferenceHintLibrary.DataUse);
		}
		nodecode.append(")->" + bodycode);
		GenerateOneLine(nodecode.toString(), false, false, false, true, null);
		return super.visit(node);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void endVisit(LambdaExpression node) {
		List<ASTNode> params = node.parameters();
		Iterator<ASTNode> itr = params.iterator();
		while (itr.hasNext()) {
			ASTNode para = itr.next();
			if (para instanceof VariableDeclarationFragment) {
				SetVeryRecentDeclaredType(null);
			}
		}
		ASTNode body = node.getBody();
		if (body instanceof SimpleName || body instanceof QualifiedName) {
			runforbid.DeleteNodeHelp(body.hashCode());
		} else {
			referhint.DeleteNodeHelp(body.hashCode());
		}
		SetVeryRecentNotGenerateCode(false);
		ExitBlock(node);
	}

	@Override
	public boolean visit(ExpressionMethodReference node) {
		runforbid.AddNodeHelp(node.getName().hashCode(), true);
		Integer hint = referhint.GetNodeHelp(node.hashCode());
		CheckHint(hint);
		ExpressionReferPreHandle(node.getExpression(), hint);
		return super.visit(node);
	}

	@Override
	public void endVisit(ExpressionMethodReference node) {
		// expression reference should be considered more carefully.
		ExpressionReferPostHandle(node, node.getExpression(), "::", GCodeMetaInfo.MethodReferenceHint,
				node.getName().toString(), false, false, false, false, false);
		runforbid.DeleteNodeHelp(node.getName().hashCode());
	}

	@Override
	public boolean visit(CreationReference node) {
		Type type = node.getType();
		String nodecode = "new::" + TypeCode(type, true);
		GenerateOneLine(nodecode, false, false, false, false, GCodeMetaInfo.MethodReferenceHint);
		return false;
	}

	@Override
	public boolean visit(SuperMethodReference node) {
		// MyLogger.Info("SuperMethodReference:"+node);
		QualifiedPreHandle(node, node.getQualifier());
		runforbid.AddNodeHelp(node.getName().hashCode(), true);
		return super.visit(node);
	}

	@Override
	public void endVisit(SuperMethodReference node) {
		// qualified reference should be considered more carefully.
		QualifiedPostHandle(node, node.getQualifier(), node.getName(), "super", "::");
		runforbid.DeleteNodeHelp(node.getName().hashCode());
	}

	@Override
	public boolean visit(TypeMethodReference node) {
		// MyLogger.Info("TypeMethodReference:"+node);
		runforbid.AddNodeHelp(node.getName().hashCode(), true);
		Type type = node.getType();
		String nodecode = node.getName().toString() + "::" + TypeCode(type, true);
		GenerateOneLine(nodecode, false, false, false, false, GCodeMetaInfo.MethodReferenceHint);
		return false;
	}

	@Override
	public void endVisit(TypeMethodReference node) {
		runforbid.DeleteNodeHelp(node.getName().hashCode());
	}

	@Override
	public boolean visit(CastExpression node) {
		Integer hint = referhint.GetNodeHelp(node.hashCode());
		// System.err.println("CastExpression:" + node);
		CheckHint(hint);
		ExpressionReferPreHandle(node.getExpression(), hint);
		return super.visit(node);
	}

	@Override
	public void endVisit(CastExpression node) {
		ExpressionReferPostHandle(node, node.getExpression(), "", GCodeMetaInfo.CastExpressionHint,
				"(" + TypeCode(node.getType(), true) + ")", false, false, false, false, false);
	}

	@Override
	public boolean visit(Assignment node) {
		ExpressionReferPreHandle(node.getLeftHandSide(), ReferenceHintLibrary.DataUpdate);
		AddFirstOrderTask(new FirstOrderTask(node.getLeftHandSide(), node.getRightHandSide(), node, true) {
			@Override
			public void run() {
				ExpressionReferPostHandle(node, (Expression) getPre(), node.getOperator().toString(),
						GCodeMetaInfo.AssignmentHint, "", true, false, true, true, false);
				referhint.AddNodeHelp(getPost().hashCode(), ReferenceHintLibrary.DataUse);
				// ExpressionReferPreHandle(node.getRightHandSide(),
				// ReferenceHintLibrary.DataUse);
			}
		});
		return super.visit(node);
	}

	@Override
	public void endVisit(Assignment node) {
		// ExpressionReferPostHandle(node, node.getRightHandSide(), "", "", "",
		// true, false, false, false, false);
		referhint.DeleteNodeHelp(node.getRightHandSide().hashCode());
	}

	@Override
	public boolean visit(BreakStatement node) {
		LabelReferPreHandle(node.getLabel());
		return super.visit(node);
	}

	@Override
	public void endVisit(BreakStatement node) {
		LabelReferPostHandle(GCodeMetaInfo.BreakHint + "break", node.getLabel());
		GenerateEndInfo(GCodeMetaInfo.DescriptionHint + GCodeMetaInfo.EndOfAStatement);
		// AppendEndInfoToLast(GCodeMetaInfo.EndOfAStatement);
	}

	@Override
	public boolean visit(ContinueStatement node) {
		LabelReferPreHandle(node.getLabel());
		return super.visit(node);
	}

	@Override
	public void endVisit(ContinueStatement node) {
		LabelReferPostHandle(GCodeMetaInfo.ContinueHint + "continue", node.getLabel());
		GenerateEndInfo(GCodeMetaInfo.DescriptionHint + GCodeMetaInfo.EndOfAStatement);
		// AppendEndInfoToLast(GCodeMetaInfo.EndOfAStatement);
	}

	@Override
	public boolean visit(DoStatement node) {
		GenerateOneLine(GCodeMetaInfo.DescriptionHint + "do", false, false, false, true, null);
		Statement body = node.getBody();
		Expression expr = node.getExpression();
		AddFirstOrderTask(new FirstOrderTask(body, expr, node, false) {
			@Override
			public void run() {
				ExpressionReferPreHandle(expr, ReferenceHintLibrary.DataUse);
			}
		});
		return super.visit(node);
	}

	@Override
	public void endVisit(DoStatement node) {
		ExpressionReferPostHandle(node, node.getExpression(), "while", GCodeMetaInfo.DoWhileHint, "", false, true,
				false, false, false);
		GenerateEndInfo(GCodeMetaInfo.DescriptionHint + GCodeMetaInfo.EndOfAStatement);
		// AppendEndInfoToLast(GCodeMetaInfo.EndOfAStatement);
	}

	@Override
	public boolean visit(EnhancedForStatement node) {
		ExpressionReferPreHandle(node.getExpression(), ReferenceHintLibrary.DataUse);
		runforbid.AddNodeHelp(node.getParameter().hashCode(), true);
		AddFirstOrderTask(new FirstOrderTask(node.getExpression(), null, node, true) {
			@Override
			public void run() {
				String code = ExpressionReferPostHandle(node, node.getExpression(), "", "", "", false, false, false,
						false, true);
				if (code == null) {
					code = GCodeMetaInfo.PreExist;
				}
				String nodecode = GCodeMetaInfo.EnhancedFor + "for(" + TypeCode(node.getParameter().getType(), true) + ":" + code + ")";
				GenerateOneLine(nodecode, false, false, false, true, null);
			}
		});
		return super.visit(node);
	}

	@Override
	public void endVisit(EnhancedForStatement node) {
		runforbid.DeleteNodeHelp(node.getParameter().hashCode());
	}

	@Override
	public boolean visit(ArrayAccess node) {
		int nodehashcode = node.hashCode();
		Integer hint = referhint.GetNodeHelp(nodehashcode);
		Expression array = node.getArray();
		Expression index = node.getIndex();
		ExpressionReferPreHandle(array, hint);
		referhint.AddNodeHelp(index.hashCode(), ReferenceHintLibrary.DataUse);
		AddFirstOrderTask(new FirstOrderTask(array, index, node, true) {
			@Override
			public void run() {
				ExpressionReferPostHandle(node, array, "", GCodeMetaInfo.ArrayAccess, "#", true, false, true, true,
						false);
			}
		});
		return super.visit(node);
	}

	@Override
	public void endVisit(ArrayAccess node) {
		AppendEndInfoToLast(GCodeMetaInfo.EndOfArrayDeclarationIndexExpression);
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean visit(ArrayCreation node) {
		ArrayType type = node.getType();

		// MyLogger.Error("ArrayType:"+type);
		// MyLogger.Error("ArrayElementType:"+type.getElementType());

		String typecode = TypeCode(type.getElementType(), true);
		GenerateOneLine(GCodeMetaInfo.ArrayCreationHint + typecode + "(new)", false, false, false, true, null);
		List<Expression> list = node.dimensions();
		Iterator<Expression> itr = list.iterator();
		while (itr.hasNext()) {
			Expression expr = itr.next();
			referhint.AddNodeHelp(expr.hashCode(), ReferenceHintLibrary.DataUse);
			AddFirstOrderTask(new FirstOrderTask(expr, null, node, true) {
				@Override
				public void run() {
					AppendEndInfoToLast(GCodeMetaInfo.EndOfArrayDeclarationIndexExpression);
				}
			});
		}
		return super.visit(node);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void endVisit(ArrayCreation node) {
		List<Expression> list = node.dimensions();
		Iterator<Expression> itr = list.iterator();
		while (itr.hasNext()) {
			Expression expr = itr.next();
			referhint.DeleteNodeHelp(expr.hashCode());
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean visit(ArrayInitializer node) {
		GenerateOneLine(GCodeMetaInfo.DescriptionHint + "arrIni", false, false, false, true, null);
		List<Expression> list = node.expressions();
		Iterator<Expression> itr = list.iterator();
		while (itr.hasNext()) {
			Expression expr = itr.next();
			referhint.AddNodeHelp(expr.hashCode(), ReferenceHintLibrary.DataUse);
			AddFirstOrderTask(new FirstOrderTask(expr, null, node, true) {
				@Override
				public void run() {
					AppendEndInfoToLast(GCodeMetaInfo.EndOfArrayDeclarationIndexExpression);
				}
			});
		}
		return super.visit(node);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void endVisit(ArrayInitializer node) {
		List<Expression> list = node.expressions();
		Iterator<Expression> itr = list.iterator();
		while (itr.hasNext()) {
			Expression expr = itr.next();
			referhint.DeleteNodeHelp(expr.hashCode());
		}
	}

	@Override
	public boolean visit(ParenthesizedExpression node) {
		Integer hint = referhint.GetNodeHelp(node.hashCode());
		if (hint != null) {
			referhint.AddNodeHelp(node.getExpression().hashCode(), hint);
		}
		GenerateOneLine(GCodeMetaInfo.DescriptionHint + GCodeMetaInfo.LeftParenthese, false, false, false, true, null);
		return super.visit(node);
	}

	@Override
	public void endVisit(ParenthesizedExpression node) {
		// AppendEndInfoToLast(GCodeMetaInfo.RightParenthese);
		GenerateOneLine(GCodeMetaInfo.DescriptionHint + GCodeMetaInfo.RightParenthese, false, false, false, true, null);
		referhint.DeleteNodeHelp(node.getExpression().hashCode());
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean visit(InfixExpression node) {
		// MyLogger.Info("InfixExpression:" + node);
		int nodehashcode = node.hashCode();
		String operatorcode = node.getOperator().toString();
		Integer hint = referhint.GetNodeHelp(nodehashcode);
		Expression left = node.getLeftOperand();
		Expression right = node.getRightOperand();

		if (hint == null) {
			MyLogger.Error("No hint InfixExpression:" + node);
		}

		List<Expression> extendops = node.extendedOperands();

		ExpressionReferPreHandle(left, hint);
		// referhint.AddNodeHelp(left.hashCode(), hint);
		AddFirstOrderTask(new FirstOrderTask(left, right, node, false) {
			@Override
			public void run() {
				ExpressionReferPostHandle(node, left, operatorcode, GCodeMetaInfo.InfixExpressionHint, "", true, false,
						true, true, false);
				// String lastomc = omc.GetLastCode();
				// MyLogger.Info("Last
				// coreferhint.AddNodeHelp(node.getExpression().hashCode(),
				// ReferenceHintLibrary.DataUse);de of OMC Node Code:" +
				// lastomc);
				if (extendops == null || extendops.size() == 0) {
					referhint.AddNodeHelp(right.hashCode(), hint);
				} else {
					ExpressionReferPreHandle(right, hint);
				}
			}
		});
		if (extendops != null && extendops.size() != 0) {
			Expression pre = right;
			Expression last = extendops.get(extendops.size() - 1);
			Iterator<Expression> itr = extendops.iterator();
			while (itr.hasNext()) {
				Expression op = itr.next();
				AddFirstOrderTask(new FirstOrderTask(pre, op, node, false) {
					@Override
					public void run() {
						ExpressionReferPostHandle(node, (Expression) getPre(), operatorcode,
								GCodeMetaInfo.InfixExpressionHint, "", true, false, true, true, false);
						if (getPost() == last) {
							referhint.AddNodeHelp(getPost().hashCode(), hint);
						} else {
							ExpressionReferPreHandle((Expression) getPost(), hint);
						}
					}
				});
				pre = op;
			}
		}
		return super.visit(node);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void endVisit(InfixExpression node) {
		Expression right = node.getRightOperand();
		List<Expression> extendops = node.extendedOperands();
		if (extendops == null || extendops.size() == 0) {
			// MyLogger.Info("infix node:" + node);
			referhint.DeleteNodeHelp(right.hashCode());
			// ExpressionReferPostHandle(node, right, "", "", "", true, false,
			// false, false, false);
		} else {
			referhint.DeleteNodeHelp(extendops.get(extendops.size() - 1).hashCode());
			// ExpressionReferPostHandle(node, extendops.get(extendops.size() -
			// 1), "", "", "", true, false, false, false, false);
		}
	}

	@Override
	public boolean visit(InstanceofExpression node) {
		ExpressionReferPreHandle(node.getLeftOperand(), ReferenceHintLibrary.DataUse);
		return super.visit(node);
	}

	@Override
	public void endVisit(InstanceofExpression node) {
		ExpressionReferPostHandle(node, node.getLeftOperand(), "instanceof", GCodeMetaInfo.InstanceofExpressionHint,
				TypeCode(node.getRightOperand(), true), true, true, false, false, false);
	}

	@Override
	public boolean visit(LabeledStatement node) {
		String nodecode = GCodeMetaInfo.LabelDeclarationHint + node.getLabel().toString();
		referhint.AddNodeHelp(node.getLabel().hashCode(), ReferenceHintLibrary.LabelDeclare);
		GenerateOneLine(nodecode, false, false, false, true, null);
		return super.visit(node);
	}

	@Override
	public void endVisit(LabeledStatement node) {
		// AppendEndInfoToLast(GCodeMetaInfo.EndOfAStatement);
		referhint.DeleteNodeHelp(node.getLabel().hashCode());
	}

	@Override
	public boolean visit(PostfixExpression node) {
		ExpressionReferPreHandle(node.getOperand(), ReferenceHintLibrary.DataUpdate);
		return super.visit(node);
	}

	@Override
	public void endVisit(PostfixExpression node) {
		ExpressionReferPostHandle(node, node.getOperand(), node.getOperator().toString(),
				GCodeMetaInfo.PostfixExpressionHint, "", true, false, false, false, false);
	}

	@Override
	public boolean visit(PrefixExpression node) {
		ExpressionReferPreHandle(node.getOperand(), ReferenceHintLibrary.DataUpdate);
		return super.visit(node);
	}

	@Override
	public void endVisit(PrefixExpression node) {
		ExpressionReferPostHandle(node, node.getOperand(), node.getOperator().toString(),
				GCodeMetaInfo.PrefixExpressionHint, "", false, false, false, false, false);
	}

	@Override
	public boolean visit(ReturnStatement node) {
		Expression expr = node.getExpression();
		if (expr != null) {
			ExpressionReferPreHandle(expr, ReferenceHintLibrary.DataUse);
		}
		return super.visit(node);
	}

	@Override
	public void endVisit(ReturnStatement node) {
		Expression expr = node.getExpression();
		if (expr != null) {
			ExpressionReferPostHandle(node, expr, "return", GCodeMetaInfo.ReturnHint, "", false, true, false, false,
					false);
			GenerateEndInfo(GCodeMetaInfo.DescriptionHint + GCodeMetaInfo.EndOfAStatement);
			// AppendEndInfoToLast(GCodeMetaInfo.EndOfAStatement);
		} else {
			GenerateOneLine(GCodeMetaInfo.ReturnHint + "return", false, false, false, true, null);
			GenerateEndInfo(GCodeMetaInfo.DescriptionHint + GCodeMetaInfo.EndOfAStatement);
			// AppendEndInfoToLast(GCodeMetaInfo.EndOfAStatement);
		}
	}

	@Override
	public boolean visit(SwitchStatement node) {
		ExpressionReferPreHandle(node.getExpression(), ReferenceHintLibrary.DataUse);
		AddFirstOrderTask(new FirstOrderTask(node.getExpression(), null, node, true) {
			@Override
			public void run() {
				ExpressionReferPostHandle(node, node.getExpression(), "switch", GCodeMetaInfo.SwitchHint, "", false,
						true, false, false, false);
			}
		});
		return super.visit(node);
	}

	@Override
	public void endVisit(SwitchStatement node) {
	}

	@Override
	public boolean visit(SwitchCase node) {
		Expression expr = node.getExpression();
		if (expr != null) {
			ExpressionReferPreHandle(expr, ReferenceHintLibrary.DataUse);
		}
		return super.visit(node);
	}

	@Override
	public void endVisit(SwitchCase node) {
		Expression expr = node.getExpression();
		if (expr != null) {
			ExpressionReferPostHandle(node, expr, "case", GCodeMetaInfo.CaseHint, "", false, true, false, false, false);
		} else {
			GenerateOneLine(GCodeMetaInfo.DefaultHint + "default", false, false, false, true, null);
		}
	}

	@Override
	public boolean visit(SynchronizedStatement node) {
		ExpressionReferPreHandle(node.getExpression(), ReferenceHintLibrary.DataUse);
		AddFirstOrderTask(new FirstOrderTask(node.getExpression(), node.getBody(), node, false) {
			@Override
			public void run() {
				ExpressionReferPostHandle(node, node.getExpression(), "synchronized", GCodeMetaInfo.SynchronizedHint,
						"", false, true, false, false, false);
			}
		});
		return super.visit(node);
	}

	@Override
	public boolean visit(ThrowStatement node) {
		ExpressionReferPreHandle(node.getExpression(), ReferenceHintLibrary.DataUse);
		return super.visit(node);
	}

	@Override
	public void endVisit(ThrowStatement node) {
		ExpressionReferPostHandle(node, node.getExpression(), "throw", GCodeMetaInfo.ThrowStatementHint, "", false,
				true, false, false, false);
		// AppendEndInfoToLast(GCodeMetaInfo.EndOfAStatement);
		GenerateEndInfo(GCodeMetaInfo.DescriptionHint + GCodeMetaInfo.EndOfAStatement);
	}

	@Override
	public boolean visit(CatchClause node) {
		runforbid.AddNodeHelp(node.getException().hashCode(), true);
		String nodecode = GCodeMetaInfo.CatchHint + "catch" + GCodeMetaInfo.WhiteSpaceReplacer
				+ TypeCode(node.getException().getType(), true);
		GenerateOneLine(nodecode, false, false, false, true, null);
		return super.visit(node);
	}

	// below are judge and loop related.

	@Override
	public boolean visit(WhileStatement node) {
		GenerateOneLine(GCodeMetaInfo.DescriptionHint + "while", false, false, false, true, null);
		referhint.AddNodeHelp(node.getExpression().hashCode(), ReferenceHintLibrary.DataUse);
		AddFirstOrderTask(new FirstOrderTask(node.getExpression(), null, node, true) {
			@Override
			public void run() {
				GenerateOneLine(GCodeMetaInfo.DescriptionHint + "ecwhile", false, false, false, true, null);
			}
		});
		return super.visit(node);
	}

	@Override
	public void endVisit(WhileStatement node) {
		referhint.DeleteNodeHelp(node.getExpression().hashCode());
	}

	@Override
	public boolean visit(IfStatement node) {
		GenerateOneLine(GCodeMetaInfo.IfStatementHint + "if", false, false, false, true, null);
		// ExpressionReferPreHandle(node.getExpression(),
		// ReferenceHintLibrary.DataUse);
		int exprhashcode = node.getExpression().hashCode();
		referhint.AddNodeHelp(exprhashcode, ReferenceHintLibrary.DataUse);
		AddFirstOrderTask(new FirstOrderTask(node.getExpression(), node.getThenStatement(), node, true) {
			@Override
			public void run() {
				GenerateOneLine(GCodeMetaInfo.DescriptionHint + "then", false, false, false, true, null);
				// ExpressionReferPostHandle(node, (Expression)getPre(), "", "",
				// "", false, true, false, false, false);
			}
		});
		AddFirstOrderTask(new FirstOrderTask(node.getThenStatement(), node.getElseStatement(), node, true) {
			@Override
			public void run() {
				if (getPost() != null) {
					GenerateOneLine(GCodeMetaInfo.DescriptionHint + "else", false, false, false, true, null);
				}
			}
		});
		return super.visit(node);
	}

	@Override
	public void endVisit(IfStatement node) {
		referhint.DeleteNodeHelp(node.getExpression().hashCode());
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean visit(ForStatement node) {
		GenerateOneLine(GCodeMetaInfo.DescriptionHint + "for", false, false, false, true, null);
		List<ASTNode> inis = node.initializers();
		boolean oneempty = false;
		boolean twoempty = false;
		boolean threeempty = false;
		if (inis == null || inis.size() == 0) {
			oneempty = true;
			GenerateOneLine(GCodeMetaInfo.DescriptionHint + "forIniOver", false, false, false, true, null);
		}
		Expression expr = node.getExpression();
		if (expr != null) {
			referhint.AddNodeHelp(expr.hashCode(), ReferenceHintLibrary.DataUse);
		}
		if (expr == null) {
			twoempty = true;
			GenerateOneLine(GCodeMetaInfo.DescriptionHint + "forExpOver", false, false, false, true, null);
		}
		List<ASTNode> ups = node.updaters();
		if (ups == null || ups.size() == 0) {
			threeempty = true;
			GenerateOneLine(GCodeMetaInfo.DescriptionHint + "forUpdOver", false, false, false, true, null);
		}
		if (!oneempty) {
			AddFirstOrderTask(new FirstOrderTask(inis.get(inis.size() - 1), null, node, true) {
				@Override
				public void run() {
					GenerateOneLine(GCodeMetaInfo.DescriptionHint + "forIniOver", false, false, false, true, null);
				}
			});
		}
		if (!twoempty) {
			AddFirstOrderTask(new FirstOrderTask(expr, null, node, true) {
				@Override
				public void run() {
					GenerateOneLine(GCodeMetaInfo.DescriptionHint + "forExpOver", false, false, false, true, null);
				}
			});
		}
		if (!threeempty) {
			AddFirstOrderTask(new FirstOrderTask(ups.get(ups.size() - 1), null, node, true) {
				@Override
				public void run() {
					GenerateOneLine(GCodeMetaInfo.DescriptionHint + "forUpdOver", false, false, false, true, null);
				}
			});
		}
		return super.visit(node);
	}

	@Override
	public void endVisit(ForStatement node) {
		Expression expr = node.getExpression();
		if (expr != null) {
			referhint.DeleteNodeHelp(expr.hashCode());
		}
	}

	// below are VariableDeclarations

	@Override
	public boolean visit(VariableDeclarationExpression node) {
		String typecode = TypeCode(node.getType(), true);
		SetVeryRecentDeclaredType(node.getType().toString());
		String nodecode = GenerateVariableDeclarationTypeCode(typecode, null);
		GenerateOneLine(nodecode, false, false, false, true, null);
		return super.visit(node);
	}

	@Override
	public void endVisit(VariableDeclarationExpression node) {
		SetVeryRecentDeclaredType(null);
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean visit(SingleVariableDeclaration node) {
		// MyLogger.Info("SingleVariableDeclaration:" + node);
		int namehashcode = node.getName().hashCode();
		Boolean forbid = runforbid.GetNodeHelp(node.hashCode());
		SetVeryRecentDeclaredType(node.getType().toString());
		if (forbid != null && forbid == true) {
			runpermit.AddNodeHelp(namehashcode, true);
			referhint.AddNodeHelp(namehashcode, ReferenceHintLibrary.DataDeclare);
			visit(node.getName());
			return false;
		}
		String typecode = TypeCode(node.getType(), true);
		String nodecode = GenerateVariableDeclarationTypeCode(typecode, node.extraDimensions());
		if (!VeryRecentNotGenerateCode) {
			GenerateOneLine(nodecode, false, false, false, true, null);
		}
		VariableDeclarationFragmentPreHandle(node.getInitializer(), node.getName());
		return super.visit(node);
	}

	@Override
	public void endVisit(SingleVariableDeclaration node) {
		int nodehashcode = node.hashCode();
		int namehashcode = node.getName().hashCode();
		Boolean forbid = runforbid.GetNodeHelp(nodehashcode);
		if (forbid != null && forbid == true) {
			endVisit(node.getName());
			runpermit.DeleteNodeHelp(namehashcode);
			referhint.DeleteNodeHelp(namehashcode);
			return;
		}
		VariableDeclarationFragmentPostHandle(node.getInitializer(), node.getName());
		SetVeryRecentDeclaredType(null);
	}

	@Override
	public boolean visit(FieldDeclaration node) {
		return false;
	}

	@Override
	public void endVisit(FieldDeclaration node) {
	}

	@Override
	public boolean visit(VariableDeclarationStatement node) {
		// MyLogger.Error("VariableDeclarationStatement:"+node);
		// MyLogger.Error("VariableDeclarationStatementType:"+node.getType());
		String typecode = TypeCode(node.getType(), true);
		SetVeryRecentDeclaredType(node.getType().toString());
		String nodecode = GenerateVariableDeclarationTypeCode(typecode, null);
		GenerateOneLine(nodecode, false, false, false, true, null);
		return super.visit(node);
	}

	@Override
	public void endVisit(VariableDeclarationStatement node) {
		SetVeryRecentDeclaredType(null);
		// AppendEndInfoToLast(GCodeMetaInfo.EndOfAStatement);
		GenerateEndInfo(GCodeMetaInfo.DescriptionHint + GCodeMetaInfo.EndOfAStatement);
	}

	@Override
	public boolean visit(VariableDeclarationFragment node) {
		VariableDeclarationFragmentPreHandle(node.getInitializer(), node.getName());
		return super.visit(node);
	}

	@Override
	public void endVisit(VariableDeclarationFragment node) {
		VariableDeclarationFragmentPostHandle(node.getInitializer(), node.getName());
	}

	@Override
	public boolean visit(ConditionalExpression node) {
		Integer hint = referhint.GetNodeHelp(node.hashCode());
		referhint.AddNodeHelp(node.getExpression().hashCode(), ReferenceHintLibrary.DataUse);
		if (hint != null) {
			referhint.AddNodeHelp(node.getThenExpression().hashCode(), hint);
			referhint.AddNodeHelp(node.getElseExpression().hashCode(), hint);
		} else {
			referhint.AddNodeHelp(node.getThenExpression().hashCode(), ReferenceHintLibrary.DataUse);
			referhint.AddNodeHelp(node.getElseExpression().hashCode(), ReferenceHintLibrary.DataUse);
		}
		GenerateOneLine(GCodeMetaInfo.DescriptionHint + "CondExpBegin", false, false, false, true, null);
		AddFirstOrderTask(new FirstOrderTask(node.getExpression(), node.getThenExpression(), node, false) {
			@Override
			public void run() {
				GenerateOneLine(GCodeMetaInfo.DescriptionHint + "CondExpQM", false, false, false, true, null);
			}
		});
		AddFirstOrderTask(new FirstOrderTask(node.getThenExpression(), node.getElseExpression(), node, false) {
			@Override
			public void run() {
				GenerateOneLine(GCodeMetaInfo.DescriptionHint + "CondExpCM", false, false, false, true, null);
			}
		});
		return super.visit(node);
	}

	@Override
	public void endVisit(ConditionalExpression node) {
		GenerateOneLine(GCodeMetaInfo.DescriptionHint + "CondExpEnd", false, false, false, true, null);
		referhint.DeleteNodeHelp(node.getExpression().hashCode());
		referhint.DeleteNodeHelp(node.getThenExpression().hashCode());
		referhint.DeleteNodeHelp(node.getElseExpression().hashCode());
	}

	// field access is highly related to the below.

	@Override
	public boolean visit(FieldAccess node) {
		// MyLogger.Info("FieldAccess:"+node);
		// MyLogger.Info("FieldAccessName:"+node.getName());
		// MyLogger.Info("FieldAccessExpr:"+node.getExpression());
		int nodehashcode = node.hashCode();
		Integer hint = referhint.GetNodeHelp(nodehashcode);
		ASTNode expr = node.getExpression();
		runforbid.AddNodeHelp(node.getName().hashCode(), true);
		int exprhashcode = expr.hashCode();
		/*
		 * if (expr instanceof FieldAccess || expr instanceof SuperFieldAccess)
		 * { AddNodeRefered(exprhashcode); referhint.AddNodeHelp(exprhashcode,
		 * hint); }
		 */
		if (expr instanceof ThisExpression) {
			int namehashcode = node.getName().hashCode();
			runpermit.AddNodeHelp(namehashcode, true);
			Integer changedhint = ReferenceHintLibrary.ChangeHintHighByteToField(hint);
			referhint.AddNodeHelp(namehashcode, changedhint);
			AddNodeRefered(namehashcode);
		} else {
			AddNodeRefered(exprhashcode);
			referhint.AddNodeHelp(exprhashcode, hint);
		}
		return super.visit(node);
	}

	// field access should not be one line.
	// Must Ensure.
	@Override
	public void endVisit(FieldAccess node) {
		String nodecode = "";
		ASTNode expr = node.getExpression();
		if (expr instanceof ThisExpression) {
			int namehashcode = node.getName().hashCode();
			nodecode = referedcnt.GetNodeHelp(namehashcode);
		} else {
			String exprcode = referedcnt.GetNodeHelp(expr.hashCode());
			if (exprcode == null || exprcode.equals("")) {
				exprcode = GCodeMetaInfo.PreExist;
			}
			nodecode = node.getName().toString() + "." + exprcode;
		}
		int nodehashcode = node.hashCode();
		if (NodeIsRefered(nodehashcode)) {
			referedcnt.AddNodeHelp(nodehashcode, nodecode);
			refernoline.AddNodeHelp(nodehashcode, true);
		} else {
			GenerateOneLine(nodecode, true, false, false, false, GCodeMetaInfo.FieldAccessHint);
		}

		// delete hint.
		runforbid.DeleteNodeHelp(node.getName().hashCode());
		int exprhashcode = expr.hashCode();
		/*
		 * if (expr instanceof FieldAccess) { DeleteNodeRefered(exprhashcode);
		 * referhint.DeleteNodeHelp(exprhashcode); }
		 */
		if (expr instanceof ThisExpression) {
			int namehashcode = node.getName().hashCode();
			runpermit.DeleteNodeHelp(namehashcode);
			referhint.DeleteNodeHelp(namehashcode);
			DeleteNodeRefered(namehashcode);
		} else {
			DeleteNodeRefered(exprhashcode);
			referhint.DeleteNodeHelp(exprhashcode);
		}
	}

	// below are most important method related : MethodDeclaration.

	@Override
	@SuppressWarnings("unchecked")
	public boolean visit(MethodDeclaration node) {
		// MyLogger.Info("MethodDeclarationParent:"+node.getParent().hashCode());
		if (isFirstLevelASTNode(node) || ParentIsTypeDeclaration(node)) {
			if (omc != null) {
				FlushCode();
				ClearClassAndLabelInfo();
				ResetDLM();
			}
			omc = new NodeCode(argmutiple);
		}
		String nodecode = GCodeMetaInfo.MethodDeclarationHint + node.getName().toString();
		runforbid.AddNodeHelp(node.getName().hashCode(), true);
		nodecode = nodecode + "(";
		List<SingleVariableDeclaration> types = node.parameters();
		Iterator<SingleVariableDeclaration> itr = types.iterator();
		while (itr.hasNext()) {
			SingleVariableDeclaration t = itr.next();
			runforbid.AddNodeHelp(t.hashCode(), true);
			String typecode = TypeCode(t.getType(), false);
			nodecode = nodecode + typecode + ",";
		}
		if (types.size() > 0) {
			nodecode = nodecode.substring(0, nodecode.length() - 1);
		}
		nodecode = nodecode + ")";
		GenerateOneLine(nodecode, false, false, false, true, null);
		return super.visit(node);
	}

	private boolean ParentIsTypeDeclaration(BodyDeclaration node) {
		if (node.getParent() instanceof TypeDeclaration) {
			return true;
		}
		return false;
	}

	@Override
	@SuppressWarnings("unchecked")
	public void endVisit(MethodDeclaration node) {
		List<SingleVariableDeclaration> types = node.parameters();
		Iterator<SingleVariableDeclaration> itr = types.iterator();
		while (itr.hasNext()) {
			SingleVariableDeclaration t = itr.next();
			runforbid.DeleteNodeHelp(t.hashCode());
		}
		if (isFirstLevelASTNode(node)) {
			FlushCode();
		} else {
			OneSentenceEnd();
		}
		runforbid.DeleteNodeHelp(node.getName().hashCode());
	}

	// below are all method invocations.

	@Override
	@SuppressWarnings("unchecked")
	public boolean visit(ClassInstanceCreation node) {
		// MyLogger.Info("Node Type:"+node.getType());
		// MyLogger.Info("Body:"+node.getAnonymousClassDeclaration());
		OneMethodInvocationOccurs(TypeCode(node.getType(), true));
		MethodPushReferRequest(node.getExpression(), node.arguments());
		return super.visit(node);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void endVisit(ClassInstanceCreation node) {
		// MyLogger.Info("Node Type:"+node.getType());
		// MyLogger.Info("Body:"+node.getAnonymousClassDeclaration());
		Expression expr = node.getExpression();
		String invoker = "new";
		if (expr != null) {
			int exprhashcode = expr.hashCode();
			String refercnt = referedcnt.GetNodeHelp(exprhashcode);
			if (refercnt != null) {
				invoker += ("." + refercnt);
			}
		}
		MethodInvocationCode(TypeCode(node.getType(), false), invoker, node.arguments());
		MethodDeleteReferRequest(expr, node.arguments());
		if (node.getAnonymousClassDeclaration() != null) {
			GenerateOneLine(GCodeMetaInfo.DescriptionHint + "AnonymousDeclaration", false, false, false, true, null);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean visit(ConstructorInvocation node) {
		MethodPushReferRequest(null, node.arguments());
		return super.visit(node);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void endVisit(ConstructorInvocation node) {
		// Do nothing now.
		// MyLogger.Info("ConstructorInvocation:" + node);
		MethodInvocationCode("this", "this", node.arguments());
		MethodDeleteReferRequest(null, node.arguments());
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean visit(SuperConstructorInvocation node) {
		MethodPushReferRequest(node.getExpression(), node.arguments());
		return super.visit(node);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void endVisit(SuperConstructorInvocation node) {
		Expression expr = node.getExpression();
		String invoker = "this";
		if (expr != null) {
			int exprhashcode = expr.hashCode();
			String refercnt = referedcnt.GetNodeHelp(exprhashcode);
			if (refercnt != null) {
				invoker = refercnt;
			} else {
				invoker = GCodeMetaInfo.PreExist;
			}
		}
		MethodInvocationCode("super", invoker, node.arguments());
		MethodDeleteReferRequest(expr, node.arguments());
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean visit(SuperMethodInvocation node) {
		OneMethodInvocationOccurs(node.getName().toString());
		MethodPushReferRequest(null, node.arguments());
		runforbid.AddNodeHelp(node.getName().hashCode(), true);
		if (node.getQualifier() != null) {
			runforbid.AddNodeHelp(node.getQualifier().hashCode(), true);
		}
		return super.visit(node);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void endVisit(SuperMethodInvocation node) {
		String invoker = "super";
		if (node.getQualifier() != null) {
			String qualifiercnt = GetDefaultStrictedLengthOfName(node.getQualifier());
			if (qualifiercnt != null) {
				invoker += "." + qualifiercnt;
			}
		}
		MethodInvocationCode(node.getName().toString(), invoker, node.arguments());
		MethodDeleteReferRequest(null, node.arguments());

		runforbid.DeleteNodeHelp(node.getName().hashCode());
		if (node.getQualifier() != null) {
			runforbid.DeleteNodeHelp(node.getQualifier().hashCode());
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean visit(MethodInvocation node) {
		OneMethodInvocationOccurs(node.getName().toString());
		MethodPushReferRequest(node.getExpression(), node.arguments());
		runforbid.AddNodeHelp(node.getName().hashCode(), true);
		return super.visit(node);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void endVisit(MethodInvocation node) {
		// MyLogger.Info("MethodInvocation:" + node);
		Expression expr = node.getExpression();
		String invoker = "this";
		if (expr != null) {
			int exprhashcode = expr.hashCode();
			String refercnt = referedcnt.GetNodeHelp(exprhashcode);
			if (refercnt != null) {
				invoker = refercnt;
			} else {
				invoker = GCodeMetaInfo.PreExist;
			}
		}
		MethodInvocationCode(node.getName().toString(), invoker, node.arguments());
		MethodDeleteReferRequest(expr, node.arguments());

		runforbid.DeleteNodeHelp(node.getName().hashCode());
	}

	// raw names : handle types : SuperFieldAccess or Name

	@Override
	public boolean visit(SuperFieldAccess node) {
		// MyLogger.Info("SuperFieldAccess:" + node);
		// MyLogger.Info("SuperFieldAccess Qualifier:" +
		// MyLogger.Info("SuperFieldAccess Name:" + node.getName());
		runforbid.AddNodeHelp(node.getName().hashCode(), true);
		Name qualifier = node.getQualifier();
		return QualifiedPreHandle(node, qualifier) && super.visit(node);
	}

	// this should never be a line.
	@Override
	public void endVisit(SuperFieldAccess node) {
		Name qualifier = node.getQualifier();
		Name name = node.getName();
		QualifiedPostHandle(node, qualifier, name, "super", ".");

		runforbid.DeleteNodeHelp(node.getName().hashCode());
	}

	@Override
	public boolean visit(QualifiedName node) {
		int nodehashcode = node.hashCode();
		Boolean forbid = runforbid.GetNodeHelp(nodehashcode);
		if (forbid != null && forbid == true) {
			return false;
		}
		int len = PredictLength(node);
		boolean ctn = true;
		if (len > StrictedNameLength) {
			ctn = false;
			String nodecode = GetStrictedLengthOfName(node, StrictedNameLength);
			if (NodeIsRefered(nodehashcode)) {
				referedcnt.AddNodeHelp(nodehashcode, nodecode);
				refernoline.AddNodeHelp(nodehashcode, true);
			} else {
				GenerateOneLine(nodecode, true, false, false, false, GCodeMetaInfo.QualifiedHint);
			}
		} else {
			runforbid.AddNodeHelp(node.getName().hashCode(), true);
			Name qualifier = node.getQualifier();
			ctn = QualifiedPreHandle(node, qualifier);
		}
		return ctn && super.visit(node);
	}

	@Override
	public void endVisit(QualifiedName node) {
		int nodehashcode = node.hashCode();
		Boolean forbid = runforbid.GetNodeHelp(nodehashcode);
		if (forbid != null && forbid == true) {
			return;
		}
		int len = PredictLength(node);
		if (len <= StrictedNameLength) {
			Name qualifier = node.getQualifier();
			Name name = node.getName();
			QualifiedPostHandle(node, qualifier, name, null, null);

			runforbid.DeleteNodeHelp(node.getName().hashCode());
		}
	}

	protected int PredictLength(Name node) {
		if (node instanceof SimpleName) {
			return 1;
		} else {
			return PredictLength(((QualifiedName) node).getQualifier()) + 1;
		}
	}

	protected String GetDefaultStrictedLengthOfName(Name node) {
		int len = PredictLength(node);
		if (len == 1) {
			return node.toString();
		}
		return GetStrictedLengthOfName((QualifiedName) node, Math.min(StrictedNameLength, len));
	}

	protected String GetStrictedLengthOfName(QualifiedName node, int len) {
		String cnt = "";
		SimpleName name = null;
		while (len > 0) {
			len--;
			String cat = ".";
			if (len == 0) {
				cat = "";
			}
			if (len >= 1) {
				cnt += node.getName().toString() + cat;
			} else {
				cnt += name.toString() + cat;
			}
			if (len > 1) {
				node = (QualifiedName) node.getQualifier();
			}
			if (len == 1) {
				Name qualifier = node.getQualifier();
				if (qualifier instanceof QualifiedName) {
					name = ((QualifiedName) qualifier).getName();
				} else {
					name = (SimpleName) qualifier;
				}
			}
		}
		return cnt;
	}

	protected boolean QualifiedPreHandle(ASTNode node, Name qualifier) {
		int nodehashcode = node.hashCode();
		Boolean forbid = runforbid.GetNodeHelp(nodehashcode);
		if (forbid != null && forbid == true) {
			return false;
		}
		if (qualifier != null) {
			runforbid.AddNodeHelp(qualifier.hashCode(), true);
		}
		return true;
	}

	protected void QualifiedPostHandle(ASTNode node, Name qualifier, Name name, String additional,
			String additionalprefixoperator) {

		// MyLogger.Info("node:" + node);
		// MyLogger.Info("qualifier:" + qualifier);
		// MyLogger.Info("node is refered:" + NodeIsRefered(node.hashCode()));

		int nodehashcode = node.hashCode();
		Boolean forbid = runforbid.GetNodeHelp(nodehashcode);
		if (forbid != null && forbid == true) {
			return;
		}
		String nodecode = name.toString() + (additional != null ? additionalprefixoperator + additional : "")
				+ (qualifier != null ? "." + GetDefaultStrictedLengthOfName(qualifier) : "");
		if (NodeIsRefered(nodehashcode)) {
			referedcnt.AddNodeHelp(nodehashcode, nodecode);
			refernoline.AddNodeHelp(nodehashcode, true);
		} else {
			GenerateOneLine(nodecode, true, false, false, false, GCodeMetaInfo.QualifiedHint);
		}
		if (qualifier != null) {
			runforbid.DeleteNodeHelp(qualifier.hashCode());
		}
	}

	@Override
	public boolean visit(SimpleName node) {
		int nodehashcode = node.hashCode();
		Boolean canrun = runpermit.GetNodeHelp(nodehashcode);

		if (canrun == null || canrun == false) {
			Boolean forbid = runforbid.GetNodeHelp(nodehashcode);
			if (forbid != null && forbid == true) {
				return false;
			} else {
				/*
				 * if (!NodeIsRefered(nodehashcode)) { return false; }
				 */
			}
		}

		Integer hint = referhint.GetNodeHelp(node.hashCode());

		// MyLogger.Info("hint:"+hint);
		// MyLogger.Info("SimpleNameParent:"+node.getParent());
		// MyLogger.Info("SimpleNameParentType:"+node.getParent().getClass());

		if (hint == null) {
			MyLogger.Error("SimpleName:" + node);
		}
		// MyLogger.Info("name:" + node.toString() +";hint:" + (hint ==
		// ReferenceHintLibrary.DataDeclare)+";hint2:"+(hint ==
		// ReferenceHintLibrary.DataUse));

		boolean isfield = false;
		String result = null;
		if (hint != ReferenceHintLibrary.NoHint) {
			String code = null;
			boolean hasCorrespond = false;
			String data = node.toString();
			switch (hint) {
			case ReferenceHintLibrary.DataUse:
				/*
				 * if (data.equals("a")) { MyLogger.Info("DataUse"); }
				 */
				// MyLogger.Info("datause:"+data);
				code = GetDataOffset(data, false, false);
				break;
			case ReferenceHintLibrary.FieldUse:
				/*
				 * if (data.equals("a")) { MyLogger.Info("FieldUse"); }
				 */
				isfield = true;
				code = GetDataOffset(data, true, false);
				break;
			case ReferenceHintLibrary.DataUpdate:
				/*
				 * if (data.equals("a")) { MyLogger.Info("DataUpdate"); }
				 */
				code = GetDataOffset(data, false, false);
				DataNewlyUsed(data, null, false, false, false, false, false);
				break;
			case ReferenceHintLibrary.FieldUpdate:
				/*
				 * if (data.equals("a")) { MyLogger.Info("FieldUpdate"); }
				 */
				isfield = true;
				code = GetDataOffset(data, true, false);
				DataNewlyUsed(data, null, false, false, false, true, false);
				break;
			case ReferenceHintLibrary.DataDeclare:
				/*
				 * if (data.equals("a")) { MyLogger.Info("DataDeclare"); }
				 */
				String declaredtype = GetVeryRecentDeclaredType();
				// MyLogger.Info("common declaredtype:" + declaredtype
				// + "; and data is:" + data + "; and is final:" +
				// GetVeryRecentDeclaredFinal());
				CheckVeryRecentDeclaredTypeMustNotNull(declaredtype);
				// MyLogger.Error("data is:" + data + "
				// declaredtype:"+declaredtype);
				DataNewlyUsed(data, declaredtype, false, false, true, false, false);
				hasCorrespond = true;
				break;
			case ReferenceHintLibrary.FieldDeclare:
				/*
				 * if (data.equals("a")) { MyLogger.Info("FieldDeclare"); }
				 */
				isfield = true;
				String declaredtype2 = GetVeryRecentDeclaredType();
				CheckVeryRecentDeclaredTypeMustNotNull(declaredtype2);
				DataNewlyUsed(data, declaredtype2, false, true, false, false, false);
				hasCorrespond = true;
				break;
			default:
				break;
			}
			if (code != null) {
				result = code;
			} else {
				if (!hasCorrespond) {
					String nodestr = node.toString();
					String pre = (isfield ? "this." : "");
					if (Character.isLowerCase(nodestr.charAt(0)) == true) {
						MyLogger.Error("Debugging Data: " + node
								+ "; No corresponding data offset. Maybe data use or others.");
					}
					result = pre + nodestr;
				}
			}
		} else {
			String nodestr = node.toString();
			if (Character.isLowerCase(nodestr.charAt(0)) == true) {
				MyLogger.Error("Warning Data: " + node
						+ "; just for debugging and testing. The simple name does not have hint.");
			}
			result = nodestr;
		}

		if (result == null) {
			if (hint == ReferenceHintLibrary.DataDeclare || hint == ReferenceHintLibrary.FieldDeclare) {
				// do nothing.
			} else {
				MyLogger.Error("What the fuck. How serious the error is!");
				new Exception().printStackTrace();
				System.exit(1);
			}
		} else {
			if (NodeIsRefered(nodehashcode)) {
				referedcnt.AddNodeHelp(nodehashcode, result);
				refernoline.AddNodeHelp(nodehashcode, true);
			} else {
				GenerateOneLine(result, false, false, false, false, GCodeMetaInfo.NameHint);
			}
		}
		return super.visit(node);
	}

	// the handle of the following types should use the helper function

	@SuppressWarnings("unchecked")
	protected String TypeCode(Type node, boolean simplified) {
		if (node == null)
		{
			System.err.println("Null Type? What the fuck!");
			new Exception("Null Type").printStackTrace();
			System.exit(1);
		}
		if (node instanceof PrimitiveType) {
			return node.toString();
		}
		String type = RawTypeCode(node);
		String typecode = GetClassOffset(type);
		ClassNewlyAssigned(type);
		if (node instanceof SimpleType || node instanceof QualifiedType || node instanceof NameQualifiedType
				|| node instanceof WildcardType) {
			if (typecode == null) {
				if (node instanceof SimpleType || node instanceof QualifiedType || node instanceof NameQualifiedType)
				{
					typecode = type;
				}
				if (node instanceof WildcardType)
				{
					WildcardType wildtype = (WildcardType)node;
					if (wildtype.getBound() == null)
					{
						typecode = "?";
					}
					else
					{
						typecode = "?" + GCodeMetaInfo.WhiteSpaceReplacer + (wildtype.isUpperBound()?"extends":"super") + GCodeMetaInfo.WhiteSpaceReplacer + TypeCode(wildtype.getBound(), true);
					}
				}
				if (simplified) {
					if (node instanceof NameQualifiedType) {
						typecode = ((NameQualifiedType) node).getName().toString();
					}
					if (node instanceof QualifiedType) {
						typecode = ((QualifiedType) node).getName().toString();
					}
					if (node instanceof SimpleType) {
						Name name = ((SimpleType) node).getName();
						if (name instanceof QualifiedName) {
							typecode = ((QualifiedName) name).getName().toString();
						}
					}
				}
			}
		}
		if (node instanceof ArrayType) {
			if (typecode == null) {
				ArrayType arraynode = (ArrayType) node;
				Type pretype = arraynode.getElementType();
				String simplifiedtype = "Object";
				if (pretype instanceof PrimitiveType) {
					simplifiedtype = pretype.toString();
				}
				if (simplified) {
					int dimens = ((ArrayType) node).dimensions().size();
					String dimenstr = "";
					for (int i = 0; i < dimens; i++) {
						dimenstr += "[]";
					}
					typecode = simplifiedtype + dimenstr;
				} else {
					typecode = type;
				}
			}
		}
		if (node instanceof ParameterizedType) {
			
			// System.err.println("ParameterizedType:" + node);
			
			if (typecode == null) {
				ParameterizedType pt = (ParameterizedType) node;
				//if (simplified) {
				//	typecode = TypeCode(pt.getType(), true);
				//} else {
					typecode = TypeCode(pt.getType(), simplified) + "<";
					List<Type> tas = pt.typeArguments();
					Iterator<Type> itr = tas.iterator();
					while (itr.hasNext())
					{
						Type tt = itr.next();
						
						// System.err.println("ParameterizedTypeSmallType :" + tt.getClass());
						
						typecode += TypeCode(tt, simplified);
						if (itr.hasNext())
						{
							typecode += ",";
						}
					}
					typecode += ">";
				//}
			}
		}
		if (node instanceof IntersectionType) {
			IntersectionType tnode = (IntersectionType) node;
			List<Type> types = tnode.types();
			Iterator<Type> itr = types.iterator();
			typecode = "";
			while (itr.hasNext()) {
				Type t = itr.next();
				String tstr = TypeCode(t, simplified);
				typecode = typecode + "&" + tstr;
			}
			if (types.size() > 0) {
				typecode = typecode.substring(1);
			}
		}
		if (node instanceof UnionType) {
			UnionType tnode = (UnionType) node;
			List<Type> types = tnode.types();
			Iterator<Type> itr = types.iterator();
			typecode = "";
			while (itr.hasNext()) {
				Type t = itr.next();
				String tstr = TypeCode(t, simplified);
				typecode = typecode + "|" + tstr;
			}
			if (types.size() > 0) {
				typecode = typecode.substring(1);
			}
		}
		return typecode;
	}

	@SuppressWarnings("unchecked")
	protected String RawTypeCode(Type node) {
		if (node instanceof PrimitiveType)
		{
			String code = ((PrimitiveType)node).toString().trim();
			int widx = code.lastIndexOf(' ');
			return code.substring(widx+1);
		}
		if (node instanceof SimpleType)
		{
			return GetStrictedName(((SimpleType)node).getName(), StrictedNameLength);
		}
		if (node instanceof QualifiedType)
		{
			QualifiedType qn = (QualifiedType)node;
			return qn.getName().toString() + RawTypeCode(qn.getQualifier());
		}
		if (node instanceof NameQualifiedType)
		{
			NameQualifiedType nt = (NameQualifiedType)node;
			return nt.getName().toString() + "." + GetStrictedName(((NameQualifiedType)node).getQualifier(), StrictedNameLength-1);
		}
		if (node instanceof WildcardType)
		{
			WildcardType wt = (WildcardType)node;
			if (wt.getBound() == null)
			{
				return "?";
			}
			return "?" + (wt.isUpperBound() ? " extends " : " super ") + RawTypeCode(wt.getBound());
		}
		if (node instanceof ArrayType)
		{
			ArrayType at = (ArrayType)node;
			int dimens = at.dimensions().size();
			String dimenstr = "";
			for (int i = 0; i < dimens; i++) {
				dimenstr += "[]";
			}
			return RawTypeCode(at.getElementType()) + dimenstr;
		}
		if (node instanceof ParameterizedType)
		{
			ParameterizedType pt = (ParameterizedType)node;
			String result = RawTypeCode(pt.getType()) + "<";
			List<Type> tas = pt.typeArguments();
			Iterator<Type> itr = tas.iterator();
			while (itr.hasNext())
			{
				Type tt = itr.next();
				result += RawTypeCode(tt);
				if (itr.hasNext())
				{
					result += ",";
				}
			}
			result += ">";
			return result;
		}
		if (node instanceof UnionType)
		{
			UnionType ut = (UnionType) node;
			List<Type> types = ut.types();
			Iterator<Type> itr = types.iterator();
			String result = "";
			boolean first = true;
			while (itr.hasNext()) {
				Type t = itr.next();
				String tstr = RawTypeCode(t);
				if (first)
				{
					result = tstr;
					first = false;
				}
				else
				{
					result = result + "|" + tstr;
				}
			}
			return result;
		}
		if (node instanceof IntersectionType)
		{
			IntersectionType ut = (IntersectionType) node;
			List<Type> types = ut.types();
			Iterator<Type> itr = types.iterator();
			String result = "";
			boolean first = true;
			while (itr.hasNext()) {
				Type t = itr.next();
				String tstr = RawTypeCode(t);
				if (first)
				{
					result = tstr;
					first = false;
				}
				else
				{
					result = result + "&" + tstr;
				}
			}
			return result;
		}
	    
		System.err.println("Uncognized Type node.");
		System.exit(1);
		return null;
	}
	
	protected String GetStrictedName(Name name, int alreadylen)
	{
		String result = null;
		if (name != null && alreadylen > 0)
		{
			if (name instanceof QualifiedName)
			{
				QualifiedName qn = (QualifiedName)name;
				result = qn.getName().toString();
				String qs = GetStrictedName(qn, alreadylen-1);
				if (qs != null)
				{
					result += "." + qs;
				}
			}
			if (name instanceof SimpleName)
			{
				result = name.toString();
			}
		}
		return result;
	}
	
	@Override
	public boolean visit(IntersectionType node) {
		// type & type
		// MyLogger.Info("IntersectionType:" + node);
		// do nothing.
		return super.visit(node);
	}

	@Override
	public boolean visit(UnionType node) {
		// type | type
		// MyLogger.Info("UnionType:" + node);
		// do nothing.
		return super.visit(node);
	}

	@Override
	public boolean visit(PrimitiveType node) {
		// MyLogger.Info("PrimitiveType:" + node);
		// do nothing. but this is different.
		return false;
	}

	@Override
	public boolean visit(WildcardType node) {
		// MyLogger.Info("WildcardType:" + node);
		// do nothing.
		return false;
	};

	@Override
	public boolean visit(SimpleType node) {
		// MyLogger.Info("SimpleType:" + node);
		// do nothing.
		return false;
	}

	@Override
	public boolean visit(QualifiedType node) {
		// MyLogger.Info("QualifiedType:"+node);
		// do nothing.
		return false;
	}

	@Override
	public boolean visit(ArrayType node) {
		// MyLogger.Info("ArrayType:"+node);
		// do nothing.
		return false;
	}

	@Override
	public boolean visit(ParameterizedType node) {
		// MyLogger.Info("ParameterizedType:"+node);
		// do nothing.
		return false;
	}

	@Override
	public boolean visit(NameQualifiedType node) {
		// MyLogger.Info("NameQualifiedType:"+node);
		// do nothing.
		return false;
	}

	// raw string
	@Override
	@SuppressWarnings("unchecked")
	public boolean visit(EnumDeclaration node) {
		// Do nothing now.
		// MyLogger.Info("EnumDeclaration:"+node);
		AppendOtherCode(GCodeMetaInfo.EnumCorpus, node.getName().toString());
		FlushCode();
		if (FirstLevelClass == null) {
			FirstLevelClass = node.hashCode();
		}
		EnterBlock(node);
		runforbid.AddNodeHelp(node.getName().hashCode(), true);
		GenerateOneLine(GCodeMetaInfo.EnumDeclarationHint + node.getName().toString(), false, false, false, true, null);
		SimplifiedFieldProcessASTVisitor sfpa = new SimplifiedFieldProcessASTVisitor(this);
		List<BodyDeclaration> bnlist = node.bodyDeclarations();
		Iterator<BodyDeclaration> itr = bnlist.iterator();
		while (itr.hasNext()) {
			BodyDeclaration bd = itr.next();
			if (bd instanceof FieldDeclaration) {
				bd.accept(sfpa);
			}
		}
		return super.visit(node);
	}

	@Override
	public void endVisit(EnumDeclaration node) {
		FlushCode();
		if (FirstLevelClass == node.hashCode()) {
			FirstLevelClass = null;
		}
		ExitBlock(node);
		runforbid.DeleteNodeHelp(node.getName().hashCode());
	}

	@Override
	@SuppressWarnings("unchecked")
	public boolean visit(EnumConstantDeclaration node) {
		AppendOtherCode(GCodeMetaInfo.EnumCorpus, node.getName().toString());
		MethodPushReferRequest(null, node.arguments());
		runforbid.AddNodeHelp(node.getName().hashCode(), true);
		return super.visit(node);
	}

	@Override
	@SuppressWarnings("unchecked")
	public void endVisit(EnumConstantDeclaration node) {
		String invoker = "this";
		EnumConstantInvocationCode(node.getName().toString(), invoker, node.arguments());
		MethodDeleteReferRequest(null, node.arguments());
		if (node.getAnonymousClassDeclaration() != null) {
			GenerateOneLine(GCodeMetaInfo.DescriptionHint + "AnonymousDeclaration", false, false, false, true, null);
		}
		runforbid.DeleteNodeHelp(node.getName().hashCode());
	}

	@Override
	public boolean visit(TypeLiteral node) {
		Type type = node.getType();
		String typecode = "void";
		if (type != null) {
			typecode = TypeCode(type, true);
		}
		String nodecode = "class." + typecode;
		RawLiteralHandle(node, nodecode);
		return super.visit(node);
	}

	@Override
	public boolean visit(NullLiteral node) {
		RawLiteralHandle(node, null);
		return false;
	}

	@Override
	public boolean visit(BooleanLiteral node) {
		RawLiteralHandle(node, null);
		return false;
	}

	@Override
	public boolean visit(StringLiteral node) {
		// MyLogger.Info("StringLiteral:"+node);
		String literal = node.toString().trim();
		AppendOtherCode(GCodeMetaInfo.StringCorpus, literal.substring(1, literal.length() - 1));
		RawLiteralHandle(node, GCodeMetaInfo.StringHolder);
		return false;
	}

	@Override
	public boolean visit(NumberLiteral node) {
		// MyLogger.Info("NumberLiteral:"+node);
		AppendOtherCode(GCodeMetaInfo.NumberCorpus, node.toString());

		RawLiteralHandle(node, null);
		return false;
	}

	@Override
	public boolean visit(CharacterLiteral node) {
		// MyLogger.Info("CharacterLiteral:"+node);
		AppendOtherCode(GCodeMetaInfo.StringCorpus, node.charValue() + "");
		AppendOtherCode(GCodeMetaInfo.CharCorpus, node.charValue() + "");

		RawLiteralHandle(node, null);
		return false;
	}

	// helper functions

	protected void RawLiteralHandle(ASTNode node, String nodecode) {
		if (nodecode == null) {
			nodecode = node.toString();
		}
		// special for char.
		if (nodecode.trim().equals("' '") || nodecode.trim().equals("'	'")) {
			// System.out.println("Exist ' ', stop to watch closely.");
			// System.exit(1);
			nodecode = GCodeMetaInfo.WhiteSpace;
		}
		int nodehashcode = node.hashCode();
		if (NodeIsRefered(nodehashcode)) {
			referedcnt.AddNodeHelp(nodehashcode, nodecode);
			refernoline.AddNodeHelp(nodehashcode, true);
		} else {
			GenerateOneLine(nodecode, false, false, false, false, GCodeMetaInfo.LiteralHint);
		}
	}

	protected void AnonymousClassDeclarationCodeFileAddMethodWindow() {
		((OneJavaFileAnonymousClassesCode)jc).AddPreDeclrations(mw);
	}

	protected void OneMethodInvocationOccurs(String rawmethodname) {
		mw.PushMethodName(rawmethodname);
	}

	protected boolean isFirstLevelASTNode(ASTNode node) {
		int parenthashcode = node.getParent().hashCode();
		if (parenthashcode == FirstLevelClass) {
			return true;
		}
		return false;
	}

	protected void PushMethodNodeCodeToJavaFileCode() {
		jc.AddOneMethodNodeCode(omc);
	}

	protected void OneSentenceEnd() {
		jc.OneSentenceEnd();
	}

	protected void FlushCode() {
		if (omc != null && !omc.IsEmpty()) {
			PushMethodNodeCodeToJavaFileCode();
			OneSentenceEnd();
		}
		omc = null;
		if (omc == null) {
			omc = (new NodeCode(argmutiple));
		}
	}

	protected void AppendOtherCode(String corpus, String code) {
		ocm.AppendOtherCode(corpus, code);
	}

	protected void AddFirstOrderTask(FirstOrderTask runtask) {
		fotp.InfixNodeAddFirstOrderTask(runtask);
	}

	protected void GenerateOneLine(String nodecode, boolean couldappend, boolean mustappend, boolean mustpre,
			boolean occupyoneline, String preHint) {
		omc.AddOneLineCode(nodecode, couldappend, mustappend, mustpre, occupyoneline, preHint);
	}
	
	protected void GenerateEndInfo(String lcode) {
		omc.GenerateEndInfo(lcode);
	}

	protected void AppendEndInfoToLast(String ncode) {
		omc.AppendEndInfoToLast(ncode);
	}

	// If doesn't know the kind, just set one as random. The one must be the big
	// kind you want.
	protected String GetDataOffset(String data, boolean isFieldUseOrUpdate, boolean isCommonUseOrUpdate) {
		String code = sdm.GetDataOffsetInfo(data, isFieldUseOrUpdate, isCommonUseOrUpdate);
		return code;
	}

	protected void ClassNewlyAssigned(String type) {
		cjcs.PushNewlyAssignedData(type, GCodeMetaInfo.HackedNoType);
	}

	protected String GetClassOffset(String type) {
		Integer offset = cjcs.GetExactOffset(type, GCodeMetaInfo.HackedNoType);
		if (offset == null) {
			return null;
		}
		return "$K" + 0 + GCodeMetaInfo.OffsetSpiliter + OffsetLibrary.GetOffsetDescription(offset);
	}

	protected void LabelNewlyAssigned(String label) {
		ljcs.PushNewlyAssignedData(label, GCodeMetaInfo.HackedNoType);
	}

	protected String GetLabelOffset(String label) {
		Integer offset = ljcs.GetExactOffset(label, GCodeMetaInfo.HackedNoType);
		if (offset == null) {
			return null;
		}
		return "$L" + 0 + GCodeMetaInfo.OffsetSpiliter + OffsetLibrary.GetOffsetDescription(offset);
	}

	protected void DataNewlyUsed(String data, String type, boolean isfianl, boolean isFieldDeclare,
			boolean isCommonDeclare, boolean isFieldUseOrDeclare, boolean isCommonUseOrDeclare) {
		sdm.AddDataNewlyUsed(data, type, isfianl, isFieldDeclare, isCommonDeclare, isFieldUseOrDeclare,
				isCommonUseOrDeclare);
	}

	protected String GetVeryRecentDeclaredType() {
		if (VeryRecentDeclaredType.size() > 0) {
			return VeryRecentDeclaredType.peek();
		}
		return null;
	}

	protected void CheckVeryRecentDeclaredTypeMustNotNull(String declaredtype) {
		if (declaredtype == null) {
			MyLogger.Error("No Declared Type? The system will exit.");
			new Exception("No Recent Declared Type").printStackTrace();
			System.exit(1);
		}
	}

	protected void ClearClassAndLabelInfo() {
		cjcs.ClearAll();
		ljcs.ClearAll();
	}

	protected boolean NodeIsRefered(int nodehashcode) {
		Boolean isrefered = berefered.GetNodeHelp(nodehashcode);
		if (isrefered != null && isrefered == true) {
			return true;
		}
		return false;
	}

	protected boolean NodeIsAlreadyRefered(int nodehashcode) {
		Boolean isrefered = bereferedAlready.GetNodeHelp(nodehashcode);
		if (isrefered != null && isrefered == true) {
			return true;
		}
		return false;
	}

	protected void ExpressionReferPreHandle(Expression expr, int referenceHint) {
		int exprhashcode = expr.hashCode();
		referhint.AddNodeHelp(exprhashcode, referenceHint);
		AddNodeRefered(exprhashcode);
	}

	protected String ExpressionReferPostHandle(ASTNode node, Expression expr, String operator, String operatorHint,
			String addedcnt, boolean exprisleft, boolean needaddsplitter, boolean couldAppend, boolean mustAppend,
			boolean asreturn) {
		boolean mustOneLine = false;
		int exprhashcode = expr.hashCode();
		String exprcode = referedcnt.GetNodeHelp(exprhashcode);
		Boolean exprnoline = refernoline.GetNodeHelp(exprhashcode);
		if (exprnoline == null || exprnoline == false) {
			exprnoline = false;
		}
		boolean occupyline = true;
		if (exprnoline) {
			occupyline = false;
		}
		if (operator != null && !operator.equals("")) {
			mustOneLine = true;
		}
		if (addedcnt != null && !addedcnt.equals("")) {
			mustOneLine = true;
		}
		if (mustOneLine) {
			occupyline = true;
		}
		/*
		 * if (node instanceof PrefixExpression || node instanceof
		 * PostfixExpression) { occupyline = false; }
		 */
		/*
		 * if (node instanceof PrefixExpression) { MyLogger.Info("nodestr:" +
		 * node + ";node expr null?" + (exprcode == null)); }
		 */
		boolean exprused = false;
		if (exprcode == null) {
			if ((operator == null || operator.equals("")) && (addedcnt == null || addedcnt.equals(""))) {
				exprcode = "";
			} else {
				exprcode = GCodeMetaInfo.PreExist;
			}
		} else {
			if (!needaddsplitter) {
				if (CheckAppend() && exprnoline && !occupyline && exprisleft
						&& !(node instanceof PostfixExpression || node instanceof PrefixExpression)) {
					GenerateOneLine(exprcode, false, false, false, false, null);
					exprused = true;
					exprcode = "";
				}
			}
		}

		// MyLogger.Info("nodestr:"+node+";expr:"+expr+";exprcode:"+exprcode+";operator:"+operator);

		String nodecode = "";
		String splitter = (needaddsplitter ? GCodeMetaInfo.CommonSplitter : "");
		if (exprisleft) {
			operator = (operator == null || operator.equals("")) ? "" : (splitter + operator);
			addedcnt = (addedcnt == null || addedcnt.equals("")) ? "" : (splitter + addedcnt);
		} else {
			operator = (operator == null || operator.equals("")) ? "" : (operator + splitter);
			addedcnt = (addedcnt == null || addedcnt.equals("")) ? "" : (addedcnt + splitter);
		}
		if (exprisleft) {
			nodecode = exprcode + operator + addedcnt;
		} else {
			nodecode = addedcnt + operator + exprcode;
		}
		if (!asreturn && !nodecode.equals("")) {
			int nodehashcode = node.hashCode();
			if (!mustOneLine && NodeIsRefered(nodehashcode)) {
				referedcnt.AddNodeHelp(nodehashcode, nodecode);
				refernoline.AddNodeHelp(nodehashcode, true);
			} else {
				if (!occupyline) {
					operatorHint = "";
				}
				boolean mustPre = false;
				if (exprused && exprisleft) {
					mustPre = true;
				}
				GenerateOneLine(nodecode, couldAppend, mustAppend, mustPre, occupyline, operatorHint);
			}
		}

		referhint.DeleteNodeHelp(exprhashcode);
		DeleteNodeRefered(exprhashcode);
		if (nodecode.equals("")) {
			// new Exception("====== How Strange ======").printStackTrace();
			// return "============================================== How
			// Strange ==============================================";
			nodecode = null;
		}
		return nodecode;
	}

	protected void SetVeryRecentNotGenerateCode(boolean veryRecentNotGenerateCode) {
		VeryRecentNotGenerateCode = veryRecentNotGenerateCode;
	}

	protected void SetVeryRecentDeclaredType(String veryRecentDeclaredType) {
		if (veryRecentDeclaredType == null) {
			VeryRecentDeclaredType.pop();
		} else {
			VeryRecentDeclaredType.push(veryRecentDeclaredType);
		}
	}

	protected String GenerateVariableDeclarationTypeCode(String typecode, List<ASTNode> dimens) {
		String dimenstr = "";
		if (dimens != null && dimens.size() > 0) {
			Iterator<ASTNode> itr = dimens.iterator();
			while (itr.hasNext()) {
				dimenstr += itr.next().toString();
			}
		}
		return GCodeMetaInfo.VariableDeclarationHint + typecode + dimenstr;
	}

	protected boolean CheckAppend() {
		return omc.CheckAppend();
	}

	protected void CheckHint(Integer hint) {
		if (hint == null || hint == ReferenceHintLibrary.NoHint) {
			MyLogger.Error("There is no hint in this node handle. The system will exit.");
			new Exception("No hint exception").printStackTrace();
			System.exit(1);
		}
	}

	protected void CheckCode(String code) {
		if (code == null) {
			MyLogger.Error("There is no code in this node handle. The system will exit.");
			new Exception("No code exception").printStackTrace();
			System.exit(1);
		}
	}

	protected void AddNodeRefered(int nodehashcode) {
		if (NodeIsRefered(nodehashcode)) {
			bereferedAlready.AddNodeHelp(nodehashcode, true);
		} else {
			berefered.AddNodeHelp(nodehashcode, true);
		}
	}

	protected void DeleteNodeRefered(int nodehashcode) {
		if (NodeIsAlreadyRefered(nodehashcode)) {
			bereferedAlready.DeleteNodeHelp(nodehashcode);
		} else {
			berefered.DeleteNodeHelp(nodehashcode);
			referedcnt.DeleteNodeHelp(nodehashcode);
		}
	}

	public Map<String, String> GetGeneratedCode() {
		Map<String, String> result = new TreeMap<String, String>();
		result.putAll(ocm.getOtherCodeMap());
		StringBuilder sb = new StringBuilder("");
		if (!ojfc.IsEmpty()) {
			sb.append(ojfc.toString());
		}
		if (!acp.IsEmpty()) {
			sb.append(acp.toString());
		}
		if (sb.length() > 0)
		{
			result.put(GCodeMetaInfo.LogicCorpus, sb.toString());
		}
		return result;
	}

	protected void TypePreHandle(Type type, boolean simplified) {
		berefered.AddNodeHelp(type.hashCode(), true);
		if (simplified) {
			typesimp.AddNodeHelp(type.hashCode(), true);
		}
	}

	protected String TypeContent(Type type) {
		return referedcnt.GetNodeHelp(type.hashCode());
	}

	protected void TypePostHandle(Type type) {
		int typehashcode = type.hashCode();
		berefered.DeleteNodeHelp(typehashcode);
		referedcnt.DeleteNodeHelp(typehashcode);
		typesimp.DeleteNodeHelp(typehashcode);
	}

	protected void EnterBlock(ASTNode node) {
		// MyLogger.Info("Hashcode:"+node.hashCode()+";node:"+node);
		int nhash = node.hashCode();
		Boolean ck = scopeck.get(nhash);
		if (ck != null)
		{
			throw new ConflictASTNodeHashCodeError("Conflict Scope.");
		}
		scopeck.put(nhash, true);
		sdm.EnterBlock(nhash);
	}

	protected void ExitBlock(ASTNode node) {
		sdm.ExitBlock();
		scopeck.remove(node.hashCode());
	}

	protected void LabelReferPreHandle(SimpleName label) {
		if (label != null) {
			int namehashcode = label.hashCode();
			runpermit.AddNodeHelp(namehashcode, true);
			referhint.AddNodeHelp(namehashcode, ReferenceHintLibrary.LabelUse);
			AddNodeRefered(namehashcode);
		}
	}

	protected void LabelReferPostHandle(String prefix, SimpleName label) {
		String labelcode = null;
		if (label != null) {
			int namehashcode = label.hashCode();
			labelcode = referedcnt.GetNodeHelp(namehashcode);

			runpermit.DeleteNodeHelp(namehashcode);
			referhint.DeleteNodeHelp(namehashcode);
			DeleteNodeRefered(namehashcode);
		}
		String nodecode = prefix + (labelcode != null ? GCodeMetaInfo.CommonSplitter + labelcode : "");
		GenerateOneLine(nodecode, false, false, false, true, null);
	}

	protected void VariableDeclarationFragmentPreHandle(Expression iniexpr, SimpleName name) {
		int hint = ReferenceHintLibrary.DataDeclare;
		if (VeryRecentIsFieldDeclared) {
			hint = ReferenceHintLibrary.FieldDeclare;
		}
		int namehashcode = name.hashCode();
		referhint.AddNodeHelp(namehashcode, hint);
		runpermit.AddNodeHelp(namehashcode, true);
		if (iniexpr != null) {
			referhint.AddNodeHelp(iniexpr.hashCode(), ReferenceHintLibrary.DataUse);
			// AddNodeRefered(iniexpr.hashCode());
			if (!VeryRecentNotGenerateCode) {
				GenerateOneLine(GCodeMetaInfo.VariableDeclarationHolder + "=", true, true, false, true, null);
			}
		} else {
			if (!VeryRecentNotGenerateCode) {
				GenerateOneLine(GCodeMetaInfo.VariableDeclarationHolder, false, false, false, true, null);
			}
		}
	}

	protected void VariableDeclarationFragmentPostHandle(Expression iniexpr, SimpleName name) {
		if (!VeryRecentNotGenerateCode) {
			GenerateEndInfo(GCodeMetaInfo.DescriptionHint + GCodeMetaInfo.EndOfAPartialStatement);
		}

		// delete hint
		int namehashcode = name.hashCode();
		referhint.DeleteNodeHelp(namehashcode);
		runpermit.DeleteNodeHelp(namehashcode);
		if (iniexpr != null) {
			// DeleteNodeRefered(iniexpr.hashCode());
			referhint.DeleteNodeHelp(iniexpr.hashCode());
		}
	}

	protected void MethodPushReferRequest(Expression expr, List<ASTNode> args) {
		if (expr != null) {
			int exprhashcode = expr.hashCode();
			referhint.AddNodeHelp(exprhashcode, ReferenceHintLibrary.DataUpdate);
			AddNodeRefered(exprhashcode);
		}
		if (args != null && args.size() > 0) {
			Iterator<ASTNode> itr = args.iterator();
			while (itr.hasNext()) {
				ASTNode arg = itr.next();
				int arghashcode = arg.hashCode();
				referhint.AddNodeHelp(arghashcode, ReferenceHintLibrary.DataUse);
				AddNodeRefered(arghashcode);
				argmutiple.push(false);
				AddFirstOrderTask(new FirstOrderTask(arg, null, arg.getParent(), true) {
					@Override
					public void run() {
						if (argmutiple.pop()) {
							GenerateEndInfo(GCodeMetaInfo.DescriptionHint + GCodeMetaInfo.EndOfAPartialStatement);
							// AppendEndInfoToLast(GCodeMetaInfo.EndOfAPartialStatement);
						}
					}
				});
			}
		}
	}

	protected void MethodDeleteReferRequest(Expression expr, List<ASTNode> args) {
		if (expr != null) {
			int exprhashcode = expr.hashCode();
			DeleteNodeRefered(exprhashcode);
			referhint.DeleteNodeHelp(exprhashcode);
		}
		if (args != null && args.size() > 0) {
			Iterator<ASTNode> itr = args.iterator();
			while (itr.hasNext()) {
				ASTNode arg = itr.next();
				int arghashcode = arg.hashCode();
				DeleteNodeRefered(arghashcode);
				referhint.DeleteNodeHelp(arghashcode);
			}
		}
	}

	protected void MethodInvocationCode(String methodName, String invoker, List<ASTNode> args) {
		/*
		 * if (methodName.equals("getCodeBase")) {
		 * MyLogger.Info("==============hahaha=============="); }
		 */
		StringBuilder nodecode = new StringBuilder("");
		nodecode.append(GCodeMetaInfo.MethodInvocationHint + methodName);
		String pre = "(";
		String post = ")";
		nodecode.append(pre);
		nodecode.append(invoker);
		Iterator<ASTNode> itr = args.iterator();
		while (itr.hasNext()) {
			ASTNode arg = itr.next();
			nodecode.append(",");
			String argcnt = referedcnt.GetNodeHelp(arg.hashCode());
			if (argcnt == null) {
				nodecode.append(GCodeMetaInfo.PreExist);
			} else {
				nodecode.append(argcnt);
			}
		}
		nodecode.append(post);
		GenerateOneLine(nodecode.toString(), false, false, false, true, null);
	}

	protected void EnumConstantInvocationCode(String enumConstantName, String invoker, List<Expression> arguments) {
		StringBuilder nodecode = new StringBuilder("");
		nodecode.append(GCodeMetaInfo.EnumConstantDeclarationHint + enumConstantName);
		if (arguments != null && arguments.size() > 0) {
			String pre = "(";
			String post = ")";
			nodecode.append(pre);
			nodecode.append(invoker);
			Iterator<Expression> itr = arguments.iterator();
			while (itr.hasNext()) {
				ASTNode arg = itr.next();
				nodecode.append(",");
				String argcnt = referedcnt.GetNodeHelp(arg.hashCode());
				if (argcnt == null) {
					nodecode.append(GCodeMetaInfo.PreExist);
				} else {
					nodecode.append(argcnt);
				}
			}
			nodecode.append(post);
		}
		GenerateOneLine(nodecode.toString(), false, false, false, true, null);
	}

	protected void ErrorAndStop(String info) {
		MyLogger.Error(info);
		MyLogger.Error("The system will exit.");
		new Exception().printStackTrace();
		System.exit(1);
	}

	protected void ResetDLM() {
		sdm.ResetCurrentClassField();
	}
	

	public ArrayList<String> GetMainAnalyseList(boolean isInAnonymous) {
		if (isInAnonymous)
		{
			return acp.GetRecentAnalyseList();
		}
		else
		{
			return ojfc.toList();
		}
	}

}