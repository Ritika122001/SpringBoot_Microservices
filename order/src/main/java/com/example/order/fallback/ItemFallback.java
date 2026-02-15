package com.example.order.fallback;

import org.springframework.stereotype.Component;

import com.example.order.dto.ItemDTO;
import com.example.order.client.ItemClient;

@Component 
public class ItemFallback implements ItemClient{


    public ItemDTO getItemById(Long id) 
    {
        ItemDTO fallbackItem = new ItemDTO();
        fallbackItem.setId(id);
        fallbackItem.setName("Item Service unavailable");
        fallbackItem.setQuantity(0);
        return fallbackItem;

    }
    
}
