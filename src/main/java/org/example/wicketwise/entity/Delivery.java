package org.example.wicketwise.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Delivery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Innings innings;

    private Integer overNumber;
    private Integer ballInOver;

    @ManyToOne
    private Player bowler;

    @ManyToOne
    private Player batsman;

    @ManyToOne
    private Player nonStriker;

    private Integer runs = 0;
    private Integer batsmanRuns = 0;
    private Integer extras = 0;
    private String extraType; // wide, no-ball, bye, leg-bye

    private Boolean wicket = false;
    private String wicketType; // bowled, caught, runout, lbw...
    private Long fielderId; // optional reference

    private Boolean freeHit = false;
}
