package com.codingshuttle.linkedin.connections_service.controller;

import com.codingshuttle.linkedin.connections_service.entity.Person;
import com.codingshuttle.linkedin.connections_service.service.ConnectionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/core")
@RequiredArgsConstructor
public class ConnectionsController {

    private final ConnectionsService connectionsService;

    @GetMapping("/first-degree")
    public ResponseEntity<List<Person>> getFirstConnections() {
        return ResponseEntity.ok(connectionsService.getFirstdegreeConnections());
    }

    @PostMapping("/request/{userId}")
    public ResponseEntity<Boolean> sendConnectionRequest(@PathVariable Long userId) {
        return ResponseEntity.ok(connectionsService.sendConnectionRequest(userId));
    }

    @PostMapping("/accept/{userId}")
    public ResponseEntity<Boolean> acceptConnection(@PathVariable Long userId) {
        return ResponseEntity.ok(connectionsService.acceptConnection(userId));
    }

    @PostMapping("/reject/{userId}")
    public ResponseEntity<Boolean> rejectConnection(@PathVariable Long userId) {
        return ResponseEntity.ok(connectionsService.rejectConnection(userId));
    }

}
