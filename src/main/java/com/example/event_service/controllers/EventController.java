package com.example.event_service.controllers;

import com.example.event_service.exceptions.EventNotFoundException;
import com.example.event_service.models.Event;
import com.example.event_service.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    EventService eventService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id) {
        try {
            Event foundEvent = eventService.findEventById(id);
            return new ResponseEntity<>(foundEvent, HttpStatus.OK);
        } catch (EventNotFoundException e) {
            return new ResponseEntity<>( e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
