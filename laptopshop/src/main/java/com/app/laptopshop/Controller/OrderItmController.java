package com.app.laptopshop.Controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import com.app.laptopshop.Entity.OrderItem;
import com.app.laptopshop.Services.OrderItmservice;
import com.app.laptopshop.DTO.PageDTO;

@RestController
@RequestMapping("/api/orderitems")
public class OrderItmController {
    @Autowired
    private OrderItmservice orderItemService;

    // Existing CRUD endpoints
    @PostMapping
    public ResponseEntity<OrderItem> create(@RequestBody OrderItem orderItem) {
        return new ResponseEntity<>(orderItemService.createOrderItem(orderItem), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItem> get(@PathVariable long id) {
        OrderItem orderItem = orderItemService.getOrderItem(id);
        if (orderItem != null) {
            return new ResponseEntity<>(orderItem, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public List<OrderItem> getAll() {
        return orderItemService.getAllOrderItems();
    }

    // Updated paginated endpoints
    @GetMapping("/paged")
    public ResponseEntity<PageDTO<OrderItem>> getAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "orderitemid") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        try {
            Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? 
                Sort.Direction.ASC : Sort.Direction.DESC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
            Page<OrderItem> itemPage = orderItemService.getAllOrderItemsPaged(pageable);
            return ResponseEntity.ok(new PageDTO<>(itemPage));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/paged/order/{orderId}")
    public ResponseEntity<PageDTO<OrderItem>> getByOrderPaged(
            @PathVariable Long orderId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("orderitemid").ascending());
            Page<OrderItem> itemPage = orderItemService.getOrderItemsByOrderPaged(orderId, pageable);
            return ResponseEntity.ok(new PageDTO<>(itemPage));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/paged/laptop/{laptopId}")
    public ResponseEntity<PageDTO<OrderItem>> getByLaptopPaged(
            @PathVariable Long laptopId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("orderitemid").ascending());
            Page<OrderItem> itemPage = orderItemService.getOrderItemsByLaptopPaged(laptopId, pageable);
            return ResponseEntity.ok(new PageDTO<>(itemPage));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderItem> update(@PathVariable Long id, @RequestBody OrderItem orderItem) {
        try {
            return ResponseEntity.ok(orderItemService.updateOrderItem(id, orderItem));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        try {
            orderItemService.deleteOrderItem(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
