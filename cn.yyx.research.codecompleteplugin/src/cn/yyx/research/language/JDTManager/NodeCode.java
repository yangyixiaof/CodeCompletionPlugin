package cn.yyx.research.language.JDTManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Stack;

public class NodeCode {
	// once set, no change.
	private int firstCodeLevel = -1;
	private boolean couldAppend = false;
	private boolean mustAppend = false;
	protected Stack<Boolean> argmutiple = new Stack<Boolean>();

	ArrayList<String> codelist = new ArrayList<String>();

	public NodeCode(Stack<Boolean> argmutiple) {
		this.argmutiple = argmutiple;
	}

	public boolean IsEmpty() {
		return codelist.size() == 0;
	}

	public boolean LastCharacterIsDot() {
		if (codelist.size() > 0) {
			String code = codelist.get(codelist.size() - 1);
			if (code.charAt(code.length() - 1) == '.') {
				return true;
			}
		}
		return false;
	}

	public boolean NotInitialize() {
		return getFirstCodeLevel() == -1;
	}
	
	public void AddOneLineCode(String code, boolean couldappend, boolean mustappend, boolean mustpre, boolean occupyoneline, String preHint) {
		if (code == null || code.equals(""))
		{
			return;
		}
		
		// debugging
		if (code.trim().equals("'"))
		{
			System.err.println("Wrong error: what is '?");
			System.exit(1);
		}
		if (code.trim().equals("')"))
		{
			System.err.println("Wrong error: what is ')?");
			System.exit(1);
		}
		
		// lastCodeLevel = level;
		/*if (code == null)
		{
			new Exception("AppendOneLineCode Is Null").printStackTrace();
		}
		if (code.equals("null3"))
		{
			System.err.println("codelast:" + codelist.get(codelist.size()-1) + ";1:couldappend:"+couldappend+";mustappend:"+mustappend+";mustpre:"+mustpre+";occupyoneline:"+occupyoneline);
			System.err.println("codelast:" + codelist.get(codelist.size()-1) + ";2:couldappend:"+couldAppend+";mustappend:"+mustAppend);
		}*/
		boolean iscodenewline = false;
		if (couldAppend)
		{
			if (occupyoneline)
			{
				if (mustAppend)
				{
					AppendEndInfoToLast(GCodeMetaInfo.CodeHole);
				}
				if (preHint != null && !preHint.equals(""))
				{
					code = preHint + code;
				}
				codelist.add(code);
				iscodenewline = true;
			}
			else
			{
				AppendEndInfoToLast(code);
			}
		}
		else
		{
			if (mustpre)
			{
				code = GCodeMetaInfo.PreExist + code;
				codelist.add(code);
				iscodenewline = true;
			}
			if (preHint != null && !preHint.equals(""))
			{
				code = preHint + code;
			}
			codelist.add(code);
			iscodenewline = true;
		}
		
		// set couldAppend.
		this.mustAppend = mustappend;
		this.couldAppend = couldappend;
		if (iscodenewline)
		{
			if (argmutiple.size() > 0)
			{
				Boolean mut = argmutiple.pop();
				if (!mut)
				{
					mut = true;
				}
				argmutiple.push(mut);
			}
		}
	}
	
	public void GenerateEndInfo(String lcode)
	{
		if (lcode.charAt(lcode.length()-1) == ';')
		{
			int idx = codelist.size() - 1;
			String lastcode = codelist.get(idx);
			char lc = lastcode.charAt(lastcode.length()-1);
			if (lc == ',' || lc == ';')
			{
				codelist.remove(idx);
			}
		}
		codelist.add(lcode);
	}

	public void AppendEndInfoToLast(String apdcode) {
		if (apdcode.equals(";"))
		{
			CheckAndDeletePartialEnd();
		}
		AppendToLast(apdcode);
	}
	
	private void CheckAndDeletePartialEnd() {
		int idx = codelist.size() - 1;
		String lastcode = codelist.get(idx);
		if (lastcode.endsWith(","))
		{
			lastcode = lastcode.substring(0, lastcode.length()-1);
			codelist.set(idx, lastcode);
		}
	}

	public void AppendToLast(String apdcode)
	{
		int idx = codelist.size() - 1;
		String lastcode = codelist.get(idx) + (apdcode);
		codelist.set(idx, lastcode);
	}

	public int getFirstCodeLevel() {
		return firstCodeLevel;
	}

	public void setFirstCodeLevel(int firstCodeLevel) {
		this.firstCodeLevel = firstCodeLevel;
	}

	public Iterator<String> GetCodeIterator() {
		return codelist.iterator();
	}

	public String GetLastCode() {
		return codelist.get(codelist.size()-1);
	}

	public boolean CheckAppend() {
		if (couldAppend || mustAppend)
		{
			return true;
		}
		return false;
	}

}