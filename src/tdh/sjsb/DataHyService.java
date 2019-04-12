package tdh.sjsb;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(targetNamespace="webservice.zb.ywk.xxq.thunisoft.com")
@SOAPBinding(style = SOAPBinding.Style.RPC, use = SOAPBinding.Use.ENCODED, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface DataHyService {
	
	@WebMethod(operationName="importSpdt")
	public String importSpdt(String spdtXml);

	@WebMethod(operationName="importSpdtAndAjList")
	public String importSpdtAndAjList(String spdtXml);
	
}
