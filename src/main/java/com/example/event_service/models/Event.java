package com.example.event_service.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    @Size(max = 100, message = "Name can´t be longer than 100 characters")
    private String name;

    @Size(max = 500, message = "Description can´t be longer than 500 characters")
    private String description;

    @NotNull(message = "You must choose a date for the event")
    @FutureOrPresent(message = "The date can´t be in the past")
    private LocalDateTime date;

    @NotBlank
    private String location;

    @NotBlank
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
}
