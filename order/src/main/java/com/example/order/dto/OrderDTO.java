package com.example.order.dto;

import java.time.LocalDateTime;

import lombok.*;
import java.util.List;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor

public class OrderDTO {
    private Long id;
    private String customerName;
    private List<Long> itemIds;
    private List<ItemDTO> items;
    private LocalDateTime orderDate;
    private LocalDateTime updatedDate;
}