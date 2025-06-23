package com.app.laptopshop.Repository;

import com.app.laptopshop.Entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrdersRepo extends JpaRepository<Orders, Long> {
    // Regular queries
    @Query("SELECT o FROM Orders o WHERE o.customer.customerid = :customerId")
    List<Orders> findByCustomerId(@Param("customerId") Long customerId);

    @Query("SELECT o FROM Orders o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    List<Orders> findByOrderDateRange(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate
    );

    // Paginated queries
    @Query("SELECT o FROM Orders o WHERE o.customer.customerid = :customerId")
    Page<Orders> findByCustomerId(@Param("customerId") Long customerId, Pageable pageable);

    @Query("SELECT o FROM Orders o WHERE o.orderDate BETWEEN :startDate AND :endDate")
    Page<Orders> findByOrderDateRange(
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        Pageable pageable
    );
}
