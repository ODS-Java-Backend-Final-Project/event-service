package com.example.event_service.DTO;

import com.example.event_service.models.EStatus;
import com.example.event_service.models.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventBoardGameDTO {
    private Long id;
    private String name;
    private String description;
    private LocalDateTime date;
    private String location;
    private String organizer;
    private EStatus status;
    private String boardGameName;
    private String category;
    private int minPlayers;
    private int maxPlayers;
    private int duration;
    private List<String> attendee;

    public EventBoardGameDTO(Event event, BoardGameDTO boardGameDTO) {
        this.id = event.getId();
        this.name = event.getName();
        this.description = event.getDescription();
        this.date = event.getDate();
        this.location = event.getLocation();
        this.organizer = event.getOrganizer();
        this.status = event.getStatus();
        this.attendee = event.getAttendee();

        this.boardGameName = boardGameDTO.getName();
        this.category = boardGameDTO.getCategory();
        this.minPlayers = boardGameDTO.getMinPlayers();
        this.maxPlayers = boardGameDTO.getMaxPlayers();
        this.duration = boardGameDTO.getDuration();
    }

}
