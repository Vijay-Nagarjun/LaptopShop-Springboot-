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

import com.app.laptopshop.Entity.Laptop;
import com.app.laptopshop.Services.Laptopservice;
import com.app.laptopshop.DTO.PageDTO;

@RestController
@RequestMapping("/api/laptops")
public class LaptopController {
    @Autowired
    private Laptopservice laptopService;

    @PostMapping
    public ResponseEntity<Laptop> create(@RequestBody Laptop laptop) {
       return new ResponseEntity<>(laptopService.createLaptop(laptop), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Laptop> get(@PathVariable long id) {
        Laptop laptop = laptopService.getLaptop(id);
        if (laptop != null) {
            return new ResponseEntity<>(laptop, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping
    public List<Laptop> getAll() {
        return laptopService.getAllLaptops();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Laptop> update(@PathVariable Long id, @RequestBody Laptop laptop) {
        return ResponseEntity.ok(laptopService.updateLaptop(id, laptop));
}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        laptopService.deleteLaptop(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // New paginated endpoints
    @GetMapping("/paged")
    public ResponseEntity<PageDTO<Laptop>> getAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {
        try {
            Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ? 
                Sort.Direction.ASC : Sort.Direction.DESC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
            Page<Laptop> laptopPage = laptopService.getAllLaptopsPaged(pageable);
            return ResponseEntity.ok(new PageDTO<>(laptopPage));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/paged/category/{category}")
    public ResponseEntity<PageDTO<Laptop>> getLaptopsByCategory(
            @PathVariable String category,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("price").ascending());
            Page<Laptop> laptopPage = laptopService.getLaptopsByCategory(category, pageable);
            return ResponseEntity.ok(new PageDTO<>(laptopPage));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/paged/brand/{brand}")
    public ResponseEntity<PageDTO<Laptop>> getLaptopsByBrand(
            @PathVariable String brand,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("price").ascending());
            Page<Laptop> laptopPage = laptopService.getLaptopsByBrand(brand, pageable);
            return ResponseEntity.ok(new PageDTO<>(laptopPage));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
