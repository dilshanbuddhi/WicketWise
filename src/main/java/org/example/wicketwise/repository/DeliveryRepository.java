package org.example.wicketwise.repository;

import org.example.wicketwise.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    /**
     * Find a delivery by its ID
     * @param id the ID of the delivery
     * @return an Optional containing the delivery if found
     */
    Optional<Delivery> findById(Long id);

    /**
     * Check if a delivery exists by ID
     * @param id the ID of the delivery
     * @return true if a delivery with the given ID exists, false otherwise
     */
    boolean existsById(Long id);

    /**
     * Find all deliveries in a specific innings by innings ID
     * @param inningsId the ID of the innings
     * @return List of deliveries in the specified innings
     */
    @Query("SELECT d FROM Delivery d WHERE d.innings.id = :inningsId ORDER BY d.overNumber, d.ballInOver")
    List<Delivery> findByInningsId(@Param("inningsId") Long inningsId);
    /**
     * Find all deliveries bowled by a specific player by bowler ID
     * @param bowlerId the ID of the bowler
     * @return List of deliveries bowled by the specified player
     */
    @Query("SELECT d FROM Delivery d WHERE d.bowler.id = :bowlerId ORDER BY d.innings.match.startTime DESC, d.innings.inningsNumber, d.overNumber, d.ballInOver")
    List<Delivery> findByBowlerId(@Param("bowlerId") Long bowlerId);

    /**
     * Find all deliveries faced by a specific batsman by batsman ID
     * @param batsmanId the ID of the batsman
     * @return List of deliveries faced by the specified batsman
     */
    @Query("SELECT d FROM Delivery d WHERE d.batsman.id = :batsmanId ORDER BY d.innings.match.startTime DESC, d.innings.inningsNumber, d.overNumber, d.ballInOver")
    List<Delivery> findByBatsmanId(@Param("batsmanId") Long batsmanId);
    
    /**
     * Find all deliveries where the specified player is the batsman or the bowler
     * @param playerId the ID of the player
     * @return List of deliveries where the player was involved as batsman or bowler
     */
    @Query("SELECT d FROM Delivery d WHERE d.batsman.id = :playerId OR d.bowler.id = :playerId ORDER BY d.innings.match.startTime DESC, d.innings.inningsNumber, d.overNumber, d.ballInOver")
    List<Delivery> findByPlayerInvolved(@Param("playerId") Long playerId);
    
    /**
     * Find all deliveries in a specific match
     * @param matchId the ID of the match
     * @return List of deliveries in the specified match
     */
    @Query("SELECT d FROM Delivery d WHERE d.innings.match.id = :matchId ORDER BY d.innings.inningsNumber, d.overNumber, d.ballInOver")
    List<Delivery> findByMatchId(@Param("matchId") Long matchId);
    
    /**
     * Check if a delivery exists for a specific match
     * @param matchId the ID of the match
     * @return true if deliveries exist for the match, false otherwise
     */
    @Query("SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END FROM Delivery d WHERE d.innings.match.id = :matchId")
    boolean existsByMatchId(@Param("matchId") Long matchId);

    /**
     * Find all deliveries in a specific innings of a match
     * @param matchId the ID of the match
     * @param inningsNumber the innings number (1 or 2)
     * @return List of deliveries in the specified innings of the match
     */
    @Query("SELECT d FROM Delivery d WHERE d.innings.match.id = :matchId AND d.innings.inningsNumber = :inningsNumber ORDER BY d.overNumber, d.ballInOver")
    List<Delivery> findByInningsMatchIdAndInningsInningsNumber(
            @Param("matchId") Long matchId,
            @Param("inningsNumber") Integer inningsNumber
    );

    /**
     * Delete a delivery by its ID
     * @param id the ID of the delivery to delete
     */
    void deleteById(Long id);

}
