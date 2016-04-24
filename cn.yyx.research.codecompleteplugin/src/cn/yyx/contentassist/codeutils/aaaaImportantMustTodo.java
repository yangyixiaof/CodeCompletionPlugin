package cn.yyx.contentassist.codeutils;

public class aaaaImportantMustTodo {
	
	// TODO some rule adds more conditions, must be handled. such superFieldAccess not exists in previous version.
	
	// TODO needs to handle java varargs: Object... as Object[].
	
	// TODO DH@then and DH@else are not handled.
	
	// Solved. the handle of @HO can only be in the very end. remember to handle @HO at very end. No need to do that, array start and array end signal has solved the problem.
	// be sure this happens: A@$C0?0=@HO IxE@1+@HO. Not this: A@$C0?0=@HO IxE@1+2 IxE@@pre+2.
	// Such as : A@$C0?0=@HO m() ma(@pre).
	
	// Solved. what is exactly the field offset? Now just the declaration position.
	
	// Solved what is behind enumConstantDeclarationStatement, can that cause stopping? If not, change that. This is very important. Add 'DH@E,' as the end signal.
	
	// method arg split should use DH@@, instead of DH@, to distinguish with common , Oh, this should be ignored.
	
	// Solved. Question: int a[][][] = new int[][][]; | int a[][][] = {......};the [][][] behind new should be considered. This needs the whole effort. But it may be included in ArrayType. ArrayClassCreation just handles it.
	// Solved. reminder to add '{' and '}' to the begin and end of type declaration. There is no need to do that.
	
	// Solved. if has hint, must handled directly add the modified to result. This is handled in the new mechanism.
	
	// Solved. int[] when inferring specification, [] should be eliminated, this must be considered.
	
	// Solved. int must be translated to Integer. boolean float double char are the same. This must be done in program-processor phase. Not done in program-processor phase but handled in the plugin.
	
	// Solved. anonymousBegin need to be added to sequence. No problem.
	
	// Solved. method declaration should add return type.
	
	// Solved. a[a[]] need to be tested.
	
	// Solved. cstack must not set the structure info to null, the whole judge schema should be changed. Now changed to all-search.
	
	// Solved. field declare not handled.
}