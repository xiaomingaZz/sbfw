package tdh.sjsb;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC, use = SOAPBinding.Use.ENCODED, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface DataJgService {
	/**
	 * 用于和厂商反馈数据中心接收到的数据存在不满足规则的案件清单，便于厂商能够及时的获取数据问题和修正数据问题。
	 * @param xml
	 * @return
	 */
	@WebMethod(operationName="zlgzYcList")
	public String zlgzYcList	(@WebParam(name = "xml") String xml);
	
	/**
	 * 反馈每日数据上报生产库和中心库的差异，包括数量的不一致以及关键数据信息的不一致。
	 * @param xml
	 * @return
	 */
	@WebMethod(operationName="sjbdDayList")
	public String sjbdDayList(@WebParam(name = "xml") String xml);
	
	/**
	 * 通过数据中心监管平台主动要求第三方厂商将指定的案件重新上报到数据中心。
	 * 平台主动请求重报接口
	 * @param xml
	 * @return
	 */
	@WebMethod(operationName="ptCbList")
	public String ptCbList(@WebParam(name = "xml") String xml);
	
	/**
	 * 将上一个接口返回的需要重报的清单，进行重报后，将任务ID进行确认，是否已经重报。
	 * 确认已重报接口
	 * @param xml
	 * @return
	 */
	@WebMethod(operationName="ptCbQr")
	public String ptCbQr(@WebParam(name = "xml") String xml);
}
