package org.webscraper.productScraper.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.With;

import javax.persistence.*;
import java.io.Serializable;
import java.net.URI;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@With
public class Store implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id", nullable = false)
    private Long id;


    @Column(unique = true)
    private String storeName;

    private URI webSite;

    private URI icon;

}
