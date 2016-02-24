package cn.yyx.research.language.JDTHelper;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.eclipse.jdt.core.dom.AbstractTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeDeclaration;
import org.eclipse.jdt.core.dom.AnnotationTypeMemberDeclaration;
import org.eclipse.jdt.core.dom.BodyDeclaration;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.EnumConstantDeclaration;
import org.eclipse.jdt.core.dom.EnumDeclaration;
import org.eclipse.jdt.core.dom.FieldDeclaration;
import org.eclipse.jdt.core.dom.Initializer;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.TypeDeclaration;

import cn.yyx.research.language.Utility.CorpusContentPair;
import cn.yyx.research.language.simplified.JDTHelper.SimplifiedCodeGenerateASTVisitor;

public class ClassLogicDetailCorpus {
	
	@SuppressWarnings("unchecked")
	public static ArrayList<CorpusContentPair> GenerateClassDetailCorpus(CompilationUnit compilationUnit)
	{
		List<AbstractTypeDeclaration> typeDeclarations = compilationUnit.types();
		Map<String, String> allcodemap = new TreeMap<String, String>();
		for (AbstractTypeDeclaration object : typeDeclarations) {
			AbstractTypeDeclaration clazzNode = (AbstractTypeDeclaration) object;
			if (IsEmptyTypeDeclaration(clazzNode))
			{
				continue;
			}
			SimplifiedCodeGenerateASTVisitor fmastv = new SimplifiedCodeGenerateASTVisitor();
			clazzNode.accept(fmastv);
			Map<String, String> codemap = fmastv.GetGeneratedCode();
			Set<String> keys = codemap.keySet();
			Iterator<String> itr = keys.iterator();
			while (itr.hasNext())
			{
				String corpus = itr.next();
				if (allcodemap.get(corpus) == null)
				{
					allcodemap.put(corpus, "");
				}
				String value = codemap.get(corpus);
				allcodemap.put(corpus, (allcodemap.get(corpus) + value));
			}
			codemap = null;
		}
		ArrayList<CorpusContentPair> result = new ArrayList<CorpusContentPair>();
		Set<String> keys = allcodemap.keySet();
		Iterator<String> itr = keys.iterator();
		while (itr.hasNext())
		{
			String corpus = itr.next();
			String content = allcodemap.get(corpus);
			result.add(new CorpusContentPair(corpus, content));
		}
		
		// clear variables
		allcodemap = null;
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public static boolean IsEmptyTypeDeclaration(AbstractTypeDeclaration clazzNode)
	{
		boolean isempty = true;
		List<BodyDeclaration> bs = clazzNode.bodyDeclarations();
		Iterator<BodyDeclaration> itr = bs.iterator();
		while (itr.hasNext())
		{
			BodyDeclaration bd = itr.next();
			if (bd instanceof AnnotationTypeMemberDeclaration || bd instanceof EnumConstantDeclaration || bd instanceof FieldDeclaration || bd instanceof Initializer || bd instanceof MethodDeclaration)
			{
				isempty = false;
				break;
			}
			if (bd instanceof AnnotationTypeDeclaration || bd instanceof EnumDeclaration || bd instanceof TypeDeclaration)
			{
				boolean resempty = IsEmptyTypeDeclaration((AbstractTypeDeclaration)bd);
				if (!resempty)
				{
					break;
				}
	 			
			}
		}
		return isempty;
	}
	
}