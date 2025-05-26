package com.example.event_service.services;

import com.example.event_service.exceptions.EventNotFoundException;
import com.example.event_service.models.Event;
import com.example.event_service.repositories.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
