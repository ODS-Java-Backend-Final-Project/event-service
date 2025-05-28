package com.example.event_service.controllers;

import com.example.event_service.DTO.BoardGameDTO;
import com.example.event_service.DTO.EventBoardGameDTO;
import com.example.event_service.clients.BoardGameClient;
import com.example.event_service.exceptions.EventNotFoundException;
import com.example.event_service.models.EStatus;
import com.example.event_service.models.Event;
import com.example.event_service.repositories.EventRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@SpringBootTest
public class EventControllerTestMockMVC {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    EventRepository eventRepository;

    @MockBean
    private BoardGameClient boardGameClient;


    private MockMvc mockMvc; // esta clase nos servirá para simular peticiones HTTP

    // es una herramienta para convertir objetos a JSON.
    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());


    private Event testEvent;
    private Event testEvent2;
    private EventBoardGameDTO testDTO;
    LocalDateTime eventDate = LocalDateTime.of(2025, 10, 1, 18, 0);
    List<String> attendees = List.of("Alice", "Bob");

    @BeforeEach

    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        Event newEvent = new Event();

        newEvent.setName("Tarde de juegos");
        newEvent.setDescription("Un ratos divertido jugando juegos de mesa con perritos");
        newEvent.setDate(eventDate);
        newEvent.setLocation("Dog Cafe");
        newEvent.setOrganizer("Angela");
        newEvent.setStatus(EStatus.ON_GOING);
        newEvent.setAttendee(attendees);
        newEvent.setBoardGameId(1L);

        testEvent = eventRepository.save(newEvent);

        Event newEvent2 = new Event();

        newEvent2.setName("Pelea de unicornios");
        newEvent2.setDescription("Picnic en el parque jugando Unstable Unicorns");
        newEvent2.setDate(eventDate);
        newEvent2.setLocation("Retiro");
        newEvent2.setOrganizer("Angela");
        newEvent2.setStatus(EStatus.ON_GOING);
        newEvent2.setAttendee(attendees);
        newEvent2.setBoardGameId(9L);

        testEvent2 = eventRepository.save(newEvent2);
    }


    @AfterEach
    public void tearDown() {
        try {
            eventRepository.deleteById(testEvent.getId());
            eventRepository.deleteById(testEvent2.getId());
        } catch (EventNotFoundException e) {
            System.out.println("Delete test worked and already clean this");
        }
    }

    @Test
    public void createEventTest() throws Exception {
        String eventJson = objectMapper.writeValueAsString(testEvent);

        MvcResult result = mockMvc.perform(post("/api/events")
                .contentType(MediaType.APPLICATION_JSON)
                .content(eventJson)
        ).andExpect(status().isCreated()).andReturn();

        String stringResponse = result.getResponse().getContentAsString();
        System.out.println(result.getResponse());
        assertTrue(stringResponse.contains(testEvent.getName()));
    }

    @Test
    public void putEventTest() throws Exception {
        String eventJson = objectMapper.writeValueAsString(testEvent);
        MvcResult result = mockMvc.perform(put("/api/events/" + testEvent.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(eventJson)
        ).andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(testEvent.getName()));
    }

    @Test
    void getAllTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/events"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains(testEvent.getName()));
        assertTrue(mvcResult.getResponse().getContentAsString().contains(testEvent2.getName()));
    }

    @Test
    void getByIdTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/api/events/" + testEvent.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains(testEvent.getName()));
    }
//ESTA RUTA DEBE LLAMAR AL CLIENT PERO COMO SIN LEVANTAR SERVIDORES SIN USAR MOCKBEAN DEPRECATED D: !!!
    @Test
    void getByBoardGamedTest() throws Exception {
        BoardGameDTO mockBoardGame = new BoardGameDTO();
        mockBoardGame.setId(9L);
        mockBoardGame.setName("Unstable Unicorns");
        mockBoardGame.setCategory("Strategy");
        mockBoardGame.setMinPlayers(2);
        mockBoardGame.setMaxPlayers(6);
        mockBoardGame.setDuration(30);

        when(boardGameClient.getBoardGameById(9L)).thenReturn(mockBoardGame);


        MvcResult mvcResult = mockMvc.perform(get("/api/events/board-game/" + mockBoardGame.getId()))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andReturn();
        assertTrue(mvcResult.getResponse().getContentAsString().contains("Unstable Unicorns"));
    }

    @Test
    void getDeleteByIdTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(delete("/api/events/" + testEvent.getId()))
                .andExpect(status().isNoContent()).andReturn();
        Optional<Event> deletedEvent = eventRepository.findById(testEvent.getId());
        assertFalse(deletedEvent.isPresent(), "Event hasn´t been deleted. Test failed");
    }
}