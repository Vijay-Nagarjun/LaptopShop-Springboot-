package com.app.laptopshop.Services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.app.laptopshop.Repository.customerrepo;
import com.app.laptopshop.Entity.Customer;
@Service
public class Customerservice {
    @Autowired
    private customerrepo customerRepository;
    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    public Customer getCustomer(long id) {
        return customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
    }
public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }
    public Page<Customer> getAllCustomersPaged(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }
    public Page<Customer> searchCustomers(String searchTerm, Pageable pageable) {
        return customerRepository.searchCustomers(searchTerm, pageable);
    }
    public Customer updateCustomer(Long id, Customer updatedCustomer) {
        Customer existing = getCustomer(id);

        // Update only allowed fields
        existing.setName(updatedCustomer.getName());
        existing.setEmail(updatedCustomer.getEmail());
        existing.setPhone(updatedCustomer.getPhone());
        existing.setAddress(updatedCustomer.getAddress());

        // Save the updated entity
        return customerRepository.save(existing);
    }
    public void deleteCustomer(long id) {
        customerRepository.deleteById(id);
    } 



}
