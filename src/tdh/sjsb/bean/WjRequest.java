package tdh.sjsb.bean;

import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import tdh.util.CenterUtils;

@XStreamAlias("root")
public class WjRequest {
	private String bsrq;
	private String gyid;
	@XStreamAlias("datas")
	private List<WjInfo> datas;
	
	public final List<WjInfo> getDatas() {
		return datas;
	}
	public final void setDatas(List<WjInfo> datas) {
		this.datas = datas;
	}
	
	public final String getBsrq() {
		return bsrq;
	}
	public final void setBsrq(String bsrq) {
		this.bsrq = bsrq;
	}
	public final String getGyid() {
		return gyid;
	}
	public final void setGyid(String gyid) {
		this.gyid = gyid;
	}
}
