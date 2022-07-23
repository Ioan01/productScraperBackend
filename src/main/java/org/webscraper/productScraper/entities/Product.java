package org.webscraper.groceryscraper.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.net.URI;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Product {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "productId", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "manufacturerId")
    private Manufacturer manufacturer;

    private String productName;

    private URI imageUri;

    private BigDecimal pricePerUnit;

    private String unit;

    @ManyToOne
    @JoinColumn(name = "storeId")
    private Store store;
}
