package com.app.laptopshop.Services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.app.laptopshop.Repository.LaptopRepo;
import com.app.laptopshop.Entity.Laptop;
@Service
public class Laptopservice {
@Autowired
private LaptopRepo laptopRepository;
public Laptop createLaptop(Laptop laptop) {
    return laptopRepository.save(laptop);
}
public Laptop getLaptop(long id) {
    return laptopRepository.findById(id).orElseThrow(() -> new RuntimeException("Laptop not found"));
}
public List<Laptop> getAllLaptops() {
    return laptopRepository.findAll();
}
public Page<Laptop> getAllLaptopsPaged(Pageable pageable) {
    return laptopRepository.findAll(pageable);
}
public Page<Laptop> getLaptopsByCategory(String category, Pageable pageable) {
    return laptopRepository.findByCategory(category, pageable);
}
public Page<Laptop> getLaptopsByBrand(String brand, Pageable pageable) {
    return laptopRepository.findByBrand(brand, pageable);
}
public Laptop updateLaptop(Long id, Laptop updatedLaptop) {
    Laptop existing = laptopRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Laptop not found"));

    // Update only allowed fields
    existing.setName(updatedLaptop.getName());
    existing.setBrand(updatedLaptop.getBrand());
    existing.setPrice(updatedLaptop.getPrice());
    existing.setCategory(updatedLaptop.getCategory());

    // Save the updated entity
    return laptopRepository.save(existing);
}
public void deleteLaptop(long id) {
    laptopRepository.deleteById(id);
}
}