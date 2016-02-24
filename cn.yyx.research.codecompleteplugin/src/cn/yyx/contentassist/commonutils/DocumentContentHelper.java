package cn.yyx.contentassist.commonutils;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

public class DocumentContentHelper {
	
	public static char GetOffsetLastChar(IDocument doc, int offset) throws BadLocationException
	{
		String precontentraw = doc.get(0, offset);
		String precontent = precontentraw.trim();
		char lastchar = precontent.charAt(precontent.length() - 1);
		return lastchar;
	}
	
}