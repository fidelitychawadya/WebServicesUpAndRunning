package ch01.soap;

import com.sun.xml.internal.ws.api.model.wsdl.WSDLOutput;
import com.sun.xml.internal.ws.resources.SoapMessages;

import javax.xml.soap.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Iterator;

//A demonstration of Java's Soap API
public class DemoSoap {
    private static final String LocalName = "TimeRequest";
    private static final String NameSpace = "http://ch01/mysoap/";
    private static final String NameSpacePrefix = "ms";

    private ByteArrayOutputStream out;
    private ByteArrayInputStream in;

    public static void main(String[] args) {
        new DemoSoap().request();
    }

    private void request() {
        try {
            //Build a SOAP message to send to an output stream
            SOAPMessage message = create_soap_message();
            //Inject the appropriate information into the message
            //In this case, only the (optional) message header is used
            //and the body is empty

            SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
            SOAPHeader header = envelope.getHeader();
            Name lookup_name = create_qname(message);
            header.addHeaderElement(lookup_name).addTextNode("time");

            //simulate sending the SOAP message to a remote system by writing it to a
            //ByteArrayOutputStream
            out = new ByteArrayOutputStream();
            message.writeTo(out);
            trace("The sent SOAP message:", message);

            SOAPMessage response = process_request();
            extract_contents_and_print(response);
        } catch (SOAPException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }

    }

    private SOAPMessage process_request() {
        process_incoming_soap();
        coordinate_streams();
        return create_soap_message(in);
    }

    private void process_incoming_soap() {
        try {
            //Copy output stream to input stream to simulate
            //coordinated streams over a network
            coordinate_streams();
            //create the "received" SOAP message from the input stream
            SOAPMessage message = create_soap_message(in);
            //inspect the SOAP header for the keyword 'time_request'
            //and process the request if the keyword occurs
            Name lookup_name = create_qname(message);
            SOAPHeader header = message.getSOAPHeader();
            Iterator iterator = header.getChildElements(lookup_name);
            Node next = (Node) iterator.next();
            String value = (next == null) ? "Error!" : next.getValue();
            //if SOAP message contains request for the time, create a new SOAP
            //message with the current time in the body.
            if (value.toLowerCase().contains("time_request")) {
                //extracxt the body and add the current time as element.
                String now = new Date().toString();
                SOAPBody body = message.getSOAPBody();

                body.addBodyElement(lookup_name).addTextNode(now);
                message.saveChanges();

                //Write to the output stream.
                message.writeTo(out);
                trace("The received /processed SOAP message:", message);
            }
        } catch (SOAPException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private void extract_contents_and_print(SOAPMessage message) {
        try {
            SOAPBody body = message.getSOAPBody();

            Name lookup_name = create_qname(message);
            Iterator iterator = body.getChildElements(lookup_name);
            Node next = (Node) iterator.next();

            String value = (next == null) ? "Error!" : next.getValue();
            System.out.println("\n\nReturned from server:" + value);
        } catch (SOAPException e) {
            System.err.println(e);
        }
    }

    private SOAPMessage create_soap_message() {
        SOAPMessage message = null;
        try {
            MessageFactory messageFactory = MessageFactory.newInstance();
            message = messageFactory.createMessage();
        } catch (SOAPException e) {
            System.err.println(e);
        }
        return message;
    }

    private SOAPMessage create_soap_message(InputStream in) {
        SOAPMessage message = null;
        try {
            MessageFactory messageFactory = MessageFactory.newInstance();
            message = messageFactory.createMessage(null, in);
        } catch (SOAPException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
        return message;
    }

    private Name create_qname(SOAPMessage message) {
        Name name = null;
        try {
            SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
            name = envelope.createName(LocalName, NameSpacePrefix, NameSpace);
        } catch (SOAPException e) {
            System.err.println(e);
        }
        return name;
    }

    private void trace(String s, SOAPMessage message) {
        System.out.println("\n");
        System.out.println(s);
        try {
            message.writeTo(System.out);
        } catch (SOAPException e) {
            System.err.println(e);
        } catch (IOException e) {
            System.err.println(e);
        }
    }

    private void coordinate_streams() {
        in = new ByteArrayInputStream(out.toByteArray());
        out.reset();
    }
}
