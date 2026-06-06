package com.codingshuttle.linkedin.notification_service.consumer;

import com.codingshuttle.linkedin.connections_service.event.AcceptConnectionRequestEvent;
import com.codingshuttle.linkedin.connections_service.event.SendConnectionRequestEvent;
import com.codingshuttle.linkedin.notification_service.service.SendNotification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConnectionsServiceConsumer {

    private final SendNotification sendNotification;

    @KafkaListener(topics = "send-connection-request-topic")
    public void handleSendConnectionRequest(SendConnectionRequestEvent sendConnectionRequestEvent) {
        log.info("Handle Connections: Handle  handleSendConnectionRequest");
        String message = "You hve received a connection request from user with id: %d" + sendConnectionRequestEvent.getSenderId();
        sendNotification.send(sendConnectionRequestEvent.getReceiverId(), message);
    }

    @KafkaListener(topics = "accept-connections-request-topic")
    public void handleAcceptConnectionsRequest(AcceptConnectionRequestEvent acceptConnectionRequestEvent) {
        log.info("Handle Connections: Handle  handleAcceptConnectionsRequest");
        String message = "Your connection request has been accepted by the user with id: %d" + acceptConnectionRequestEvent.getSenderId();
        sendNotification.send(acceptConnectionRequestEvent.getSenderId(), message);
    }
}
