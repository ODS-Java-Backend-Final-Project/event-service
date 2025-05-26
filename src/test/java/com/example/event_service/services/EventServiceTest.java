package com.example.event_service.services;

import com.example.event_service.models.Event;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

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
}
