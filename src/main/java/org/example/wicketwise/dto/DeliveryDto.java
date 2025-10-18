package org.example.wicketwise.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryDto {
    private Long id;
    private InningsDto innings;
    private Integer overNumber;
    private Integer ballNumber;
    private PlayerDto bowler;
    private PlayerDto batsman;
    private PlayerDto nonStriker;
    private Integer runsScored;
    private Integer extras;
    private String wicketType; // "bowled", "caught", "run out", etc.
    private PlayerDto fielder; // For catches/run outs
    private String notes; // Any additional information

}
