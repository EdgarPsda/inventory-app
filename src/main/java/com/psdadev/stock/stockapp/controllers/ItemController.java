package com.psdadev.stock.stockapp.controllers;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.psdadev.stock.stockapp.model.Item;
import com.psdadev.stock.stockapp.service.ItemService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @GetMapping("/items")
    public ResponseEntity<List<Item>> getItems() {

        List<Item> items = itemService.findAllItems();

        if (items.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        try {
            return new ResponseEntity<>(items, HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Error getting items");
        }
    }

    @GetMapping("/items/{id}")
    public Item getItemById(@PathVariable Long id) {
        Item item = itemService.findItemById(id);

        if (item == null) {
            throw new RuntimeException("Item not found");
        }

        try {
            return item;
        } catch (Exception e) {
            throw new RuntimeException("Error getting item");
        }
    }

    @PostMapping("/items")
    public ResponseEntity<Item> createItem(@Valid @RequestBody Item item) {
        Item savedItem = itemService.saveItem(item);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedItem.getId()).toUri();

        try {
            return ResponseEntity.created(location).body(savedItem);
        } catch (Exception e) {
            throw new RuntimeException("Error saving item");
        }
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<Item> updateItem(@Valid @RequestBody Item item, @PathVariable Long id) {
        Item updateItem = itemService.findItemById(id);

        if (updateItem == null) {
            throw new RuntimeException("Item not found");
        }

        // update values if not null
        updateItem.setName(item.getName() != null ? item.getName() : updateItem.getName());
        updateItem.setDescription(item.getDescription() != null ? item.getDescription() : updateItem.getDescription());
        updateItem.setCategory(item.getCategory() != null ? item.getCategory() : updateItem.getCategory());
        updateItem.setPrice(item.getPrice() != null ? item.getPrice() : updateItem.getPrice());
        updateItem.setQuantity(item.getQuantity() != null ? item.getQuantity() : updateItem.getQuantity());

        Item savedItem = itemService.saveItem(updateItem);

        try {
            return ResponseEntity.ok(savedItem);
        } catch (Exception e) {
            throw new RuntimeException("Error updating item");
        }
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {
        Item item = itemService.findItemById(id);

        if (item == null) {
            throw new RuntimeException("Item not found");
        }

        try {
            itemService.deleteItem(id);
            Map<String, String> response = new HashMap<>();
            response.put("message", "Item deleted");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting item");
        }
    }

}
