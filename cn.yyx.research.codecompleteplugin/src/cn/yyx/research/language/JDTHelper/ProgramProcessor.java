package cn.yyx.research.language.JDTHelper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import cn.yyx.research.language.Utility.CorpusContentPair;

public class ProgramProcessor {
	
	public static ArrayList<CorpusContentPair> ProcessOneJavaFile(File f) throws Exception
	{
		ArrayList<CorpusContentPair> target = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(f));
			StringBuilder content = new StringBuilder();
			String tmp = null;
			while ((tmp = reader.readLine()) != null) {
				content.append(tmp);
				content.append("\n");
			}
			reader.close();
			reader = null;
			String source = content.toString();
			
			ASTTraversal astmdf = new ASTTraversal(f.getName(), source);
			target = astmdf.GeneratePredictionSuiteOnJava();
			astmdf.Clear();
			astmdf = null;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return target;
	}
	
}