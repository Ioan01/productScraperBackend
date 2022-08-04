package org.webscraper.productScraper.pojo;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.webscraper.productScraper.entities.Product;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@NoArgsConstructor
public class MIProductPOJO {

    private static Pattern pricePattern = Pattern.compile("^[\\d]+[.,]*[\\d]*");
    private static Pattern unitPattern = Pattern.compile("(?<=Lei/)[\\w]+");

    private static String baseImageUrl = "https://d1lqpgkqcok0l.cloudfront.net";

    private String manufacturerName;

    private String manufacturerSubBrandName;

    private String name;

    private ArrayList<Object> images;

    private Map<String, Object> price;


    private String getUnit() {
        String unit = "piece";

        String supplementaryPriceLabel2 = (String) price.get("supplementaryPriceLabel2");
        if (supplementaryPriceLabel2 == null)
            return unit;

        if (price.get("unitCode").equals("kilogram"))
            return "Kg";

        Matcher matcher = unitPattern.matcher(supplementaryPriceLabel2);
        if (!matcher.find())
            return unit;
        unit = matcher.group();

        return unit;
    }

    private Double getPrice() {

        Double price = (Double) this.price.get("value");

        String supplementaryPriceLabel2 = (String) this.price.get("supplementaryPriceLabel2");
        if (supplementaryPriceLabel2 == null)
            return price;

        Matcher matcher = pricePattern.matcher(supplementaryPriceLabel2);
        if (!matcher.find())
            return price;
        price = Double.valueOf(matcher.group());

        return price;
    }

    public Product toProduct() {

        String unit = getUnit();
        Double price = getPrice();

        String thumbnail = ((Map<String, String>) images.get(0)).get("url");


        return new Product(name, URI.create(baseImageUrl + thumbnail), BigDecimal.valueOf(price), unit);
    }

}
