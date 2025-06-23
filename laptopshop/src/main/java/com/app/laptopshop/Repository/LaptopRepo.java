package com.app.laptopshop.Repository;

import com.app.laptopshop.Entity.Laptop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaptopRepo extends JpaRepository<Laptop, Long> {
    // Regular queries
    @Query("SELECT l FROM Laptop l WHERE l.brand = :brand")
    List<Laptop> findByBrand(@Param("brand") String brand);

    @Query("SELECT l FROM Laptop l WHERE l.price BETWEEN :minPrice AND :maxPrice")
    List<Laptop> findByPriceRange(@Param("minPrice") Double minPrice, @Param("maxPrice") Double maxPrice);

    // Paginated queries
    @Query("SELECT l FROM Laptop l WHERE l.brand = :brand")
    Page<Laptop> findByBrand(@Param("brand") String brand, Pageable pageable);

    @Query("SELECT l FROM Laptop l WHERE l.category = :category")
    Page<Laptop> findByCategory(@Param("category") String category, Pageable pageable);

    @Query("SELECT l FROM Laptop l WHERE l.price BETWEEN :minPrice AND :maxPrice")
    Page<Laptop> findByPriceRange(
        @Param("minPrice") Double minPrice,
        @Param("maxPrice") Double maxPrice,
        Pageable pageable
    );
}
