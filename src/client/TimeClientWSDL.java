package client;

public class TimeClientWSDL {
    public static void main(String[] args){
        //The TimeServerImplService class is the Java type bound to
        //the service section of the WSDL document.
        TimeServerImplService service = new TimeServerImplService();
        //The TimeServer interface is the Java type bound to
        // the portType section of the wsdl document
        TimeServer eif = service.getTimeServerImplPort();
        //invoke the methods
        System.out.println(eif.getTimeAsString());
        System.out.println(eif.getTimeAsElapsed());

    }
}
