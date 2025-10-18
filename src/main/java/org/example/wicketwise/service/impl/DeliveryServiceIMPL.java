package org.example.wicketwise.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.wicketwise.dto.DeliveryDto;
import org.example.wicketwise.entity.Delivery;
import org.example.wicketwise.exception.ResourceNotFoundException;
import org.example.wicketwise.mapper.DeliveryMapper;
import org.example.wicketwise.repository.DeliveryRepository;
import org.example.wicketwise.repository.InningsRepository;
import org.example.wicketwise.repository.PlayerRepository;
import org.example.wicketwise.service.DeliveryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DeliveryServiceIMPL implements DeliveryService {

    private final DeliveryRepository deliveryRepository;
    private final InningsRepository inningsRepository;
    private final PlayerRepository playerRepository;
    private final DeliveryMapper deliveryMapper;

    @Override
    public DeliveryDto createDelivery(DeliveryDto deliveryDto) {
        log.debug("Creating new delivery");
        
        // Validate innings exists
        if (!inningsRepository.existsById(deliveryDto.getInnings().getId())) {
            throw new ResourceNotFoundException("Innings not found with id: " + deliveryDto.getInnings().getId());
        }
        
        // Validate bowler exists if provided
        if (deliveryDto.getBowler() != null && !playerRepository.existsById(deliveryDto.getBowler().getId())) {
            throw new ResourceNotFoundException("Bowler not found with id: " + deliveryDto.getBowler().getId());
        }
        
        // Validate batsman exists if provided
        if (deliveryDto.getBatsman() != null && !playerRepository.existsById(deliveryDto.getBatsman().getId())) {
            throw new ResourceNotFoundException("Batsman not found with id: " + deliveryDto.getBatsman().getId());
        }
        
        try {
            Delivery delivery = deliveryMapper.toEntity(deliveryDto);
            Delivery savedDelivery = deliveryRepository.save(delivery);
            log.info("Created new delivery with ID: {}", savedDelivery.getId());
            return deliveryMapper.toDto(savedDelivery);
        } catch (Exception e) {
            log.error("Error creating delivery: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create delivery", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public DeliveryDto getDeliveryById(Long id) {
        log.debug("Fetching delivery with ID: {}", id);
        return deliveryRepository.findById(id)
                .map(deliveryMapper::toDto)
                .orElseThrow(() -> {
                    log.warn("Delivery not found with ID: {}", id);
                    return new ResourceNotFoundException("Delivery not found with id: " + id);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeliveryDto> getDeliveriesByInnings(Long inningsId) {
        log.debug("Fetching deliveries for innings ID: {}", inningsId);
        
        if (!inningsRepository.existsById(inningsId)) {
            throw new ResourceNotFoundException("Innings not found with id: " + inningsId);
        }
        
        return deliveryRepository.findByInningsId(inningsId).stream()
                .map(deliveryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeliveryDto> getDeliveriesByBowler(Long bowlerId) {
        log.debug("Fetching deliveries by bowler ID: {}", bowlerId);
        
        if (!playerRepository.existsById(bowlerId)) {
            throw new ResourceNotFoundException("Player not found with id: " + bowlerId);
        }
        
        return deliveryRepository.findByBowlerId(bowlerId).stream()
                .map(deliveryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeliveryDto> getDeliveriesByBatsman(Long batsmanId) {
        log.debug("Fetching deliveries by batsman ID: {}", batsmanId);
        
        if (!playerRepository.existsById(batsmanId)) {
            throw new ResourceNotFoundException("Player not found with id: " + batsmanId);
        }
        
        return deliveryRepository.findByBatsmanId(batsmanId).stream()
                .map(deliveryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<DeliveryDto> getDeliveriesByMatchAndInnings(Long matchId, Integer inningsNumber) {
        log.debug("Fetching deliveries for match ID: {} and innings number: {}", matchId, inningsNumber);
        
        return deliveryRepository.findByInningsMatchIdAndInningsInningsNumber(matchId, inningsNumber).stream()
                .map(deliveryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DeliveryDto updateDelivery(Long id, DeliveryDto deliveryDto) {
        log.debug("Updating delivery with ID: {}", id);
        
        return deliveryRepository.findById(id)
                .map(existingDelivery -> {
                    deliveryMapper.updateDeliveryFromDto(deliveryDto, existingDelivery);
                    Delivery updatedDelivery = deliveryRepository.save(existingDelivery);
                    log.info("Updated delivery with ID: {}", id);
                    return deliveryMapper.toDto(updatedDelivery);
                })
                .orElseThrow(() -> {
                    log.warn("Delivery not found for update with ID: {}", id);
                    return new ResourceNotFoundException("Delivery not found with id: " + id);
                });
    }

    @Override
    public void deleteDelivery(Long id) {
        log.debug("Deleting delivery with ID: {}", id);
        
        if (!deliveryRepository.existsById(id)) {
            log.warn("Delivery not found for deletion with ID: {}", id);
            throw new ResourceNotFoundException("Delivery not found with id: " + id);
        }
        
        try {
            deliveryRepository.deleteById(id);
            log.info("Deleted delivery with ID: {}", id);
        } catch (Exception e) {
            log.error("Error deleting delivery with ID {}: {}", id, e.getMessage(), e);
            throw new RuntimeException("Failed to delete delivery with ID: " + id, e);
        }
    }
}
