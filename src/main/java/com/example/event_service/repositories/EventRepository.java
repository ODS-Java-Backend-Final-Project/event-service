package com.example.event_service.repositories;

import com.example.event_service.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    List<Event> findByBoardGameId(Long boardGameId);
}
