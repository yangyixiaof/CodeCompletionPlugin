package cn.yyx.contentassist.codesynthesis.typeutil;

public enum TypeComputationKind {
	NotSureOptr,
	NoOptr,
	JudgeOptr,
	ArithOptr,
	CastOptr,
	AssignOptr,
	LeftOptr, // <<
	RightOptr,// >>
	DirectUniqueUseFirstTypeOptr,
	DirectUniqueUseSecondTypeOptr,
	Unknown;
}
