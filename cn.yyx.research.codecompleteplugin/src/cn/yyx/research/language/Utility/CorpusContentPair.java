package cn.yyx.research.language.Utility;

public class CorpusContentPair {
	private String Corpus = null;
	private String Content = null;
	
	public CorpusContentPair(String pCorpus, String pContent) {
		setCorpus(pCorpus);
		setContent(pContent);
	}

	public String getCorpus() {
		return Corpus;
	}

	public void setCorpus(String corpus) {
		Corpus = corpus;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
	
}