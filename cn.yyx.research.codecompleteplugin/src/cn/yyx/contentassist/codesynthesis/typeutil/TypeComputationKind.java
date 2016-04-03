package cn.yyx.contentassist.codesynthesis.typeutil;

public enum TypeComputationKind {
	JudgeOptr,
	ArithOptr,
	CastOptr,
	AssignOptr,
	LeftOptr, // <<
	RightOptr,// >>
	Unknown;
}
