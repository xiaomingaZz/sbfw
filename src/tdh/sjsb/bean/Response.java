package tdh.sjsb.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import tdh.util.CenterUtils;

@XStreamAlias("result")
public class Response {
	/**
	 * 返回代码
	 */
	private String flag;
	/**
	 * 消息内容
	 */
	private String message;
	public final String getFlag() {
		return flag;
	}
	public final void setFlag(String flag) {
		this.flag = flag;
	}
	public final String getMessage() {
		return message;
	}
	public final void setMessage(String message) {
		this.message = message;
	}
}
