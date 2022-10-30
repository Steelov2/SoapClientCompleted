package com.example.demo.client;

import com.example.consumingwebservice.wsdl.StoreDocumentRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import com.example.consumingwebservice.wsdl.StoreDocumentResponse;

import java.util.Arrays;

public class DocumentClient extends WebServiceGatewaySupport {
    private static final Logger log = LoggerFactory.getLogger(DocumentClient.class);
    public StoreDocumentResponse getDocument(byte [] message) {
        StoreDocumentRequest request = new StoreDocumentRequest();
        request.setDocument(message);
        log.info("Requesting location for " + Arrays.toString(message));

        StoreDocumentResponse response =(StoreDocumentResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:8080/ws/document", request,
                        new SoapActionCallback(
                                "http://spring.io/guides/gs-producing-web-service/StoreDocumentRequest"));
        return response;
    }
}
