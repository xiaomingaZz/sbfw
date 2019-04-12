package tdh.sjsb;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC, use = SOAPBinding.Use.ENCODED, parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface zhiliangycService {
	
	
	@WebMethod(operationName="zlgzYcList")
	public String zlgzYcList(@WebParam(name = "xml") String xml);
}
