package tdh.sjsb.bean;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import tdh.util.CenterUtils;

@XStreamAlias("root")
public class AjRequest {
	private String tjsj;
	private String gyid;
	public final String getTjsj() {
		return tjsj;
	}
	public final void setTjsj(String tjsj) {
		this.tjsj = tjsj;
	}
	public final String getGyid() {
		return gyid;
	}
	public final void setGyid(String gyid) {
		this.gyid = gyid;
	}
	@XStreamAlias("datas")
	private List<XqInfo> datas;
	public final List<XqInfo> getData() {
		return datas;
	}
	public final void setData(List<XqInfo> datas) {
		this.datas = datas;
	}
}
