package com.example.event_service.services;

import com.example.event_service.DTO.EventBoardGameDTO;
import com.example.event_service.exceptions.EventNotFoundException;
import com.example.event_service.models.Event;
import com.example.event_service.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class EventService {
    @Autowired
    EventRepository eventRepository;

    public Event findEventById(Long id) throws  EventNotFoundException {
        Optional<Event> foundEvent = eventRepository.findById(id);
        if (foundEvent.isPresent()) {
           return foundEvent.get();

        } else {
            throw new EventNotFoundException("Event with ID " + id + " not found.");
        }
    }


    public List <Event> findEventsByBoardGameId( Long boardGameId) {
        List<Event> foundEvents = eventRepository.findByBoardGameId(boardGameId);
        if (foundEvents.isEmpty()) {
            throw new EventNotFoundException("No events found for this board game");
        }
        return foundEvents;
    }


    public Event saveEvent(Event event) {
        return eventRepository.save(event);
    }

    public void deleteEventById(Long id) throws EventNotFoundException {
        Optional<Event> foundEvent = eventRepository.findById(id);
        if (foundEvent.isPresent()) {
            eventRepository.deleteById(id);
        } else {
            throw new EventNotFoundException("Event with ID " + id + " not found.");
        }
    }

    public Event updateEvent(Long id, Event event) throws EventNotFoundException {
        Event existingEvent = eventRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Event with ID " + id + " not found."));
        existingEvent.setName(event.getName());
        existingEvent.setDescription(event.getDescription());
        existingEvent.setDate(event.getDate());
        existingEvent.setLocation(event.getLocation());
        existingEvent.setOrganizer(event.getOrganizer());
        existingEvent.setAttendee(event.getAttendee());
        existingEvent.setStatus(event.getStatus());
        existingEvent.setBoardGameId(event.getBoardGameId());

        return eventRepository.save(existingEvent);
    }

    public List<Event> findAll() {
        return eventRepository.findAll();
    }

}
