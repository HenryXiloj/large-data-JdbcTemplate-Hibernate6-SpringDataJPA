package com.henry.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "customers")
public class Customer {
    @Id
    private int id;
    private String name;

}
