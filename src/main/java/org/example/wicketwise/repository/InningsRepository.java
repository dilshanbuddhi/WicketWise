package org.example.wicketwise.repository;

import org.example.wicketwise.entity.Innings;
import org.example.wicketwise.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InningsRepository extends JpaRepository<Innings, Long> {
    List<Innings> findByMatch(Match match);
    List<Innings> findByBattingTeamId(Long teamId);
    // Add more custom queries as needed
}
