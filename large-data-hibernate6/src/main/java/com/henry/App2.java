package com.henry;

import com.henry.model.Customer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class App2
{
    public static void main( String[] args )
    {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

        System.out.println("Starting Transaction");
        entityManager.getTransaction().begin();

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

        // close the entity manager
        entityManager.close();
        entityManagerFactory.close();

    }
}
