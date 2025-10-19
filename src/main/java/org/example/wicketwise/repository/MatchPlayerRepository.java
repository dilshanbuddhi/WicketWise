package org.example.wicketwise.repository;

import org.example.wicketwise.entity.Match;
import org.example.wicketwise.entity.MatchPlayer;
import org.example.wicketwise.entity.Player;
import org.example.wicketwise.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatchPlayerRepository extends JpaRepository<MatchPlayer, Long> {
    // Basic CRUD operations
    @Override
    Optional<MatchPlayer> findById(Long id);
    
    @Override
    boolean existsById(Long id);
    
    // Find by entity references
    List<MatchPlayer> findByMatch(Match match);
    List<MatchPlayer> findByPlayer(Player player);
    List<MatchPlayer> findByTeam(Team team);
    List<MatchPlayer> findByMatchAndTeam(Match match, Team team);
    
    // Find by IDs
    @Query("SELECT mp FROM MatchPlayer mp WHERE mp.match.id = :matchId")
    List<MatchPlayer> findByMatchId(@Param("matchId") Long matchId);
    
    @Query("SELECT mp FROM MatchPlayer mp WHERE mp.player.id = :playerId")
    List<MatchPlayer> findByPlayerId(@Param("playerId") Long playerId);
    
    @Query("SELECT mp FROM MatchPlayer mp WHERE mp.team.id = :teamId")
    List<MatchPlayer> findByTeamId(@Param("teamId") Long teamId);
    
    // Find by match and player
    @Query("SELECT mp FROM MatchPlayer mp WHERE mp.match.id = :matchId AND mp.player.id = :playerId")
    Optional<MatchPlayer> findByMatchIdAndPlayerId(
            @Param("matchId") Long matchId,
            @Param("playerId") Long playerId
    );
    
    // Find by match and team
    @Query("SELECT mp FROM MatchPlayer mp WHERE mp.match.id = :matchId AND mp.team.id = :teamId")
    List<MatchPlayer> findByMatchIdAndTeamId(
            @Param("matchId") Long matchId,
            @Param("teamId") Long teamId
    );
    
    // Check if player exists in a match
    @Query("SELECT CASE WHEN COUNT(mp) > 0 THEN true ELSE false END " +
           "FROM MatchPlayer mp WHERE mp.match.id = :matchId AND mp.player.id = :playerId")
    boolean existsByMatchIdAndPlayerId(
            @Param("matchId") Long matchId,
            @Param("playerId") Long playerId
    );
    
    // Count players in a match
    @Query("SELECT COUNT(mp) FROM MatchPlayer mp WHERE mp.match.id = :matchId")
    long countByMatchId(@Param("matchId") Long matchId);
    
    // Count players in a team for a match
    @Query("SELECT COUNT(mp) FROM MatchPlayer mp WHERE mp.match.id = :matchId AND mp.team.id = :teamId")
    long countByMatchIdAndTeamId(
            @Param("matchId") Long matchId,
            @Param("teamId") Long teamId
    );
}
