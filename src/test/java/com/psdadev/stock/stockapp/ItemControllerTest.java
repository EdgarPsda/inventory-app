package com.psdadev.stock.stockapp;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.psdadev.stock.stockapp.controllers.ItemController;
import com.psdadev.stock.stockapp.model.Item;
import com.psdadev.stock.stockapp.service.ItemService;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Arrays;
import java.util.List;

@WebMvcTest(ItemController.class)
public class ItemControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ItemService itemService;

    @InjectMocks
    private ItemController itemController;

    @Test
    public void testGetItemById() throws Exception {
        Long itemId = 1L;
        Item item = new Item(itemId, "Test Item", null, null, null, null);

        when(itemService.findItemById(itemId)).thenReturn(item);

        mockMvc.perform(get("/api/v1/items/{id}", itemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(itemId))
                .andExpect(jsonPath("$.name").value("Test Item"));
    }

    @Test
    public void testGetItemById_ItemNotFound() throws Exception {
        Long itemId = 1L;

        when(itemService.findItemById(itemId)).thenReturn(null);

        mockMvc.perform(get("/items/{id}", itemId))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetItems() throws Exception {
        List<Item> items = Arrays.asList(
                new Item(1L, "Item 1", null, null, null, null),
                new Item(2L, "Item 2", null, null, null, null));

        when(itemService.findAllItems()).thenReturn(items);

        mockMvc.perform(get("/api/v1/items"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Item 1"))
                .andExpect(jsonPath("$[1].id").value(2L))
                .andExpect(jsonPath("$[1].name").value("Item 2"));
    }

    @Test
    public void testDeleteItem() throws Exception {
        Long itemId = 1L;
        Item item = new Item(itemId, "Test Item", null, null, null, null);

        when(itemService.findItemById(itemId)).thenReturn(item);

        mockMvc.perform(delete("/api/v1/items/{id}", itemId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Item deleted"));

        verify(itemService, times(1)).deleteItem(itemId);
    }
}