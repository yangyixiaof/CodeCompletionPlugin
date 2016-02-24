package cn.yyx.research.language.JDTHelper;

import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.ASTVisitor;
import org.eclipse.jdt.core.dom.BlockComment;
import org.eclipse.jdt.core.dom.Javadoc;
import org.eclipse.jdt.core.dom.LineComment;
import org.eclipse.jface.text.IDocument;

public class RemoveCommentASTVisitor extends ASTVisitor {

	IDocument document = null;

	public RemoveCommentASTVisitor(IDocument document) {
		this.document = document;
	}

	@Override
	public boolean visit(Javadoc node) {
		try {
			ASTNode naltroot = node.getAlternateRoot();
			if (naltroot != null) {
				naltroot.delete();
			} else {
				node.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(BlockComment node) {
		try {
			ASTNode naltroot = node.getAlternateRoot();
			if (naltroot != null) {
				naltroot.delete();
			} else {
				node.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.visit(node);
	}

	@Override
	public boolean visit(LineComment node) {
		/*
		 * System.out.printf("Comment.toString(): %s\n", node.toString()); try {
		 * System.out.printf("Comment Text: %s\n",
		 * document.get(node.getStartPosition(), node.getLength())); } catch
		 * (BadLocationException e) { e.printStackTrace(); }
		 */
		try {
			// try to delete comment node
			// using ASTRewrite to record modification
			ASTNode naltroot = node.getAlternateRoot();
			if (naltroot != null) {
				naltroot.delete();
				// this.rewrite.remove(naltroot, null);
			} else {
				// this.rewrite.remove(node, null); // this will throw
				// NullPointerException
				node.delete();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.visit(node);
	}
}