package cn.yyx.contentassist.commonutils;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jdt.ui.text.java.JavaContentAssistInvocationContext;

public class ContextHandler {
	private JavaContentAssistInvocationContext javacontext = null;
	private IProgressMonitor monitor = null;
	
	public ContextHandler(JavaContentAssistInvocationContext javacontext, IProgressMonitor monitor) {
		this.setJavacontext(javacontext);
		this.setMonitor(monitor);
	}

	public JavaContentAssistInvocationContext getJavacontext() {
		return javacontext;
	}

	public void setJavacontext(JavaContentAssistInvocationContext javacontext) {
		this.javacontext = javacontext;
	}

	public IProgressMonitor getMonitor() {
		return monitor;
	}

	public void setMonitor(IProgressMonitor monitor) {
		this.monitor = monitor;
	}
	
}
