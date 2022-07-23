package org.webscraper.productScraper.entities;

import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "categoryId", nullable = false)
    private Long id;


    @Column(unique = true)
    private String categoryName;



}
