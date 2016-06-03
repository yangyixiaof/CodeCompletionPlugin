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
	
	public static boolean NewLineBegin(IDocument doc, int offset) throws BadLocationException
	{
		boolean newlinebegin = true;
		String precontentraw = doc.get(0, offset);
		int lbkidx = precontentraw.lastIndexOf('\n');
		for (int i=lbkidx+1;i<offset;i++)
		{
			char c = precontentraw.charAt(i);
			if (c == ' ' || c == '\t')
			{
				continue;
			} else {
				newlinebegin = false;
				break;
			}
		}
		return newlinebegin;
	}
	
}