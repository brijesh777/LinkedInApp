package com.codingshuttle.linkedin.connections_service.controller;

import com.codingshuttle.linkedin.connections_service.entity.Person;
import com.codingshuttle.linkedin.connections_service.service.ConnectionsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/core")
@RequiredArgsConstructor
public class ConnectionsController {

    private final ConnectionsService connectionsService;

    @GetMapping("/first-degree")
    public ResponseEntity<List<Person>> getFirstConnections(@RequestHeader("X-User-Id") Long userId) {
        return ResponseEntity.ok(connectionsService.getFirstdegreeConnections(userId));
    }


}
