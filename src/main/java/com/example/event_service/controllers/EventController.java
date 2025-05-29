package com.example.event_service.controllers;

import com.example.event_service.DTO.BoardGameDTO;
import com.example.event_service.DTO.EventBoardGameDTO;
import com.example.event_service.clients.BoardGameClient;
import com.example.event_service.exceptions.EventNotFoundException;
import com.example.event_service.models.Event;
import com.example.event_service.services.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventController {
    @Autowired
    EventService eventService;

    @Autowired
    BoardGameClient boardGameClient;


    @GetMapping("/board-game/{boardGameId}")
    public ResponseEntity<?> getEventsByBoardGameId(@PathVariable @Valid Long boardGameId) {
        try {
            List<Event> foundEvents = eventService.findEventsByBoardGameId(boardGameId);
            BoardGameDTO foundBoardGame = boardGameClient.getBoardGameById(boardGameId);
            List<EventBoardGameDTO> events = new ArrayList<>();
            for(Event eachEvent: foundEvents) {
               events.add( new EventBoardGameDTO(eachEvent, foundBoardGame));
            }
            return new ResponseEntity<>(events, HttpStatus.OK);
        } catch (EventNotFoundException e) {
            return new ResponseEntity<>( e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getEventById(@PathVariable Long id) {
        try {
            Event foundEvent = eventService.findEventById(id);
            BoardGameDTO foundBoardGame = boardGameClient.getBoardGameById(foundEvent.getBoardGameId());
            EventBoardGameDTO eventBoardGameDTO = new EventBoardGameDTO(foundEvent, foundBoardGame);
            return new ResponseEntity<>(eventBoardGameDTO, HttpStatus.OK);
        } catch (EventNotFoundException e) {
            return new ResponseEntity<>( e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping
   @ResponseStatus(HttpStatus.OK)

    public List<Event> findAllEvents() {
        return eventService.findAll();
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEventById(@PathVariable Long id) {
        try {
            eventService.deleteEventById(id);
        } catch (EventNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Event createEvent(@RequestBody Event event) {
        BoardGameDTO foundBoardGame = boardGameClient.getBoardGameById(event.getBoardGameId());
        if (foundBoardGame != null) {
            return eventService.saveEvent(event);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board game not found for ID: " + event.getBoardGameId());
        }

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Event updateEvent(@PathVariable Long id, @RequestBody @Valid Event event) {
        BoardGameDTO foundBoardGame = boardGameClient.getBoardGameById(event.getBoardGameId());
        if (foundBoardGame != null) {
            try {
                return eventService.updateEvent(id, event);
            } catch (EventNotFoundException e) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Board game not found for ID: " + event.getBoardGameId());
        }
    }
}
