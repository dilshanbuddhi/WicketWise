package org.example.wicketwise.repository;

import org.example.wicketwise.entity.Innings;
import org.example.wicketwise.entity.Match;
import org.example.wicketwise.entity.Team;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface InningsRepository extends JpaRepository<Innings, Long> {
    
    // Find all innings for a specific match
    List<Innings> findByMatch(Match match);
    
    // Find all innings by batting team ID
    List<Innings> findByBattingTeamId(Long teamId);
    
    // Find all innings by match ID
    List<Innings> findByMatchId(Long matchId);
    
    // Find top scoring innings by team ID with pagination
    @Query("SELECT i FROM Innings i WHERE i.battingTeam.id = :teamId ORDER BY i.runs DESC")
    Page<Innings> findTopScoresByTeam(@Param("teamId") Long teamId, Pageable pageable);
    
    // Find all innings where the team is either batting or bowling
    @Query("SELECT i FROM Innings i WHERE i.battingTeam.id = :teamId OR i.bowlingTeam.id = :teamId")
    List<Innings> findInningsByTeamInvolvement(@Param("teamId") Long teamId);
    
    // Find innings by match and team
    @Query("SELECT i FROM Innings i WHERE i.match.id = :matchId AND (i.battingTeam.id = :teamId OR i.bowlingTeam.id = :teamId)")
    List<Innings> findByMatchIdAndTeamId(@Param("matchId") Long matchId, @Param("teamId") Long teamId);

    /**
     * Find all innings where the specified team is involved (either batting or bowling)
     * @param team the team to search for
     * @return List of innings where the team was involved
     */
    @Query("SELECT i FROM Innings i WHERE i.battingTeam = :team OR i.bowlingTeam = :team")
    List<Innings> findInningsByTeam(@Param("team") Team team);
}
