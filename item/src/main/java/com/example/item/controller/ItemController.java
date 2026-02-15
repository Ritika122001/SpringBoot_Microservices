package com.example.item.controller;


import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.item.dto.ItemDTO;
import com.example.item.service.ItemService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;

@RefreshScope
@RestController
@RequestMapping("/api/items")
public class ItemController {


    @Value("${item.service.fail}")
    private boolean failService;

    private final ItemService ItemService;
    
    public ItemController(ItemService ItemService) {
        this.ItemService = ItemService;
    }

    @PostMapping("/createItem")
    public ItemDTO createItem(@RequestBody ItemDTO dto) {
        return ItemService.createItem(dto);
    }

    @GetMapping("/getItems")
    public List<ItemDTO> getAllItems() {
        return ItemService.getAllItems();
    }

    
    
    @GetMapping("/{id}")
    public ItemDTO getItemById(@PathVariable Long id) {

        if (failService) {
            throw new RuntimeException("Simulated failure");
        }

        return ItemService.getItemById(id);
    }


}