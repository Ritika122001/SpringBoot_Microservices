package com.example.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.order.dto.ItemDTO;

@Component
@FeignClient(name = "ITEM-SERVICE")
public interface ItemClient {

    @GetMapping("/api/Items/{id}")
    ItemDTO getItemById(@PathVariable("id") Long id);
}
