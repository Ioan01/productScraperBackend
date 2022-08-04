package org.webscraper.productScraper.dto;


import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class MIProductsDTO {

    private Map<String, Object> data;

}
