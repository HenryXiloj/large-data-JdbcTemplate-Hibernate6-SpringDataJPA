package com.henry.service.impl;

import com.henry.dao.CustomerRepository;
import com.henry.service.CustomerService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerDAO;

    public CustomerServiceImpl(CustomerRepository customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    @Transactional(readOnly = true)
    public int findAll() {
        System.out.println("start");
        long start = System.nanoTime();

        int i=0;
        int counter=0;
        int batch = 1000000;

        var list =
                customerDAO
                        .myQuery(
                                PageRequest.of(i, batch))
                        .collect(Collectors.toList());

        counter = list.size();

        /*while(list.size() == batch){
            i+=batch;
            System.out.println("i "+i);
            list =
                    customerDAO
                            .myQuery(
                                    PageRequest.of(i, batch))
                            .collect(Collectors.toList());
            counter=counter + list.size();
        }*/

        System.out.println("list "+counter);

        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("millis " + duration);

        return counter;

    }

    @Override
    public int findAllWithEM() {
        return customerDAO.findSizeCustom();
    }
}
