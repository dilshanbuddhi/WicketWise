package org.example.wicketwise.repository;

import org.example.wicketwise.entity.Player;
import org.example.wicketwise.entity.PlayerStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerStatsRepository extends JpaRepository<PlayerStats, Long> {
    
    // Find player stats by player
    Optional<PlayerStats> findByPlayer(Player player);
    
    // Find player stats by player ID
    Optional<PlayerStats> findByPlayerId(Long playerId);
    
    // Find top batsmen by runs (descending order)
    List<PlayerStats> findTop10ByOrderByRunsDesc();
    
    // Find top bowlers by wickets (descending order)
    List<PlayerStats> findTop10ByOrderByWicketsDesc();
    
    // Find players with best batting average (minimum 10 innings)
    @Query("SELECT ps FROM PlayerStats ps WHERE ps.innings >= 10 ORDER BY ps.battingAverage DESC")
    List<PlayerStats> findBestBattingAverages();
    
    // Find players with best bowling average (minimum 10 wickets)
    @Query("SELECT ps FROM PlayerStats ps WHERE ps.wickets >= 10 ORDER BY (ps.runsConceded / NULLIF(ps.wickets, 0)) ASC")
    List<PlayerStats> findBestBowlingAverages();
    
    // Find players by team (through player relationship)
    @Query("SELECT ps FROM PlayerStats ps WHERE ps.player.team.id = :teamId")
    List<PlayerStats> findByTeamId(@Param("teamId") Long teamId);
    
    // Find players with most matches
    List<PlayerStats> findTop10ByOrderByMatchesDesc();
    
    // Find players with best economy rate (minimum 50 overs bowled)
    @Query("SELECT ps FROM PlayerStats ps WHERE ps.ballsBowled >= 300 ORDER BY ps.bowlingEconomy ASC")
    List<PlayerStats> findBestEconomyRates();
    
    // Find players with best strike rate (minimum 100 balls faced)
    @Query("SELECT ps FROM PlayerStats ps WHERE ps.ballsFaced >= 100 ORDER BY ps.battingStrikeRate DESC")
    List<PlayerStats> findBestBattingStrikeRates();
}
