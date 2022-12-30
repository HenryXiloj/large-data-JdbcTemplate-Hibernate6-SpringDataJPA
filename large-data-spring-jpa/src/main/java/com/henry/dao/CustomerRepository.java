package com.henry.dao;

import com.henry.model.Customer;
import jakarta.persistence.QueryHint;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.stream.Stream;

import static org.hibernate.jpa.HibernateHints.HINT_CACHEABLE;
import static org.hibernate.jpa.HibernateHints.HINT_READ_ONLY;

public interface CustomerRepository extends PagingAndSortingRepository<Customer, Integer>, CustomerRepositoryCustom {
    @QueryHints(value = {
            @QueryHint(name = HINT_CACHEABLE, value = "false"),
            @QueryHint(name = HINT_READ_ONLY, value = "true")
           // @QueryHint(name = HINT_FETCH_SIZE, value = "1000")
    })
    @Query(value="SELECT c from customers c")
    Stream<Customer> myQuery(Pageable pageable);
}
