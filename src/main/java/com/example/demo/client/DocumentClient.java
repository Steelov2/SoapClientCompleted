package com.example.demo.client;

import com.example.consumingwebservice.wsdl.StoreDocumentRequest;
import com.example.consumingwebservice.wsdl.StoreDocumentResponse;

import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

public class DocumentClient extends WebServiceGatewaySupport {
    public StoreDocumentResponse getDocument(byte [] message) {
        StoreDocumentRequest request = new StoreDocumentRequest();
        request.setDocument(message);
        StoreDocumentResponse response =(StoreDocumentResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/ws/document", request,
                        new SoapActionCallback(
                                "http://spring.io/guides/gs-producing-web-service/StoreDocumentRequest"));
        return response;
    }
}
