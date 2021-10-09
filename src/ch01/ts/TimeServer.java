package ch01.ts;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public interface TimeServer {
    /**
     * The annotation @WebService signals that this is the SEI (Service
     * Endpoint Interface).
     *
     * @WebMethod indicates that each methos is a service operation.
     * @SOAPBinding impacts the under-the-hood construction of the service
     * contract,
     * The WSDL document.Style.RPC sinplifies the contract and makes deployment
     * easier
     */

    @WebMethod
    String getTimeAsString();

    @WebMethod
    long getTimeAsElapsed();

}
