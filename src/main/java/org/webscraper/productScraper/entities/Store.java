package org.webscraper.productScraper.entities;

import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;
import java.net.URI;

@Entity
@Getter
public class Store implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "storeId", nullable = false)
    private Long id;


    @Column(unique = true)
    private String storeName;

    private URI webSite;

    private URI icon;

}
