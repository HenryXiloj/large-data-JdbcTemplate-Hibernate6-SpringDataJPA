package com.henry.dao.impl;

import com.henry.dao.CustomerRepositoryCustom;
import com.henry.model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

public class CustomerRepositoryCustomImpl implements CustomerRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public int findSizeCustom() {
        long start = System.nanoTime();

        int i=0;
        int counter=0;
        int batch = 1000000; // per batch
        var  list = entityManager
                .createQuery("SELECT e FROM customers e", Customer.class)
                //.setHint("org.hibernate.fetchSize", 1000)
                .setFirstResult(i)
                .setMaxResults(batch)
                .getResultList();

        counter = list.size();

        while(list.size() == batch){
            i+=batch;
            list = entityManager
                    .createQuery("SELECT e FROM customers e", Customer.class)
                    //.setHint("org.hibernate.fetchSize", 1000)
                    .setFirstResult(i)
                    .setMaxResults(batch)
                    .getResultList();
            counter=counter + list.size();
        }


        System.out.println("list "+counter);

        long duration = (System.nanoTime() - start) / 1_000_000;
        System.out.println("millis " + duration);
        return counter;
    }
}
