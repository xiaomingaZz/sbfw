package tdh.sjsb.bean;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import tdh.util.CenterUtils;

@XStreamAlias("laxq")
public class LaxqInfo {
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
	
	@XStreamAlias("lalist")
	@XStreamOmitField
	private List<LaajInfo> lalist;
	
	public final List<LaajInfo> getLalist() {
		return lalist;
	}
	public final void setLalist(List<LaajInfo> lalist) {
		this.lalist = lalist;
	}
	
	
}
