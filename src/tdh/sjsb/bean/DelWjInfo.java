package tdh.sjsb.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import tdh.util.CenterUtils;

@XStreamAlias("del")
public class DelWjInfo {
	private String wjmc;
	private String bssj;
	public final String getWjmc() {
		return wjmc;
	}
	public final void setWjmc(String wjmc) {
		this.wjmc = wjmc;
	}
	public final String getBssj() {
		return bssj;
	}
	public final void setBssj(String bssj) {
		this.bssj = bssj;
	}
}
