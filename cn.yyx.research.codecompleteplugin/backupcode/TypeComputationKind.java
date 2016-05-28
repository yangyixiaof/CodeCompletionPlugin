package cn.yyx.contentassist.codesynthesis.typeutil.computations;

public abstract class TypeComputationKind {
	/*NotSureOptr,
	NoOptr,
	JudgeOptr,
	ArithOptr,
	CastOptr,
	AssignOptr,
	LeftOptr, // <<
	RightOptr,// >>
	DirectUniqueUseFirstTypeOptr,
	DirectUniqueUseSecondTypeOptr,
	Unknown;*/
	NoOptr,
	BooleanRTwoSideSame,
	BooleanRTwoSideSameBoolean,
	BooleanROnlyOneBoolean,
	NumberBitROnlyOneNumberBit,
	NumberBitRTwoSideSameNumberBit,
	StringNumberBitRTwoSideSameNumberBitOrOneString,
	NumberBitROneOrTwoSideSameNumberBit,
	InheritLeftOrRightTwoSameSide,
	InheritLeftRightNumbetBit,
	DirectUniqueUseFirstTypeOptr,
	DirectUniqueUseSecondTypeOptr,
	LeftOrRightCast;
}
