package org.example.wicketwise.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayerStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Player player;

    private Integer matches = 0;
    private Integer innings = 0;
    private Integer runs = 0;
    private Integer ballsFaced = 0;
    private Integer fifties = 0;
    private Integer hundreds = 0;
    private Double average = 0.0;
    private Double strikeRate = 0.0;

    private Integer wickets = 0;
    private Integer ballsBowled = 0;
    private Integer runsConceded = 0;
    private Double economy = 0.0;
}
