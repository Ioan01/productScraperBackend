package org.webscraper.productScraper.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@NoArgsConstructor
@Getter
public class CurrencyEntry implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    private Date date;
    private BigDecimal eur;
    private BigDecimal usd;

    public CurrencyEntry(BigDecimal eur, BigDecimal usd) {
        this.eur = eur;
        this.usd = usd;
        this.date = new Date(System.currentTimeMillis());
    }


}
