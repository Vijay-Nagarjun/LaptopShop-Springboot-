package com.app.laptopshop.Controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
public class ProtectedController {
    @GetMapping("/api/protected/resource")
    public String protectedResource() {
        return "You have accessed a protected resource!";
    }
    
}
