package com.psdadev.stock.stockapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.psdadev.stock.stockapp.model.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    // List all items
    @SuppressWarnings("null")
    List<Item> findAll();

    // Find item by name
    Item findByName(String name);

    // Find item by id
    @SuppressWarnings("null")
    Optional<Item> findById(Long id);

    List<Item> findByCategory(String category);

    List<Item> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);

    // Save Item
    @SuppressWarnings({ "null", "unchecked" })
    Item save(Item item);

    // Delete Item
    @SuppressWarnings("null")
    void deleteById(Long id);

}
