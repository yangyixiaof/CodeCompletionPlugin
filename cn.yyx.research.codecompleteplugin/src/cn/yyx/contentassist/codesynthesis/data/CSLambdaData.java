package cn.yyx.contentassist.codesynthesis.data;

public class CSLambdaData extends CSFlowLineData {
	
	private String[] declares = null;
	
	public CSLambdaData(String[] declares, CSFlowLineData fld) {
		super(fld.getId(), fld.getSete(), fld.getData(), fld.getDcls(), fld.getTck(), fld.getHandler());
		if (fld.isHashole())
		{
			this.setHashole(true);
		}
		this.setDeclares(declares);
		this.setCsep(fld.getCsep());
		this.setCsep(new CSLambdaProperty(null));
		this.setScm(fld.getSynthesisCodeManager());
		this.setExtraData(fld.getExtraData());
	}

	public String[] getDeclares() {
		return declares;
	}

	public void setDeclares(String[] declares) {
		this.declares = declares;
	}
	
}