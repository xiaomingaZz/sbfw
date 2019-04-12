package tdh.sjsb.bean;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("data")
public class WjInfo {
	private String id;
	@XStreamAlias("ajlist")
	private List<AjwjInfo> ajlist;
	@XStreamAlias("jglist")
	private List<JgwjInfo> jglist;
	@XStreamAlias("dellist")
	private List<DelWjInfo> dellist;
	public final String getId() {
		return id;
	}
	public final void setId(String id) {
		this.id = id;
	}
	public final List<AjwjInfo> getAjlist() {
		return ajlist;
	}
	public final void setAjlist(List<AjwjInfo> ajlist) {
		this.ajlist = ajlist;
	}
	public final List<JgwjInfo> getJglist() {
		return jglist;
	}
	public final void setJglist(List<JgwjInfo> jglist) {
		this.jglist = jglist;
	}
	public final List<DelWjInfo> getDellist() {
		return dellist;
	}
	public final void setDellist(List<DelWjInfo> dellist) {
		this.dellist = dellist;
	}
	
	
}
