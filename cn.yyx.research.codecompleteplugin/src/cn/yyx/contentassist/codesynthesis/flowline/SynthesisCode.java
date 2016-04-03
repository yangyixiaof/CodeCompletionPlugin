package cn.yyx.contentassist.codesynthesis.flowline;

public class SynthesisCode {
	
	private String code = null;
	private boolean valid = true;
	
	public SynthesisCode(String code, boolean valid)
	{
		this.setCode(code);
		this.setValid(valid);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}
	
}