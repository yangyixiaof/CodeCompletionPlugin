package cn.yyx.contentassist.codesynthesis.typeutil;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public class TypeComputer {
	
	// Class<?>
	public static CCType ComputeType(CCType c1, CCType c2, TypeComputationKind tck) throws TypeConflictException
	{
		switch (tck) {
		case NoOptr:
			if (c1 != null && c2 == null)
			{
				return c1;
			}
			if (c1 == null && c2 != null)
			{
				return c2;
			}
			return c1;
			//if (c1 == null || c2 == null)
			//{
			//	return null;
			//}
			//throw new TypeConflictException("This Noptr but two class all have resolved class.");
		case JudgeOptr:
			if (c1 != null && c2 != null)
			{
				if (c2.isAssignableFrom(c1))
				{
					return c1;
				}
				else
				{
					if (c1.isAssignableFrom(c2))
					{
						return c2;
					}
					else
					{
						throw new TypeConflictException(c1 + " and " + c2 + " can not be inter casted. Wrong judge optr.");
					}
				}
			}
			return new CCType(Boolean.class, "Boolean");
		case ArithOptr:
			if (c1 == null)
			{
				return c2;
			}
			else
			{
				if (c2 == null)
				{
					return c1;
				}
				else
				{
					if (c2.getCls().equals(String.class) || c1.getCls().equals(String.class))
					{
						return new CCType(String.class, "String");
					}
					else
					{
						Class<?> nmc1 = TypeCheckHelper.NormalizeClass(c1.getCls());
						Class<?> nmc2 = TypeCheckHelper.NormalizeClass(c2.getCls());
						if (nmc1 != nmc2)
						{
							throw new TypeConflictException("Arith optr two types not handled.");
						}
						return c1;
					}
				}
			}
		case AssignOptr:
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
			else
			{
				if (c1.isAssignableFrom(c2))
				{
					return c2;
				}
				else
				{
					throw new TypeConflictException(c1 + " and " + c2 + " can not be inter casted. Wrong assign optr.");
				}
			}
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
	
	public static TypeComputationKind ComputeKindFromRawString(String optr) throws CodeSynthesisException
	{
		switch (optr) {
		case ">":
		case "<":
		case "==":
		case "<=":
		case ">=":
		case "!=":
			return TypeComputationKind.BooleanRTwoSideSame;
		case "&&":
		case "||":
			return TypeComputationKind.BooleanRTwoSideSameBoolean;
		case "!":
		case "~":
			return TypeComputationKind.BooleanROnlyOneBoolean;
		case "++":
		case "--":
			return TypeComputationKind.NumberBitROnlyOneNumberBit;
		case "*":
		case "/":
		case "&":
		case "|":
		case "^":
		case "%":
			return TypeComputationKind.NumberBitRTwoSideSameNumberBit;
		case "+":
			return TypeComputationKind.StringNumberBitRTwoSideSameNumberBitOrOneString;
		case "-":
			return TypeComputationKind.NumberBitROneOrTwoSideSameNumberBit;
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
			return TypeComputationKind.InheritLeftOrRightTwoSameSide;
		case "<<=":
		case ">>=":
		case ">>>=":
		case "<<":
		case ">>":
		case ">>>":
			return TypeComputationKind.InheritLeftRightNumbetBit;
		default:
			throw new CodeSynthesisException("Unknown optr:" + optr + ".");
		}
	}

	public static TypeComputationKind ChooseOne(TypeComputationKind oneafter, TypeComputationKind beforetwo) throws CodeSynthesisException {
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
	}
	
}