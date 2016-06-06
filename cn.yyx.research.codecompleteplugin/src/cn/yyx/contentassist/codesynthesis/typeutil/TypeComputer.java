package cn.yyx.contentassist.codesynthesis.typeutil;

import java.lang.reflect.Array;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.Assignment;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.BooleanR;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.BooleanRTwoSideSame;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.BooleanRTwoSideSameBoolean;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.BooleanRTwoSideSameNumberBit;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.NumberOpAssign;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.PlusAssign;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.InheritLeftRightNumbetBit;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.NumberBitRTwoSideSameNumberBit;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.StringNumberBitRTwoSideSameNumberBitOrOneString;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.TypeComputationKind;

public class TypeComputer {
	
	public static CCType CreateArrayType(CCType ct, int count, String dimens)
	{
		int[] x = new int[count];
		for (int i=0;i<count;i++)
		{
			x[i] = 0;
		}
		if (ct.getCls() != null) {
			ct.setCls(Array.newInstance(ct.getCls(), x).getClass());
		}
		ct.setClstr(ct.getClstr() + dimens);
		return ct;
	}
	
	public static boolean IsStrictNumberBit(Class<?> cls)
	{
		if (cls == Integer.class || cls == int.class || cls == Double.class || cls == double.class || cls == Byte.class || cls == byte.class || cls == Short.class || cls == short.class || cls == Long.class || cls == long.class || cls == Float.class || cls == float.class)
		{
			return true;
		}
		return false;
	}
	
	public static boolean IsNumberBit(Class<?> cls)
	{
		if (cls == Integer.class || cls == int.class || cls == Double.class || cls == double.class || cls == Byte.class || cls == byte.class || cls == Short.class || cls == short.class || cls == Long.class || cls == long.class || cls == Float.class || cls == float.class || cls == Boolean.class || cls == boolean.class)
		{
			return true;
		}
		return false;
	}
	
	public static boolean NumberBitSame(CCType c1, CCType c2)
	{
		if (IsNumberBit(c1.getCls()) && IsNumberBit(c2.getCls()))
		{
			return true;
		}
		return false;
	}
	
	public static boolean CCTypeSame(CCType c1, CCType c2)
	{
		if (TypeCheckHelper.NormalizeClass(c1.getCls()) == TypeCheckHelper.NormalizeClass(c2.getCls()))
		{
			return true;
		}
		return false;
	}
	
	public static TypeComputationKind ComputeKindFromRawString(String optr) throws CodeSynthesisException
	{
		switch (optr) {
		case ">":
		case "<":
		case "<=":
		case ">=":
			return new BooleanRTwoSideSameNumberBit();
		case "==":
			return new BooleanRTwoSideSame();
		case "!=":
			return new BooleanR();
		case "&&":
		case "||":
			return new BooleanRTwoSideSameBoolean();
		case "!":
		case "~":
			// return new BooleanROnlyOneBoolean();
			throw new TypeConflictException("! and ~ should be handled in prefix expression.");
		case "++":
		case "--":
			// return new NumberBitROnlyOneNumberBit();
			throw new TypeConflictException("++ and -- should be handled in prefix or postfix expression.");
		case "*":
		case "/":
		case "&":
		case "|":
		case "^":
		case "%":
		case "-":
			return new NumberBitRTwoSideSameNumberBit();
		case "+":
			return new StringNumberBitRTwoSideSameNumberBitOrOneString();
			// return new NumberBitROneOrTwoSideSameNumberBit();
			// return TypeComputationKind.ArithOptr;
		case "()":
			// this will never happen.
			throw new CodeSynthesisException("CastOptr just handle it, not let this function do this.");
			// return TypeComputationKind.CastOptr;
		case "=":
			return new Assignment();
		case "+=":
			return new PlusAssign();
		case "-=":
		case "*=":
		case "/=":
		case "&=":
		case "|=":
		case "^=":
		case "%=":
			return new NumberOpAssign();
		case "<<=":
		case ">>=":
		case ">>>=":
		case "<<":
		case ">>":
		case ">>>":
			return new InheritLeftRightNumbetBit();
		default:
			throw new CodeSynthesisException("Unknown optr:" + optr + ".");
		}
	}

	/*public static TypeComputationKind ChooseOne(TypeComputationKind oneafter, TypeComputationKind beforetwo) throws CodeSynthesisException {
		if (oneafter== null || oneafter == TypeComputationKind.NoOptr)
		{
			return beforetwo;
		}
		if (oneafter.equals(beforetwo))
		{
			return oneafter;
		}
		new Exception("Type Conflict in choose before and after types!").printStackTrace();
		throw new CodeSynthesisException("Type Conflict in choose before and after types!");
	}*/
	
}