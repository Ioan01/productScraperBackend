package org.webscraper.productScraper.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.webscraper.productScraper.services.EntityService.ProductService;
import org.webscraper.productScraper.services.scrapers.AsyncTest;
import org.webscraper.productScraper.services.scrapers.MIScraper;


@RestController
public class ProductController {


    @Autowired
    AsyncTest test;
    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("test")
    public String test()
    {
        test.test();

        return "a";
    }

}
