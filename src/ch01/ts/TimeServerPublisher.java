package ch01.ts;

import javax.xml.ws.Endpoint;

/**
 * This application publishes the web service whose SIB is
 * ch01.ts.TimeSeverImpl.
 * For now the service is published at network address 127.0.0.1
 * which is localhost and at port 9876 as this port is likely available
 * on any desktop machine.
 * The publication path is /ts, an arbitrary name.
 * <p>
 * THE endpoint class has an overloaded publish menthod
 * In this two-argument version, the first argument is the publication
 * URL as a string and the second argument is an instance of the service
 * SIB, in this case ch01.ts.TimeServerImpl
 * The application runs indefinitely, awaiting service requests.
 * It needs to be termimated at the command prompt with control-C or
 * the equivalent.
 * Once the application is started, open a browser to the URL
 * <p>
 * http://127.0.0.1/ts?wsdl  to view the service contract, the WSDL
 * document.
 * This is an easy test to determi e whether the service has deployed successfully
 * If the test succeeds, a client then can be executed against the service
 */
public class TimeServerPublisher {

    public static void main(String[] args) {
        //First argument is the publication URL
        // Second argument is an SIB instance
        Endpoint.publish("http://127.0.0.1:9876/ts", new TimeServerImpl());
    }
}
