package com.app.laptopshop.Services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.app.laptopshop.Entity.*;
import com.app.laptopshop.Repository.*;
import com.app.laptopshop.DTO.*;

@Service
public class Ordersservice {

    @Autowired
    private OrdersRepo orderRepository;

    @Autowired 
    private customerrepo customerRepository;

    @Autowired
    private LaptopRepo laptopRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Orders createOrder(OrderCreateDTO dto) {
        // Validate input
        if (dto == null) {
            throw new IllegalArgumentException("Order data cannot be null");
        }
        if (dto.getCustomerId() == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }
        if (dto.getItems() == null || dto.getItems().isEmpty()) {
            throw new IllegalArgumentException("Order must contain at least one item");
        }

        System.out.println("Creating order for customer ID: " + dto.getCustomerId());
        
        Orders order = new Orders();
        order.setOrderDate(dto.getOrderDate());
        
        try {
            // Fetch managed Customer entity
            Customer customer = customerRepository.findById(dto.getCustomerId())
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + dto.getCustomerId()));
            order.setCustomer(customer);
            
            // Initialize version
            order.setVersion(0);
            
            // Save the order first
            final Orders savedOrder = orderRepository.save(order);
            System.out.println("Saved order with ID: " + savedOrder.getOrderid());
            
            // Create and associate OrderItems
            List<OrderItem> items = dto.getItems().stream()
                .map(itemDto -> {
                    if (itemDto.getLaptopId() == null) {
                        throw new IllegalArgumentException("Laptop ID cannot be null");
                    }
                    
                    OrderItem item = new OrderItem();
                    item.setOrder(savedOrder);
                    item.setQuantity(itemDto.getQuantity());
                    
                    Laptop laptop = laptopRepository.findById(itemDto.getLaptopId())
                        .orElseThrow(() -> new RuntimeException("Laptop not found with ID: " + itemDto.getLaptopId()));
                    item.setLaptop(laptop);
                    
                    return item;
                })
                .collect(Collectors.toList());
            
            savedOrder.setOrderItems(items);
            return orderRepository.save(savedOrder);
        } catch (Exception e) {
            System.err.println("Error creating order: " + e.getMessage());
            throw e;
        }
    }

    public Orders getOrder(long id) {
        return orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    public List<Orders> getAllOrder() {
        return orderRepository.findAll();
    }

    public Page<Orders> getAllOrdersPaged(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    public Page<Orders> getOrdersByCustomerPaged(Long customerId, Pageable pageable) {
        return orderRepository.findByCustomerId(customerId, pageable);
    }

    public Page<Orders> getOrdersByDateRangePaged(LocalDate startDate, LocalDate endDate, Pageable pageable) {
        return orderRepository.findByOrderDateRange(startDate, endDate, pageable);
    }

    @Transactional
    public Orders updateOrder(Long id, Orders updatedOrder) {
        Orders existing = orderRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Order not found"));

        // Update only allowed fields
        existing.setOrderDate(updatedOrder.getOrderDate());
        existing.setCustomer(updatedOrder.getCustomer());

        return orderRepository.save(existing);
    }

    public void deleteOrder(long id) {
        orderRepository.deleteById(id);
    }
}
