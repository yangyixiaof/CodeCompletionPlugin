package cn.yyx.contentassist.codeutils;

public class aaaaImportantMustTodo {
	
	// TODO arrayInitializerStartStatement add '{''}' directly, you must insert before '}'.
	// TODO the handle of @HO can only be in the very end. remember to handle @HO at very end.
	// be sure this happens: A@$C0?0=@HO IxE@1+@HO. Not this: A@$C0?0=@HO IxE@1+2 IxE@@pre+2.
	// Such as : A@$C0?0=@HO m() ma(@pre).
	
	// Solved. what is exactly the field offset? Now just the declaration position.
	
	// TODO what is behind enumConstantDeclarationStatement, can that cause stopping? If not, change that. This is very important.
	
	// method arg split should use DH@@, instead of DH@, to distinguish with common , Oh, this should be ignored.
	
	// TODO Question: int a[][][] = new int[][][]{......}; the [][][] behind new should be considered. This needs the whole effort. But it may be included in ArrayType.
	// TODO reminder to add '{' and '}' to the begin and end of type declaration.
	
	// TODO if has hint, must handled directly add the modified to result.
	
	// TODO int[] when inferring specification, [] should be eliminated, this must be considered.
	
	// TODO int must be translated to Integer. boolean float double char are the same. This must be done in program-processor phase.
	
	// Solved. anonymousBegin need to be added to sequence. No problem.
	
	// TODO method declaration should add return type.
	
	// TODO a[a[]] need to be tested.
	
	// TODO cstack must not set the structure info to null, the whole judge schema should be changed.
	
	// TODO field declare not handled.
}