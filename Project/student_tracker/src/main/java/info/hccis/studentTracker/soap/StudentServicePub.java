package info.hccis.studentTracker.soap;

import javax.xml.ws.Endpoint;

public class StudentServicePub {
    public static void main(String[] args) {
        System.out.println("Starting SOAP server...");

        StudentServiceImpl serviceImpl = new StudentServiceImpl();

        // Use Endpoint.create() and then publish
        Endpoint endpoint = Endpoint.create(serviceImpl);
        endpoint.publish("http://localhost:8083/StudentService");

        System.out.println("SOAP server running at: http://localhost:8083/StudentService?wsdl");
    }
}
