package tdh.sjsb;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService(targetNamespace="http://webservice.spdt.thunisoft.com/")
@SOAPBinding(style = SOAPBinding.Style.RPC, use = SOAPBinding.Use.ENCODED, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface DataSbService {
	@WebMethod(operationName="importSpdtAndAjList")
	public String importSpdtAndAjList(@WebParam(name = "xmlSpdt") String xmlSpdt);
	
	@WebMethod(operationName="importSpdtAndAjList15")
	public String importSpdtAndAjList15(@WebParam(name = "xmlSpdt") String xmlSpdt);
	
	@WebMethod(operationName="importSpdt")
	public String importSpdt(@WebParam(name = "arg0") String xmlSpdt);
	
	@WebMethod(operationName="importWjList")
	public String importWjList(@WebParam(name = "xml") String xml);
}
