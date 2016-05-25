package cn.yyx.contentassist.commonutils;

import org.eclipse.core.runtime.IProgressMonitor;

public class TimeOutProgressMonitor implements IProgressMonitor {
	
	private long timeout = 0;
	private long fEndTime;
	
	public TimeOutProgressMonitor(final long timeout) {
		this.timeout = timeout;
	}
	
	@Override
	public void beginTask(String name, int totalWork) {
		fEndTime = System.currentTimeMillis() + timeout;
	}
	
	@Override
	public boolean isCanceled() {
		return fEndTime <= System.currentTimeMillis();
	}
	
	@Override
	public void done() {
	}
	
	@Override
	public void internalWorked(double work) {
	}
	
	@Override
	public void setCanceled(boolean value) {
	}
	
	@Override
	public void setTaskName(String name) {
	}
	
	@Override
	public void subTask(String name) {
	}
	
	@Override
	public void worked(int work) {
	}
	
}