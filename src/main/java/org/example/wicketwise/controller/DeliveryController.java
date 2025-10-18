package org.example.wicketwise.controller;

import lombok.RequiredArgsConstructor;
import org.example.wicketwise.dto.DeliveryDto;
import org.example.wicketwise.service.DeliveryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping
    public ResponseEntity<DeliveryDto> createDelivery(@RequestBody DeliveryDto deliveryDto) {
        DeliveryDto createdDelivery = deliveryService.createDelivery(deliveryDto);
        return new ResponseEntity<>(createdDelivery, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryDto> getDeliveryById(@PathVariable Long id) {
        DeliveryDto deliveryDto = deliveryService.getDeliveryById(id);
        return ResponseEntity.ok(deliveryDto);
    }

    @GetMapping("/innings/{inningsId}")
    public ResponseEntity<List<DeliveryDto>> getDeliveriesByInnings(@PathVariable Long inningsId) {
        List<DeliveryDto> deliveries = deliveryService.getDeliveriesByInnings(inningsId);
        return ResponseEntity.ok(deliveries);
    }

    @GetMapping("/bowler/{bowlerId}")
    public ResponseEntity<List<DeliveryDto>> getDeliveriesByBowler(@PathVariable Long bowlerId) {
        List<DeliveryDto> deliveries = deliveryService.getDeliveriesByBowler(bowlerId);
        return ResponseEntity.ok(deliveries);
    }

    @GetMapping("/batsman/{batsmanId}")
    public ResponseEntity<List<DeliveryDto>> getDeliveriesByBatsman(@PathVariable Long batsmanId) {
        List<DeliveryDto> deliveries = deliveryService.getDeliveriesByBatsman(batsmanId);
        return ResponseEntity.ok(deliveries);
    }

    @GetMapping("/match/{matchId}/innings/{inningsNumber}")
    public ResponseEntity<List<DeliveryDto>> getDeliveriesByMatchAndInnings(
            @PathVariable Long matchId,
            @PathVariable Integer inningsNumber) {
        List<DeliveryDto> deliveries = deliveryService.getDeliveriesByMatchAndInnings(matchId, inningsNumber);
        return ResponseEntity.ok(deliveries);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryDto> updateDelivery(
            @PathVariable Long id,
            @RequestBody DeliveryDto deliveryDto) {
        DeliveryDto updatedDelivery = deliveryService.updateDelivery(id, deliveryDto);
        return ResponseEntity.ok(updatedDelivery);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
        deliveryService.deleteDelivery(id);
        return ResponseEntity.noContent().build();
    }
}
