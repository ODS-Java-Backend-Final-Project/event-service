package com.example.event_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name can´t be empty")
    private String name;
    private String description;

    @NotNull(message = "You must choose a date for the event")
    private LocalDateTime date;
    private String location;
    private String organizer;

    @Enumerated(EnumType.STRING)
    @NotNull
    private EStatus status;

    @ElementCollection
    @CollectionTable(name = "event_attendees", joinColumns = @JoinColumn(name = "event_id"))
    @Column(name = "attendee")
    private List<String> attendee;

    @NotNull(message = "You must provide a Board Game ID")
    private Long boardGameId;

    public Event(String name, String description, LocalDateTime date, String location, String organizer, EStatus status, List<String> attendee, Long boardGameId) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.location = location;
        this.organizer = organizer;
        this.status = status;
        this.attendee = attendee;
        this.boardGameId = boardGameId;
    }
}
