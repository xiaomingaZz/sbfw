package tdh.sjsb.bean;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import tdh.util.CenterUtils;

@XStreamAlias("drxs")
public class DrxsInfo {
	private String zs;
	public final String getZs() {
		return zs;
	}
	public final void setZs(String zs) {
		this.zs = zs;
	}
	@XStreamImplicit
	private List<LaxqInfo> laxq;
	
	public final List<LaxqInfo> getLaxq() {
		return laxq;
	}
	public final void setLaxq(List<LaxqInfo> laxq) {
		this.laxq = laxq;
	}
	
	
}
