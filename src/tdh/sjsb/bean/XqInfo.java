package tdh.sjsb.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import tdh.util.CenterUtils;

@XStreamAlias("data")
public class XqInfo {
	private String id;
	private DrxsInfo drxs;
	private DrjaInfo drja;
	public final String getId() {
		return id;
	}
	public final void setId(String id) {
		this.id = id;
	}

	public final DrxsInfo getDrxs() {
		return drxs;
	}
	public final void setDrxs(DrxsInfo drxs) {
		this.drxs = drxs;
	}
	public final DrjaInfo getDrja() {
		return drja;
	}
	public final void setDrja(DrjaInfo drja) {
		this.drja = drja;
	}
}
