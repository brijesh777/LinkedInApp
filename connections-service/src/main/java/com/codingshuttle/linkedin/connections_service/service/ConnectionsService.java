package com.codingshuttle.linkedin.connections_service.service;

import com.codingshuttle.linkedin.connections_service.auth.UserContextHolder;
import com.codingshuttle.linkedin.connections_service.entity.Person;
import com.codingshuttle.linkedin.connections_service.event.AcceptConnectionRequestEvent;
import com.codingshuttle.linkedin.connections_service.event.SendConnectionRequestEvent;
import com.codingshuttle.linkedin.connections_service.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class ConnectionsService {

    private final PersonRepository personRepository;
    private final KafkaTemplate<Long, SendConnectionRequestEvent> sendRequestkafkaTemplate;
    private final KafkaTemplate<Long, AcceptConnectionRequestEvent> acceptRequestkafkaTemplate;

    public List<Person> getFirstdegreeConnections() {
        Long userId = UserContextHolder.getCurrentUserId();

        log.info("Getting first degree connections for user with id: {}", userId);
        return personRepository.getFirstDegreeConnections(userId);
    }

    public Boolean sendConnectionRequest(Long receiverId) {
        Long senderId = UserContextHolder.getCurrentUserId();
        log.info("Trying to send connection request for user with senderId: {}, receiverId:{}", senderId, receiverId);
        boolean alreadySentRequest = personRepository.connectionRequestExists(senderId, receiverId);
        if (alreadySentRequest) {
            throw new RuntimeException("Connection request already exists");
        }

        if (senderId.equals(receiverId)) {
            throw new RuntimeException("Sender and Receiver Ids are the same");
        }

        boolean alreadyConnected = personRepository.alreadyConnected(senderId, receiverId);
        if (alreadyConnected) {
            throw new RuntimeException("Already connected users cannot add connection request");
        }


        personRepository.addConnectionRequest(senderId, receiverId);
        log.info("Successfully added connection request for user with id: {}", senderId);

        SendConnectionRequestEvent sendConnectionRequestEvent = SendConnectionRequestEvent.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .build();
        sendRequestkafkaTemplate.send("send-connection-request", sendConnectionRequestEvent);


        return true;

    }

    public Boolean acceptConnection(Long senderId) {
        Long receiverId = UserContextHolder.getCurrentUserId();
        boolean connectionRequestExists = personRepository.connectionRequestExists(senderId, receiverId);
        if (!connectionRequestExists) {
            throw new RuntimeException("NO Connection request to accept");
        }

        personRepository.acceptConnectionRequest(senderId, receiverId);
        log.info("Successfully accepted connection request for user with id: {}", senderId);

        AcceptConnectionRequestEvent acceptConnectionRequestEvent = AcceptConnectionRequestEvent.builder()
                .senderId(senderId)
                .receiverId(receiverId)
                .build();
        acceptRequestkafkaTemplate.send("accept-connection-request", acceptConnectionRequestEvent);
        return true;
    }

    public Boolean rejectConnection(Long senderId) {
        Long receiverId = UserContextHolder.getCurrentUserId();
        boolean connectionRequestExists = personRepository.connectionRequestExists(senderId, receiverId);
        if (!connectionRequestExists) {
            throw new RuntimeException("NO Connection request exist to delete");
        }

        personRepository.rejectConnectionRequest(senderId, receiverId);
        return true;
    }


}
