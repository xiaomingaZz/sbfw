package tdh.sjsb.bean;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

public class AjRequest15 {
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
	private List<XqInfo15> datas;
	public final List<XqInfo15> getData() {
		return datas;
	}
	public final void setData(List<XqInfo15> datas) {
		this.datas = datas;
	}
}
