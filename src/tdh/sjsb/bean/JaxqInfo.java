package tdh.sjsb.bean;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import tdh.util.CenterUtils;

@XStreamAlias("jaxq")
public class JaxqInfo {
	private String ajlx;
	private String sl;
	public final String getAjlx() {
		return ajlx;
	}
	public final void setAjlx(String ajlx) {
		this.ajlx = ajlx;
	}
	public final String getSl() {
		return sl;
	}
	public final void setSl(String sl) {
		this.sl = sl;
	}
	
	@XStreamAlias("jalist")
	@XStreamOmitField
	private List<JaajInfo> jalist;
	
	public final List<JaajInfo> getJalist() {
		return jalist;
	}
	public final void setJalist(List<JaajInfo> jalist) {
		this.jalist = jalist;
	}
	
	
}
