package cn.yyx.contentassist.codesynthesis.typeutil;

public enum TypeComputationKind {
	NoOptr,
	JudgeOptr,
	ArithOptr,
	CastOptr,
	AssignOptr,
	LeftOptr, // <<
	RightOptr,// >>
	Unknown;
}
