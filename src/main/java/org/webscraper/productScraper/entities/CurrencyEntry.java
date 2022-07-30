package org.webscraper.productScraper.entities;


import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@NoArgsConstructor
@Getter
public class CurrencyEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Temporal(TemporalType.DATE)
    private Date date;

    private BigDecimal conversionRate;

    public void setId(Long id) {
        this.id = id;
    }

    public CurrencyEntry(String date, BigDecimal conversionRate) throws ParseException {
        this.date = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        this.conversionRate = conversionRate;
    }
}
