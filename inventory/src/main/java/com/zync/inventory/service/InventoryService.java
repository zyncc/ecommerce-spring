package com.zync.inventory.service;

import com.zync.inventory.dto.InventoryDto;
import com.zync.inventory.dto.InventoryMapper;
import com.zync.inventory.dto.InventoryRequest;
import com.zync.inventory.dto.UpdateInventoryDto;
import com.zync.inventory.entity.InventoryEntity;
import com.zync.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    public InventoryDto getInventory(UUID productId) {
        Optional<InventoryEntity> getInventory = inventoryRepository.findOneByProductId(productId);
        if (getInventory.isPresent()) {
            return InventoryMapper.toDto(getInventory.get());
        }

        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory does not exist for this product id");
    }

    public void updateInventory(UpdateInventoryDto payload) {
        InventoryEntity getInventory = inventoryRepository.findOneByProductId(payload.getProductId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Inventory does not exist for this product id"));

        try {
            getInventory.setQuantity(getInventory.quantity - payload.getQuantity());
            inventoryRepository.save(getInventory);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to update inventory");
        }
    }

    public void createInventory(InventoryRequest request) {
        InventoryEntity entity = new InventoryEntity();

        entity.setProductId(request.productId);
        entity.setQuantity(request.quantity);

        try {
            inventoryRepository.save(entity);
        } catch (RuntimeException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create inventory");
        }
    }
}
