package org.example.wicketwise.mapper;

import lombok.RequiredArgsConstructor;
import org.example.wicketwise.dto.DeliveryDto;
import org.example.wicketwise.entity.Delivery;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryMapper {
    
    private final ModelMapper modelMapper;
    private final InningsMapper inningsMapper;
    private final PlayerMapper playerMapper;
    
    public DeliveryDto toDto(Delivery delivery) {
        if (delivery == null) {
            return null;
        }
        return modelMapper.map(delivery, DeliveryDto.class);
    }
    
    public Delivery toEntity(DeliveryDto deliveryDto) {
        if (deliveryDto == null) {
            return null;
        }
        return modelMapper.map(deliveryDto, Delivery.class);
    }
    
    public void updateDeliveryFromDto(DeliveryDto deliveryDto, Delivery delivery) {
        if (deliveryDto == null || delivery == null) {
            return;
        }
        modelMapper.map(deliveryDto, delivery);
    }
}
