package com.example.order.service;

import java.util.ArrayList;

import com.example.order.client.ItemClient;
import com.example.order.dto.ItemDTO;
import com.example.order.dto.OrderDTO;
import com.example.order.entities.Order;
import com.example.order.repository.OrderRepository;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemClient itemClient;

    public OrderService(OrderRepository orderRepository , ItemClient itemClient) {
        this.orderRepository = orderRepository;
        this.itemClient = itemClient;
    }

    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = new Order();
        order.setCustomerName(orderDTO.getCustomerName());
        order.setItemIds(orderDTO.getItemIds());
        Order savedOrder = orderRepository.save(order);
        return convertToDTO(savedOrder);
    }

    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO getOrderById(Long id) {
        return orderRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }


    public OrderDTO convertToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setCustomerName(order.getCustomerName());
        dto.setItemIds(order.getItemIds());
        List<ItemDTO>list = new ArrayList<>();
        for(Long itemId : order.getItemIds())
        {
            ItemDTO item = getItemWithCircuitBreaker(itemId);
            list.add(item);
        }
        dto.setItems(list);
        dto.setOrderDate(order.getOrderDate());
        dto.setUpdatedDate(order.getUpdatedDate());
        return dto;
    }

    @CircuitBreaker(name = "itemService", fallbackMethod = "fallbackGetItem")
    public ItemDTO getItemWithCircuitBreaker(Long itemId) {
        return itemClient.getItemById(itemId);
    }

    public ItemDTO fallbackGetItem(Long itemId, Throwable t) {
        ItemDTO fallback = new ItemDTO();
        fallback.setId(itemId);
        fallback.setName("Item Service unavailable");
        fallback.setQuantity(0);
        return fallback;
    }
 
}