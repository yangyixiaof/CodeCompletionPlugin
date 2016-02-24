package cn.yyx.research.language.JDTManager;

public class ReferenceHintLibrary {
	
	public static final int MaskLength = 5;
	public static final int Mask = (1 << 6) - 1;
	
	public static final int Declare = 1 << 1;
	public static final int Update = 1 << 2;
	public static final int Use = 1 << 3;
	
	public static final int Field = 1 << 6;
	public static final int Data = 1 << 7;
	public static final int Label = 1 << 8;
	public static final int Class = 1 << 9;
	
	public static final int NoHint = 0;
	public static final int FieldDeclare = Field | Declare;
	public static final int FieldUpdate = Field | Update;
	public static final int FieldUse = Field | Use;
	public static final int DataDeclare = Data | Declare;
	public static final int DataUpdate = Data | Update;
	public static final int DataUse = Data | Use;
	public static final int LabelDeclare = Label | Declare;
	public static final int LabelUse = Label | Use;
	
	public static ReferenceHint ParseReferenceHint(int overAllType)
	{
		if (overAllType == NoHint)
		{
			return null;
		}
		int wayUse = (overAllType & Mask);
		int dataType = ((overAllType >> MaskLength) & Mask);
		return new ReferenceHint(dataType << MaskLength, wayUse);
	}
	
	public static Integer ChangeHintHighByteToField(Integer overAllType)
	{
		if (overAllType == null)
		{
			return ReferenceHintLibrary.NoHint;
		}
		ReferenceHint hint = ParseReferenceHint(overAllType);
		if (hint == null)
		{
			return ReferenceHintLibrary.NoHint;
		}
		hint.setDataType(Field);
		return hint.GetOverAllHint();
	}
	
}