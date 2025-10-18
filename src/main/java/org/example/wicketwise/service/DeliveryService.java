package org.example.wicketwise.service;

import org.example.wicketwise.dto.DeliveryDto;

import java.util.List;

public interface DeliveryService {
    DeliveryDto createDelivery(DeliveryDto deliveryDto);
    DeliveryDto getDeliveryById(Long id);
    List<DeliveryDto> getDeliveriesByInnings(Long inningsId);
    List<DeliveryDto> getDeliveriesByBowler(Long bowlerId);
    List<DeliveryDto> getDeliveriesByBatsman(Long batsmanId);
    DeliveryDto updateDelivery(Long id, DeliveryDto deliveryDto);
    void deleteDelivery(Long id);
    List<DeliveryDto> getDeliveriesByMatchAndInnings(Long matchId, Integer inningsNumber);
}
