package com.example.event_service.services;

import com.example.event_service.exceptions.EventNotFoundException;
import com.example.event_service.models.Event;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EventServiceTest {

    @Autowired
    EventService eventService;

    @Test
    @DisplayName("We received the right event by ID")
    public void testFindEventById() {
        Event foundEvent = eventService.findEventById(1L);
        assertNotNull(foundEvent);
    }

    @Test
    @DisplayName("we received an exception when trying to find an event by non-existing ID")
    public void testFindEventByIdException() {
        assertThrows(EventNotFoundException.class, () -> eventService.findEventById(99L));
    }

    @Test
    @DisplayName("We save a new event and check if it is saved properly")
    public void testSaveEvent() {
        Event newEvent = new Event("Tarde de juegos", "Un ratos divertido jugando juegos de mesa con perritos", "2025-10-01", "Dog Cafe", "Angela");
        Event savedEvent = eventService.saveEvent(newEvent);

        assertNotNull(savedEvent);
        assertEquals("Tarde de juegos", savedEvent.getName());
        assertEquals("Un ratos divertido jugando juegos de mesa con perritos", savedEvent.getDescription());
        assertEquals("2025-10-01", savedEvent.getDate());
        assertEquals("Dog Cafe", savedEvent.getLocation());
        assertEquals("Angela", savedEvent.getOrganizer());
    }
}
