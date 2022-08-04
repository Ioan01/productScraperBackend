package org.webscraper.productScraper.entities;

import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@NoArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"date", "product_id"})
})
public class ProductPriceNode implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private BigDecimal price;

    private Date date;

    public ProductPriceNode(Product product) {
        this.product = product;
        this.price = product.getPricePerUnit();
        date = new Date(System.currentTimeMillis());
    }
}
