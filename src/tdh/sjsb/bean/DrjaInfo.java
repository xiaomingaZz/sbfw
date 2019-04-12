package tdh.sjsb.bean;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;

import tdh.util.CenterUtils;

@XStreamAlias("drja")
public class DrjaInfo {
	private String zs;
	public final String getZs() {
		return zs;
	}
	public final void setZs(String zs) {
		this.zs = zs;
	}
	@XStreamImplicit
	private List<JaxqInfo> jaxq;
	
	public final List<JaxqInfo> getJaxq() {
		return jaxq;
	}
	public final void setJaxq(List<JaxqInfo> jaxq) {
		this.jaxq = jaxq;
	}
}
