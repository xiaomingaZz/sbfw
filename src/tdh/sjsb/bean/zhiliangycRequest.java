package tdh.sjsb.bean;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import tdh.util.CenterUtils;

@XStreamAlias("root")
public class zhiliangycRequest {
	private String fydm;
	private String lx;
	public final String getFydm() {
		return fydm;
	}
	public final void setLX(String lx) {
		this.lx = lx;
	}
	public final String getLx() {
		return lx;
	}
	public final void setLx(String lx) {
		this.lx = lx;
	}
	
}
