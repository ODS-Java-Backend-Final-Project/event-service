package com.example.event_service.DTO;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardGameDTO {
    private Long id;
    private String name;
    private String category;
    private int minPlayers;
    private int maxPlayers;
    private int duration;
}