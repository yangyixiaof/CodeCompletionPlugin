package cn.yyx.research.language.JDTManager;

import org.eclipse.jdt.core.dom.ASTNode;

public abstract class FirstOrderTask implements Runnable {
	
	ASTNode pre = null;
	ASTNode post = null;
	ASTNode infixnode = null;
	boolean afterprerun = false;
	
	public FirstOrderTask(ASTNode pre, ASTNode post, ASTNode infixnode, boolean afterprerun) {
		this.setPre(pre);
		this.setPost(post);
		this.setInfixnode(infixnode);
		this.setAfterprerun(afterprerun);
	}
	
	public ASTNode getInfixnode() {
		return infixnode;
	}
	
	public void setInfixnode(ASTNode infixnode) {
		this.infixnode = infixnode;
	}
	
	public ASTNode getPost() {
		return post;
	}
	
	public void setPost(ASTNode post) {
		this.post = post;
	}
	
	public ASTNode getPre() {
		return pre;
	}
	
	public void setPre(ASTNode pre) {
		this.pre = pre;
	}
	
	public boolean isAfterprerun() {
		return afterprerun;
	}
	
	public void setAfterprerun(boolean afterprerun) {
		this.afterprerun = afterprerun;
	}
	
}