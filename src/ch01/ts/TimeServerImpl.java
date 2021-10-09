package ch01.ts;

import javax.jws.WebService;
import java.util.Date;

@WebService(endpointInterface = "ch01.ts.TimeServer")
public class TimeServerImpl implements TimeServer{
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
    @Override
    public String getTimeAsString() {
        return new Date().toString();
    }

    @Override
    public long getTimeAsElapsed() {
        return new Date().getTime();
    }
}
