package org.example.wicketwise.repository;

import org.example.wicketwise.entity.Match;
import org.example.wicketwise.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByHomeTeamOrAwayTeam(Team team1, Team team2);
    List<Match> findByStartTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
    List<Match> findByStatus(String status);
    // Add more custom queries as needed
}
