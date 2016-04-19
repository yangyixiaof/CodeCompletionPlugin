package cn.yyx.contentassist.intellijcodecompletion;

public class TODO {
	
	// Solved. exact match must merge the possibility.
	// Solved. Some small part of a statement duplicate its possibility, this is a serious problem. Just judge two sentence if same, if same not merge probability.
	
	// Solved. multiple '(' or ')' should merge. multiple '{' or '}' should merge.
	
	// Solved. do-while should before judge.
	// Solved. do statement should be eliminated. do-while become do.
	
	// Solved. the sequence has not been eliminated by LCS.
	
	// do-while use '{'/itself to stop instead of ';'.
	
	// Solved. String new this super.a system.out such must be handled. Include fieldAccess identifier.
}