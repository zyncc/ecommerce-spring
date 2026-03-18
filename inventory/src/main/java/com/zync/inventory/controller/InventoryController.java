package com.zync.inventory.controller;

import com.zync.inventory.dto.InventoryDto;
import com.zync.inventory.dto.InventoryRequest;
import com.zync.inventory.dto.UpdateInventoryDto;
import com.zync.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {
    private final InventoryService inventoryService;

    @GetMapping("/{productId}")
    public InventoryDto getInventory(@PathVariable UUID productId) {
            return inventoryService.getInventory(productId);
    }

    @PatchMapping
    public ResponseEntity<String> updateInventory(@RequestBody UpdateInventoryDto payload) {
        inventoryService.updateInventory(payload);
        return ResponseEntity.ok("Updated inventory");
    }

    @PostMapping
    public ResponseEntity<String> createInventory(@RequestBody InventoryRequest request) {
        inventoryService.createInventory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("Created Inventory");
    }
}
