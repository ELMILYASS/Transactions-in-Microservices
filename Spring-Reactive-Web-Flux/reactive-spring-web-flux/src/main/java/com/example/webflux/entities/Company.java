package com.example.webflux.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data @AllArgsConstructor @NoArgsConstructor @ToString @Builder
@Document
public class Company {
    @Id
    private String id;
    private String name;
    private double price;
}
