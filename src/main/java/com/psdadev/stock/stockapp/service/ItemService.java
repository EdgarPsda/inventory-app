package com.psdadev.stock.stockapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.psdadev.stock.stockapp.model.Item;
import com.psdadev.stock.stockapp.repository.ItemRepository;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    // Return all items
    public List<Item> findAllItems() {

        try {
            return itemRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error getting items", e);
        }
    }

    // Return item by id
    public Item findItemById(Long id) {
        try {
            return itemRepository.findById(id).orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error getting item", e);
        }
    }

    // Return item by name
    public Item findItemByName(String name) {
        try {
            return itemRepository.findByName(name);
        } catch (Exception e) {
            throw new RuntimeException("Error getting item", e);
        }
    }

    // Save Item
    public Item saveItem(Item item) {
        try {
            return itemRepository.save(item);
        } catch (Exception e) {
            throw new RuntimeException("Error saving item", e);
        }
    }

    // Delete Item
    public void deleteItem(Long id) {
        try {
            itemRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException("Error deleting item", e);
        }
    }
}
