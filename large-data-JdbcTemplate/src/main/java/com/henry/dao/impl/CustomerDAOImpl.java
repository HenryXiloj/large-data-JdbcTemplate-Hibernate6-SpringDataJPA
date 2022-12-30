package com.henry.dao.impl;

import com.henry.dao.CustomerDAO;
import com.henry.model.Customer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class CustomerDAOImpl implements CustomerDAO {

    private final JdbcTemplate jdbcTemplate;

    public CustomerDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int findAll() {
        long start = System.nanoTime();

        String sql = """
                      SELECT  * from customers
                      """ ;
        //jdbcTemplate.setFetchSize(1000); with Oracle increase to performance
        var list = jdbcTemplate.query(sql,   (rs, rowNum) ->
                Customer.builder()
                        .id(rs.getInt("id"))
                        .name(rs.getString("name"))
                        .build()
        );
        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("millis " + duration);
       return list.size();
    }

}
