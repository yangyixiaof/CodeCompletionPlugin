package cn.yyx.contentassist.codesynthesis.data;

import java.util.List;

public class CSArgTypeListData extends CSFlowLineData {
	
	private List<String> tpandnames = null;
	
	public CSArgTypeListData(List<String> tpandnamespara, CSFlowLineData cd) {
		super(cd.getId(), cd.getSete(), cd.getData(), cd.getDcls(), cd.getTck(),
				cd.getHandler());
		if (cd.isHashole())
		{
			this.setHashole(true);
		}
		this.setCsep(cd.getCsep());
		this.setScm(cd.getSynthesisCodeManager());
		this.setExtraData(cd.getExtraData());
		this.setTpandnames(tpandnamespara);
	}

	public List<String> getTpandnames() {
		return tpandnames;
	}

	public void setTpandnames(List<String> tpandnames) {
		this.tpandnames = tpandnames;
	}
	
}