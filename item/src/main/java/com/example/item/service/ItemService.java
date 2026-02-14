package com.example.item.service;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import com.example.item.dto.ItemDTO;
import com.example.item.entities.Item;
import com.example.item.repository.ItemRepository;

@Service
public class ItemService {

    private final ItemRepository ItemRepository;

    public ItemService(ItemRepository ItemRepository) {
        this.ItemRepository = ItemRepository;
    }

    public ItemDTO createItem(ItemDTO dto) {
        Item Item = new Item();
        Item.setName(dto.getName());
        Item.setPrice(dto.getPrice());
        Item saved = ItemRepository.save(Item);
        return convertToDTO(saved);
    }

    public List<ItemDTO> getAllItems() {
        return ItemRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private ItemDTO convertToDTO(Item Item) {
        ItemDTO dto = new ItemDTO();
        dto.setId(Item.getId());
        dto.setName(Item.getName());
        dto.setPrice(Item.getPrice());
        return dto;
    }

    public ItemDTO getItemById(Long id) {
        return ItemRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

}