package cn.yyx.contentassist.codehelper;

import org.eclipse.jdt.internal.compiler.env.ICompilationUnit;
import org.eclipse.jface.text.IDocument;

@SuppressWarnings("restriction")
public class MyCompilationUnit implements ICompilationUnit {

	ICompilationUnit sourceunit = null;
	String content = null;
	
	public MyCompilationUnit(ICompilationUnit sourceunit, IDocument doc, String prefix, int offset) {
		try {
			this.sourceunit = sourceunit;
			String precontentraw = doc.get(0, offset);
			String postcontentraw = doc.get(offset, doc.getLength()-offset);
			String rawaddedtext = prefix;
			content = precontentraw + rawaddedtext + postcontentraw;
			
			
			int afteroffet = offset + prefix.length();
			System.out.println("prefix: " + prefix);
			System.out.println("context : " + content);
			// System.out.println("content type at offset: " + afteroffet + " : " + doc.getContentType(afteroffet));
			System.out.println("offset: " + afteroffet);
			System.out.println("content before offset: " + content.substring(0, afteroffet) + "#end#");
			System.out.println("content after offset: " + content.substring(afteroffet, content.length()) + "#end#");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public char[] getFileName() {
		return sourceunit.getFileName();
	}

	@Override
	public char[] getContents() {
		return content.toCharArray();
	}

	@Override
	public char[] getMainTypeName() {
		return sourceunit.getMainTypeName();
	}

	@Override
	public char[][] getPackageName() {
		return sourceunit.getPackageName();
	}

	@Override
	public boolean ignoreOptionalProblems() {
		return sourceunit.ignoreOptionalProblems();
	}
	
}