package org.example.wicketwise.repository;

import org.example.wicketwise.entity.Match;
import org.example.wicketwise.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    
    // Basic CRUD operations
    @Override
    Optional<Match> findById(Long id);
    
    @Override
    boolean existsById(Long id);
    
    // Team-related queries
    List<Match> findByHomeTeamOrAwayTeam(Team team1, Team team2);
    
    @Query("SELECT m FROM Match m WHERE m.homeTeam.id = :teamId OR m.awayTeam.id = :teamId")
    List<Match> findByTeamId(@Param("teamId") Long teamId);
    
    @Query("SELECT m FROM Match m WHERE m.homeTeam.id = :teamId OR m.awayTeam.id = :teamId")
    List<Match> findByHomeTeamIdOrAwayTeamId(@Param("teamId") Long teamId1, @Param("teamId") Long teamId2);
    
    List<Match> findByStartTimeAfter(LocalDateTime date);
    
    List<Match> findByStartTimeBefore(LocalDateTime date);
    
    @Query("SELECT m FROM Match m WHERE (m.homeTeam.id = :teamId OR m.awayTeam.id = :teamId) AND m.startTime > CURRENT_TIMESTAMP")
    List<Match> findUpcomingMatchesByTeam(@Param("teamId") Long teamId);
    
    // Date range queries
    List<Match> findByStartTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
    
    @Query("SELECT m FROM Match m WHERE m.startTime > CURRENT_TIMESTAMP")
    List<Match> findUpcomingMatches();
    
    @Query("SELECT m FROM Match m WHERE m.startTime < CURRENT_TIMESTAMP")
    List<Match> findCompletedMatches();
    
    // Status-based queries
    List<Match> findByStatus(String status);
    
    @Query("SELECT m FROM Match m WHERE m.status = :status AND m.startTime > CURRENT_TIMESTAMP")
    List<Match> findUpcomingMatchesByStatus(@Param("status") String status);
    
    // Paginated queries
    Page<Match> findAll(Pageable pageable);
    
    Page<Match> findByStatus(String status, Pageable pageable);
    
    @Query("SELECT m FROM Match m WHERE m.startTime > CURRENT_TIMESTAMP")
    Page<Match> findUpcomingMatches(Pageable pageable);
    
    // Player-related queries
    @Query("SELECT DISTINCT m FROM Match m JOIN m.matchPlayers mp WHERE mp.player.id = :playerId")
    List<Match> findMatchesByPlayerId(@Param("playerId") Long playerId);
    
    // Count queries
    @Query("SELECT COUNT(m) FROM Match m WHERE m.status = :status")
    long countByStatus(@Param("status") String status);
    
    @Query("SELECT COUNT(m) FROM Match m WHERE m.startTime > CURRENT_TIMESTAMP")
    long countUpcomingMatches();
}
