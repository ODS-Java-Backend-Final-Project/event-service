package com.example.event_service.services;

import com.example.event_service.exceptions.EventNotFoundException;
import com.example.event_service.models.EStatus;
import com.example.event_service.models.Event;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class EventServiceTest {

    @Autowired
    EventService eventService;

    @Test
    @DisplayName("We received the right event by ID")
    public void testFindEventById() {
        Event foundEvent = eventService.findEventById(2L);
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
        LocalDateTime eventDate = LocalDateTime.of(2025, 10, 1, 18, 0);
        List<String> attendees = List.of("Alice", "Bob");
        Event newEvent = new Event("Tarde de juegos", "Un ratos divertido jugando juegos de mesa con perritos", eventDate, "Dog Cafe", "Angela", EStatus.ON_GOING, attendees,
                1L);
        Event savedEvent = eventService.saveEvent(newEvent);

        assertNotNull(savedEvent);
        assertEquals("Tarde de juegos", savedEvent.getName());
        assertEquals("Un ratos divertido jugando juegos de mesa con perritos", savedEvent.getDescription());
        assertEquals(eventDate, savedEvent.getDate());
        assertEquals("Dog Cafe", savedEvent.getLocation());
        assertEquals("Angela", savedEvent.getOrganizer());
    }

    @Test
    @DisplayName("We update an existing event and check if it is updated properly")
    public void testUpdateEvent() {
        LocalDateTime eventDate = LocalDateTime.of(2025, 10, 1, 18, 0);
        List<String> attendees = List.of("Alice", "Bob");
        Event updatedEvent = new Event("Tarde de juegos", "Un ratos divertido jugando juegos de mesa con perritos", eventDate, "Dog Cafe", "Angela", EStatus.ON_GOING, attendees,
                1L );
        Event changedEvent = eventService.updateEvent(11L,updatedEvent);

        assertNotNull(changedEvent);
        assertEquals("Tarde de juegos", changedEvent.getName());
        assertEquals("Un ratos divertido jugando juegos de mesa con perritos", changedEvent.getDescription());
        assertEquals(eventDate, changedEvent.getDate());
        assertEquals("Dog Cafe", changedEvent.getLocation());
        assertEquals("Angela", changedEvent.getOrganizer());
    }

    @Test
    @DisplayName("We delete an event by ID and check if it is deleted properly")

    public void testDeleteEventById() {
        eventService.deleteEventById(11L);
        assertThrows(EventNotFoundException.class, () -> eventService.findEventById(3L));
    }

    @Test
    @DisplayName("We find all events and check if they are returned properly")
    public void testFindAllEvents() {
        List<Event> foundEvents = eventService.findAll();
        assertNotNull(foundEvents);
    }
}
