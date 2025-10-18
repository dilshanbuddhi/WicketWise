package org.example.wicketwise.repository;

import org.example.wicketwise.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    // Custom query methods can be added here
    // Example:
    // List<Team> findByNameContaining(String name);
}
