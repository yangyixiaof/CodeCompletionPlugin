package cn.yyx.contentassist.codesynthesis.typeutil;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.BooleanRTwoSideSame;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.BooleanRTwoSideSameBoolean;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.InheritLeftOrRightTwoSameSide;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.InheritLeftRightNumbetBit;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.NumberBitRTwoSideSameNumberBit;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.StringNumberBitRTwoSideSameNumberBitOrOneString;
import cn.yyx.contentassist.codesynthesis.typeutil.computations.TypeComputationKind;

public class TypeComputer {
	
	/*public static CCType ComputeType(CCType c1, CCType c2, TypeComputationKind tck) throws TypeConflictException
	{
		switch (tck) {
		case NoOptr:
			if (!CCTypeNull(c1) && CCTypeNull(c2))
			{
				return c1;
			}
			if (CCTypeNull(c1) && !CCTypeNull(c2))
			{
				return c2;
			}
			return c1;
			//if (c1 == null || c2 == null)
			//{
			//	return null;
			//}
			//throw new TypeConflictException("This Noptr but two class all have resolved class.");
		case BooleanRTwoSideSame:
			if (!CCTypeNull(c1) && CCTypeNull(c2))
			{
				return c1;
			}
			if (CCTypeNull(c1) && !CCTypeNull(c2))
			{
				return c2;
			}
			if (CCTypeNull(c1) && CCTypeNull(c2))
			{
				return new CCType(boolean.class, "boolean");
			}
			if (CCTypeSame(c1, c2))
			{
				return new CCType(boolean.class, "boolean");
			}
			throw new TypeConflictException("These two sides are not same.");
		case BooleanRTwoSideSameBoolean:
			
		case BooleanROnlyOneBoolean:
		case NumberBitROnlyOneNumberBit:
		case NumberBitRTwoSideSameNumberBit:
		case StringNumberBitRTwoSideSameNumberBitOrOneString:
		case NumberBitROneOrTwoSideSameNumberBit:
		case InheritLeftOrRightTwoSameSide:
		case InheritLeftRightNumbetBit:
		case DirectUniqueUseFirstTypeOptr:
			return c1;
		case DirectUniqueUseSecondTypeOptr:
			return c2;
		case LeftOrRightCast:
			if (c1 == null)
			{
				return c2;
			}
			if (c2 == null)
			{
				return c1;
			}
			if (c2.isAssignableFrom(c1))
			{
				return c1;
			}
			if (c1.isAssignableFrom(c2))
			{
				return c2;
			}
			throw new TypeConflictException(c1 + " can not be casted to " + c2 + ". Wrong cast optr.");
		default:
			break;
		}
		return null;
	}
	
	private static boolean CCTypeSame(CCType c1, CCType c2)
	{
		if (c1.getCls() == c2.getCls())
		{
			return true;
		}
		return false;
	}
	
	private static boolean CCTypeNull(CCType cct)
	{
		if (cct == null || cct.getCls() == null)
		{
			return true;
		}
		return false;
	}*/
	
	public static TypeComputationKind ComputeKindFromRawString(String optr) throws CodeSynthesisException
	{
		switch (optr) {
		case ">":
		case "<":
		case "==":
		case "<=":
		case ">=":
		case "!=":
			return new BooleanRTwoSideSame();
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
		case "+=":
		case "-=":
		case "*=":
		case "/=":
		case "&=":
		case "|=":
		case "^=":
		case "%=":
			return new InheritLeftOrRightTwoSameSide();
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