package cn.yyx.contentassist.commonutils;

import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;

public class ContextHandler {
	private JavaContentAssistInvocationContext javacontext = null;
	
	public ContextHandler(JavaContentAssistInvocationContext javacontext) {
		this.setJavacontext(javacontext);
	}

	public JavaContentAssistInvocationContext getJavacontext() {
		return javacontext;
	}

	public void setJavacontext(JavaContentAssistInvocationContext javacontext) {
		this.javacontext = javacontext;
	}
	
}