package org.webscraper.productScraper.entities;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.regex.Pattern;

@AllArgsConstructor
@Data
public class QuantityAndUnit {
    private static final Pattern quantityRegex = Pattern
            .compile("[0-9]+[.,]?[0-9]*",Pattern.CASE_INSENSITIVE);
    private static final Pattern quantityAndUnitRegex = Pattern
            .compile("([0-9]+[.,]?[0-9]* *)(([mk]?[gl])|bucati)",Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);



    private BigDecimal quantity;
    private String unit;

    public static QuantityAndUnit getPriceAndUnit(String productName)
    {
        var match = quantityAndUnitRegex.matcher(productName);



        if (!match.find(0))
            return new QuantityAndUnit(BigDecimal.ONE,"piece");
        if (match.results().count() > 1)
        {
            System.out.println("");
        }
        match.find(0);

        BigDecimal quantity = BigDecimal.valueOf(Double.parseDouble(match.group(1).stripTrailing()));

        String unit = match.group(2).toLowerCase();

        switch (unit)
        {
            case "mg":
                quantity = quantity.divide(BigDecimal.valueOf(1000*1000.0),4,RoundingMode.HALF_EVEN);
            case "g" :
                quantity = quantity.divide(BigDecimal.valueOf(1000.0),4, RoundingMode.HALF_EVEN);
                break;
            case "ml":
                quantity = quantity.divide(BigDecimal.valueOf(1000.0), 4,RoundingMode.HALF_EVEN);
                break;
        }


        if (unit.contains("g"))
            unit = "Kg";
        else if (unit.contains("l")) {
            unit = "L";
        }
        else unit = "piece";



        return new QuantityAndUnit(quantity,unit);
    }

}
