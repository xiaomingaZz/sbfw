package tdh.sjsb.bean;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamImplicit;
import com.thoughtworks.xstream.annotations.XStreamOmitField;

import tdh.util.CenterUtils;

public class XqInfo15 {
	private String id;
	private int drxsajsl;
	private int dryjajsl;
	private List<LaInfo> lalist;
	private List<JaInfo> jalist;

	public final String getId() {
		return id;
	}

	public final void setId(String id) {
		this.id = id;
	}

	public int getDrxsajsl() {
		return drxsajsl;
	}

	public void setDrxsajsl(int drxsajsl) {
		this.drxsajsl = drxsajsl;
	}

	public int getDryjajsl() {
		return dryjajsl;
	}

	public void setDryjajsl(int dryjajsl) {
		this.dryjajsl = dryjajsl;
	}

	public List<LaInfo> getLalist() {
		return lalist;
	}

	public void setLalist(List<LaInfo> lalist) {
		this.lalist = lalist;
	}

	public List<JaInfo> getJalist() {
		return jalist;
	}

	public void setJalist(List<JaInfo> jalist) {
		this.jalist = jalist;
	}

	@Override
	public String toString() {
		return "XqInfo15 [id=" + id + ", drxsajsl=" + drxsajsl + ", dryjajsl=" + dryjajsl + ", lalist=" + lalist
				+ ", jalist=" + jalist + "]";
	}
	
	
}
