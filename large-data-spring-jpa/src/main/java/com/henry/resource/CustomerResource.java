package com.henry.resource;

import com.henry.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
public class CustomerResource {

    private final CustomerService  customerService;

    public CustomerResource(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/findAll")
    public int findAll(){
        return customerService.findAll();
    }

    @GetMapping("/findAllWithEM")
    public int findAllWithEM(){
        return customerService.findAllWithEM();
    }
}
