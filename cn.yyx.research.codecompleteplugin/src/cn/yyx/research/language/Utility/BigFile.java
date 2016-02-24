package cn.yyx.research.language.Utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class BigFile {

	ABigSourceCodeFile mBigFile;
	
	/*public BigFile(String prefix) {
		mBigFile = new ABigSourceCodeFile(prefix);
	}*/
	
	public BigFile(File f)
	{
		mBigFile = new ABigSourceCodeFile(f);
	}
	
	public void AppendOneContentToTheBigFile(String onecnt) {
		try {
			mBigFile.Begin();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		mBigFile.appendContent(onecnt+"\n");
		mBigFile.End();
	}
	
	public void AppendOneFileToTheBigFile(File onefile) {
		try {
			mBigFile.Begin();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(onefile));
			String tempString = null;
			while ((tempString = reader.readLine()) != null) {
				tempString = tempString.trim();
				if (!tempString.equals(""))
				{
					mBigFile.appendContent(tempString+"\n");
				}
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
		mBigFile.End();
	}
	
}