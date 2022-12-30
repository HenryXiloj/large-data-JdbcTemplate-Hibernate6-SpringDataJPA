package com.henry;

import com.henry.config.AppConfig;
import com.henry.dao.CustomerDAO;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App1 {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        CustomerDAO customerDAO = context.getBean(CustomerDAO.class);

        System.out.println("list "+customerDAO.findAll());

    }
}
