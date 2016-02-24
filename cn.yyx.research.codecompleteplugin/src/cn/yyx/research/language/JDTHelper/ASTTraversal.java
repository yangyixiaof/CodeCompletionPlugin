package cn.yyx.research.language.JDTHelper;

import java.util.ArrayList;
import java.util.Map;

import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;

import cn.yyx.research.language.Utility.CorpusContentPair;

/**
 * @author yyx
 * 
 */
public class ASTTraversal {

	private Document document = null;
	private CompilationUnit compilationUnit = null;

	public ASTTraversal(String identifier, String sourceCode) {
		setDocument(new Document(sourceCode));
		setCompilationUnit(parseSourceCode(identifier, getDocument()));
	}

	@SuppressWarnings("unchecked")
	private CompilationUnit parseSourceCode(String identifier, IDocument pdocument) {
		ASTParser parser = ASTParser.newParser(AST.JLS8);
		parser.setSource(pdocument.get().toCharArray());
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
		parser.setUnitName(identifier);
		Map<String, String> options = JavaCore.getOptions();
		options.put(JavaCore.COMPILER_SOURCE, JavaCore.VERSION_1_8); //or newer version
		parser.setCompilerOptions(options);
		CompilationUnit compilationUnit = (CompilationUnit) parser.createAST(null);
		return compilationUnit;
	}

	/*
	 * private void DeleteCommentInAST() {
	 * 
	 * @SuppressWarnings("unchecked") List<Comment> comments =
	 * compilationUnit.getCommentList(); if (comments != null) { for (Comment
	 * comment : comments) { comment.accept(new
	 * RemoveCommentASTVisitor(document));//, rewrite } } }
	 */

	public ArrayList<CorpusContentPair> GeneratePredictionSuiteOnJava() {
		//CorpusContentPair cp_class_framework = ClassFrameworkCorpus.GenerateClassFrameworkCorpus(compilationUnit);
		//result.add(cp_class_framework);
		ArrayList<CorpusContentPair> cp_class_logic = ClassLogicDetailCorpus.GenerateClassDetailCorpus(getCompilationUnit());
		return cp_class_logic;
	}

	public void Clear() {
		setDocument(null);
		setCompilationUnit(null);
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public CompilationUnit getCompilationUnit() {
		return compilationUnit;
	}

	public void setCompilationUnit(CompilationUnit compilationUnit) {
		this.compilationUnit = compilationUnit;
	}

	/*public static void main(String[] args) {
		String identifier = "TestA.java";
		String sourceCode = "public class TestA<T,M> extends Test {\n\n Map< Integer, String > a,b;     T tt;     static{a=null;b=null;}    // this is comment\n    public TestA(Map< Integer, String > a){this.a = a;super.a.b = null;super.a.c();super.g(foo()).h=null;super(a);}     public POI foo(){return new POI();}    public int foo(int b, ArrayList<Object> t) {\n     a.put(\"ABC\",\"POD\");     Set<?> s;     int ty = 0;     synchronized(ty >= 0){ ty--; }     do {ty=ty+1;} while (ty<10);     while (ty>=0) {ty--;}     for (int i=0;i<1000;i++){++ty;}     Map< Integer, String > ppt;    int[] pt = new int[]{1,2,3};     for(int pppt : pt){System.out.println(pppt);}     int d = 3+6;     new Runnable() {int a=0; {a=1;} public void run() {System.out.println(\"hhaahaha !!! wo !!!\");}};     int[] t = {1,2,3,4,5};     if (t instanceof int[]) {t[0]=100;}      boolean ga = true;     if (ga == false) {t[2] = t[1]>0 ? t[3]: t[4];}     PK pk = new PK(1,2,3,\"dssdsd\");     Object o = new Object();     Entry e = (Entry)o;     POI poi = new POI();     T.c(poi.a.b);     System.out.println(foi(A.b()).bar().bt);     poi.abc = 100;     int y1=4;int y2=4;     switch (y1) {case 1:System.exit(1); break; default: System.exit(0); break;}     STE.df()=null;     t[y1+y2+y2*y1/y2+y1] += 10;     int[] p = new int[10];     char a = 's';     // this is comment, too\n        System.out.println(\"foo\");\n    return (5+6)*10;     File f = new File(\"temp.txt\");try {f.createNewFile();throw new Exception(\"This is an exception.\");} catch (IOException e) {e.printStackTrace();}}\n}";
		new ASTTraversal(identifier, sourceCode).GeneratePredictionSuiteOnJava();
	}*/

}