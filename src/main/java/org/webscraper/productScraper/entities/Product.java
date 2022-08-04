package org.webscraper.productScraper.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.net.URI;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@With
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"product_name", "store_id"})
})
public class Product implements Serializable {

    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id", nullable = false)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private Manufacturer manufacturer;
    @Column(name = "product_name")
    private String productName;
    private URI imageUri;
    private BigDecimal pricePerUnit;
    private String unit;
    @ManyToOne
    @JoinColumn(name = "store_id")
    private Store store;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;


    public Product(String productName, URI imageUri, BigDecimal pricePerUnit, String unit) {
        this.productName = productName;
        this.imageUri = imageUri;
        this.pricePerUnit = pricePerUnit;
        this.unit = unit;
    }
}
