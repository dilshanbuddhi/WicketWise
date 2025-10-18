package org.example.wicketwise.repository;

import org.example.wicketwise.entity.Match;
import org.example.wicketwise.entity.MatchPlayer;
import org.example.wicketwise.entity.Player;
import org.example.wicketwise.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchPlayerRepository extends JpaRepository<MatchPlayer, Long> {
    List<MatchPlayer> findByMatch(Match match);
    List<MatchPlayer> findByPlayer(Player player);
    List<MatchPlayer> findByTeam(Team team);
    List<MatchPlayer> findByMatchAndTeam(Match match, Team team);
    // Add more custom queries as needed
}
