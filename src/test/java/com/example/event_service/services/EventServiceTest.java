package com.example.event_service.services;

import com.example.event_service.exceptions.EventNotFoundException;
import com.example.event_service.models.EStatus;
import com.example.event_service.models.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

    private  Event testEvent;
    LocalDateTime eventDate = LocalDateTime.of(2025, 10, 1, 18, 0);
    List<String> attendees = List.of("Alice", "Bob");

    @BeforeEach
    public void  setUp () {
        Event newEvent = new Event();

        newEvent.setName("Tarde de juegos");
        newEvent.setDescription("Un ratos divertido jugando juegos de mesa con perritos");
        newEvent.setDate(eventDate);
        newEvent.setLocation("Dog Cafe");
        newEvent.setOrganizer("Angela");
        newEvent.setStatus(EStatus.ON_GOING);
        newEvent.setAttendee(attendees);
        newEvent.setBoardGameId(1L);

        testEvent = eventService.saveEvent(newEvent);
    }


    @Test
    @DisplayName("We received the right event by ID")
    public void testFindEventById() {
        Event foundEvent = eventService.findEventById(testEvent.getId());
        assertNotNull(foundEvent);
    }

    @Test
    @DisplayName("we received an exception when trying to find an event by non-existing ID")
    public void testFindEventByIdException() {
        assertThrows(EventNotFoundException.class, () -> eventService.findEventById(99L));
    }

    @Test
    @DisplayName("we received events with the right board game ")
    public void testFindEventByBoardGameId() {
        List<Event> foundEvents = eventService.findEventsByBoardGameId(testEvent.getBoardGameId());
        for (Event eachEvent: foundEvents) {
            assertNotNull(eachEvent);
            assertEquals(testEvent.getBoardGameId(), eachEvent.getBoardGameId());
        }
    }

    @Test
    @DisplayName("We save a new event and check if it is saved properly")
    public void testSaveEvent() {

        Event newEvent = new Event();

        newEvent.setName("Tarde de juegos");
        newEvent.setDescription("Un rato divertido jugando juegos de mesa con perritos");
        newEvent.setDate(eventDate);
        newEvent.setLocation("Dog Cafe");
        newEvent.setOrganizer("Angela");
        newEvent.setStatus(EStatus.ON_GOING);
        newEvent.setAttendee(attendees);
        newEvent.setBoardGameId(1L);

        Event savedEvent = eventService.saveEvent(newEvent);

        assertNotNull(savedEvent);
        assertEquals("Tarde de juegos", savedEvent.getName());
        assertEquals("Un rato divertido jugando juegos de mesa con perritos", savedEvent.getDescription());
        assertEquals(eventDate, savedEvent.getDate());
        assertEquals("Dog Cafe", savedEvent.getLocation());
        assertEquals("Angela", savedEvent.getOrganizer());
        assertEquals(EStatus.ON_GOING, savedEvent.getStatus());
        assertEquals(attendees, savedEvent.getAttendee());
        assertEquals(1L, savedEvent.getBoardGameId());

    }

    @Test
    @DisplayName("We update an existing event and check if it is updated properly")
    public void testUpdateEvent() {
        Event updatedEvent = new Event();

        updatedEvent.setName("Pelea de unicornios");
        updatedEvent.setDescription("Picnic en el parque jugando Unstable Unicorns");
        updatedEvent.setDate(eventDate);
        updatedEvent.setLocation("Retiro");
        updatedEvent.setOrganizer("Angela");
        updatedEvent.setStatus(EStatus.ON_GOING);
        updatedEvent.setAttendee(attendees);
        updatedEvent.setBoardGameId(9L);

        Event changedEvent = eventService.updateEvent(testEvent.getId(),updatedEvent);

        assertNotNull(changedEvent);
        assertEquals("Pelea de unicornios", changedEvent.getName());
        assertEquals("Picnic en el parque jugando Unstable Unicorns", changedEvent.getDescription());
        assertEquals(eventDate, changedEvent.getDate());
        assertEquals("Retiro", changedEvent.getLocation());
        assertEquals("Angela", changedEvent.getOrganizer());
        assertEquals(EStatus.ON_GOING, changedEvent.getStatus());
        assertEquals(attendees, changedEvent.getAttendee());
        assertEquals(9L, changedEvent.getBoardGameId());
    }

    @Test
    @DisplayName("We delete an event by ID and check if it is deleted properly")

    public void testDeleteEventById() {
        eventService.deleteEventById(testEvent.getId());
        assertThrows(EventNotFoundException.class, () -> eventService.findEventById(testEvent.getId()));
    }

    @Test
    @DisplayName("We find all events and check if they are returned properly")
    public void testFindAllEvents() {
        List<Event> foundEvents = eventService.findAll();
        assertNotNull(foundEvents);
    }

    @AfterEach
    public void tearDown() {
        try {
            eventService.deleteEventById(testEvent.getId());
        } catch (EventNotFoundException e) {
            System.out.println("Delete test already clean this");
        }
    }
}
