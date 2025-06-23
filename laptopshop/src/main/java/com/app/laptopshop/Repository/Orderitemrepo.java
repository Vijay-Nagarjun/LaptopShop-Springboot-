package com.app.laptopshop.Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.laptopshop.Entity.OrderItem;
import java.util.List;

@Repository
public interface Orderitemrepo extends JpaRepository<OrderItem, Long> {
    // Regular queries
    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.orderid = :orderId")
    List<OrderItem> findByOrderId(@Param("orderId") Long orderId);

    @Query("SELECT oi FROM OrderItem oi WHERE oi.laptop.id = :laptopId")
    List<OrderItem> findByLaptopId(@Param("laptopId") Long laptopId);

    // Paginated queries
    @Override
    Page<OrderItem> findAll(Pageable pageable);

    @Query("SELECT oi FROM OrderItem oi WHERE oi.order.orderid = :orderId")
    Page<OrderItem> findByOrderId(@Param("orderId") Long orderId, Pageable pageable);

    @Query("SELECT oi FROM OrderItem oi WHERE oi.laptop.id = :laptopId")
    Page<OrderItem> findByLaptopId(@Param("laptopId") Long laptopId, Pageable pageable);

    @Query("SELECT oi FROM OrderItem oi WHERE oi.quantity >= :minQuantity")
    Page<OrderItem> findByMinimumQuantity(@Param("minQuantity") Integer minQuantity, Pageable pageable);
}