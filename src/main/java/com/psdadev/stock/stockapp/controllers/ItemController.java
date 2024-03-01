package com.psdadev.stock.stockapp.controllers;

import java.util.List;

import javax.management.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.psdadev.stock.stockapp.model.Item;
import com.psdadev.stock.stockapp.service.ItemService;

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

}
