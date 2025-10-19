package org.example.wicketwise.repository;

import org.example.wicketwise.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    
    // Basic CRUD operations are provided by JpaRepository
    // Override findById to return Optional<Team> for better null safety
    @Override
    Optional<Team> findById(Long id);
    
    // Find team by exact name (case-sensitive)
    Optional<Team> findByName(String name);
    
    // Find teams by name containing (case-insensitive)
    List<Team> findByNameContainingIgnoreCase(String name);
    
    // Find teams by country
    List<Team> findByCountry(String country);
    
    // Find teams by city
    List<Team> findByCity(String city);
    
    // Find teams by home ground
    List<Team> findByHomeGround(String homeGround);
    
    // Find teams by country and city
    List<Team> findByCountryAndCity(String country, String city);
    
    // Find teams established after a certain year
    List<Team> findByEstablishedYearAfter(Integer year);
    
    // Find teams established before a certain year
    List<Team> findByEstablishedYearBefore(Integer year);
    
    // Find teams established between two years
    List<Team> findByEstablishedYearBetween(Integer startYear, Integer endYear);
    
    // Custom query to search teams by name, city, or country (case-insensitive)
    @Query("SELECT t FROM Team t WHERE LOWER(t.name) LIKE CONCAT('%', LOWER(:query), '%')")
    List<Team> searchTeams(@Param("query") String query);
    
    // Check if a team with the given name exists (case-insensitive)
    boolean existsByNameIgnoreCase(String name);
    
    // Count teams by country
    long countByCountry(String country);
    
    // Count teams by city
    long countByCity(String city);
}
