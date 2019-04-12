package tdh.sjsb.msg;

public class ParamMessage {
	public static final String XML_HEAD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
	
	public static final String MESSAGE_NOTNULL_TJSJ = "统计时间不能为空";
	public static final String MESSAGE_NOTNULL_GYID = "本省法院代码不能为空";
	public static final String MESSAGE_NOTNULL_FYID = "法院代码不能为空";
	public static final String MESSAGE_NOTNULL_XQID = "下辖法院或本院的法标代码不能为空";
	public static final String MESSAGE_NOTNULL_AJLX = "案件类型不能为空";
	public static final String MESSAGE_NOTNULL_SL = "当日新收案件数量不能为空";
	public static final String MESSAGE_NOTNULL_AJBS = "案件标识不能为空";
	public static final String MESSAGE_NOTNULL_AH = "案号不能为空";
	public static final String MESSAGE_NOTNULL_SLARQ = "收案与立案日期至少有一个不能为空";
	public static final String MESSAGE_NOTNULL_LAAY = "立案案由必填";
	public static final String MESSAGE_NOTNULL_JAFS = "结案方式不能为空";
	public static final String MESSAGE_NOTNULL_JARQ = "结案日期不能为空";
	public static final String MESSAGE_NOTNULL_JAAY = "结案案由不能为空";
	public static final String MESSAGE_NOTNULL_BSRQ = "报送日期不能为空";
	public static final String MESSAGE_NOTNULL_WJMC = "文件名称不能为空";
	public static final String MESSAGE_NOTNULL_BSSJ = "报送时间不能为空";
	
	public static final String MESSAGE_NOTNULL_DATA = "本省数据节点不能为空";
	public static final String MESSAGE_ERROR_PARAM = "请求参数不正确";
	public static final String MESSAGE_ERROR_DECODE = "解码错误";
	public static final String MESSAGE_ERROR_ENCODE = "加码错误";
	public static final String MESSAGE_ERROR_FYDM = "高法代码错误";
	public static final String MESSAGE_ERROR_XXQFYDM = "下辖法院代码错误";
	public static final String MESSAGE_ERROR_FORMAT = "XML格式代码错误";
}
