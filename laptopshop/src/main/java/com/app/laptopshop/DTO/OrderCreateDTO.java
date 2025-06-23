package com.app.laptopshop.DTO;

import java.time.LocalDate;
import java.util.List;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotEmpty;

public class OrderCreateDTO {
    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;
    
    @NotNull(message = "Order date cannot be null")
    private LocalDate orderDate;
    
    @NotNull(message = "Items cannot be null")
    @NotEmpty(message = "Items cannot be empty")
    private List<OrderItemDTO> items;

    public OrderCreateDTO() {
    }

    public OrderCreateDTO(Long customerId, LocalDate orderDate, List<OrderItemDTO> items) {
        this.customerId = customerId;
        this.orderDate = orderDate;
        this.items = items;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public List<OrderItemDTO> getItems() {
        return items;
    }

    public void setItems(List<OrderItemDTO> items) {
        this.items = items;
    }
}
