package com.app.laptopshop.Services;
import java.util.List;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.laptopshop.Entity.OrderItem;
import com.app.laptopshop.Entity.Orders;
import com.app.laptopshop.Repository.Orderitemrepo;
import com.app.laptopshop.Repository.OrdersRepo;
@Service
public class OrderItmservice {
@Autowired
private Orderitemrepo orderItemRepository;
@Autowired
private OrdersRepo orderRepository;
public OrderItem createOrderItem(OrderItem orderItem) {
    // Fetch the managed Orders entity from the database
    Orders existingOrder = orderRepository.findById(orderItem.getOrder().getOrderid())
        .orElseThrow(() -> new EntityNotFoundException("Order not found with ID: " + orderItem.getOrder().getOrderid()));

    // Set the fully initialized and managed order into order item
    orderItem.setOrder(existingOrder);

    return orderItemRepository.save(orderItem);
}
public OrderItem getOrderItem(long id) {
    return orderItemRepository.findById(id).orElseThrow(() -> new RuntimeException("Order Item not found"));
}
public List<OrderItem> getAllOrderItems() {
    return orderItemRepository.findAll();
}
// New paginated methods
public Page<OrderItem> getAllOrderItemsPaged(Pageable pageable) {
    return orderItemRepository.findAll(pageable);
}
public Page<OrderItem> getOrderItemsByOrderPaged(Long orderId, Pageable pageable) {
    return orderItemRepository.findByOrderId(orderId, pageable);
}
public Page<OrderItem> getOrderItemsByLaptopPaged(Long laptopId, Pageable pageable) {
    return orderItemRepository.findByLaptopId(laptopId, pageable);
}
@Transactional
public OrderItem updateOrderItem(Long id, OrderItem updatedOrderItem) {
    OrderItem existing = orderItemRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("OrderItem not found"));

    // Update only allowed fields
    existing.setQuantity(updatedOrderItem.getQuantity());
    if (updatedOrderItem.getOrder() != null) {
        Orders existingOrder = orderRepository.findById(updatedOrderItem.getOrder().getOrderid())
            .orElseThrow(() -> new EntityNotFoundException("Order not found"));
        existing.setOrder(existingOrder);
    }
    existing.setLaptop(updatedOrderItem.getLaptop());

    // Save the updated entity
    return orderItemRepository.save(existing);
}
public void deleteOrderItem(long id) {
    orderItemRepository.deleteById(id);
}
}