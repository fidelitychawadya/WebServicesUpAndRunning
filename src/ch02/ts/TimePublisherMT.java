package ch02.ts;

import javax.xml.ws.Endpoint;

public class TimePublisherMT {
    private Endpoint endpoint;
    public static void main(String[] args){
        TimePublisherMT self = new TimePublisherMT();
        self.createEndpoint();
        self.configureEndpoint();
        self.publish();
    }

    private void createEndpoint(){
        endpoint = Endpoint.create(new TimeServerImpl());
    }

    private void configureEndpoint(){
        endpoint.setExecutor(new MyThreadPool());
    }

    private void publish(){
        int port = 8888;
        String url = "http://localhost:" + port + "/ts";
        endpoint.publish(url);
        System.out.println("Publishing TimeServer on port" + port);
    }
}
