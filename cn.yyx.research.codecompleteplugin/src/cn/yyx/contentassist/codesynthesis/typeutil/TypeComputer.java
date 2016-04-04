package cn.yyx.contentassist.codesynthesis.typeutil;

import cn.yyx.contentassist.codepredict.CodeSynthesisException;

public class TypeComputer {
	
	public static Class<?> ComputeType(Class<?> c1, Class<?> c2, TypeComputationKind tck) throws TypeConflictException
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
			if (c1 == null || c2 == null)
			{
				return null;
			}
			throw new TypeConflictException("This Noptr but two class all have resolved class.");
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
			return Boolean.class;
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
					if (c2.equals(String.class) || c1.equals(String.class))
					{
						return String.class;
					}
					else
					{
						if (c2.equals(Double.class) || c1.equals(Double.class))
						{
							return Double.class;
						}
						if (c2.equals(Float.class) || c1.equals(Float.class))
						{
							return Float.class;
						}
						if (c2.equals(Long.class) || c1.equals(Long.class))
						{
							return Long.class;
						}
						if (c2.equals(Integer.class) || c1.equals(Integer.class))
						{
							return Integer.class;
						}
						if (c2.equals(Short.class) || c1.equals(Short.class))
						{
							return Short.class;
						}
						if (c2.equals(Byte.class) || c1.equals(Byte.class))
						{
							return Byte.class;
						}
						if (c2.equals(Boolean.class) || c1.equals(Boolean.class))
						{
							return Boolean.class;
						}
						return c1;
					}
				}
			}
		case CastOptr:
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
				throw new TypeConflictException(c1 + " can not be casted to " + c2 + ". Wrong cast optr.");
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
		case Unknown:
			throw new TypeConflictException("Unknown optr.");
		case LeftOptr:
		case RightOptr:
			return c1;
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
		case "!":
		case "~":
		case "==":
		case "<=":
		case ">=":
		case "!=":
		case "&&":
		case "||":
			return TypeComputationKind.JudgeOptr;
		case "++":
		case "--":
		case "+":
		case "-":
		case "*":
		case "/":
		case "&":
		case "|":
		case "^":
		case "%":
			return TypeComputationKind.ArithOptr;
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
		case "<<=":
		case ">>=":
		case ">>>=":
			return TypeComputationKind.AssignOptr;
		case "<<":
			return TypeComputationKind.LeftOptr;
		case ">>":
		case ">>>":
			return TypeComputationKind.RightOptr;
		default:
			throw new CodeSynthesisException("Unknown optr:" + optr + ".");
		}
	}
	
}
