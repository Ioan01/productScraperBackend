package org.webscraper.productScraper.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.webscraper.productScraper.entities.Product;
import org.webscraper.productScraper.services.EntityService.ProductService;

import java.util.ArrayList;


@RestController
public class ProductController {


    ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @GetMapping("/a")

    public Page<Product> getProductsByName(@RequestParam String name,@RequestParam String orderBy,@RequestParam Sort.Direction direction, @RequestParam Integer page,@RequestParam Integer pageSize)
    {
        return productService.getByName(name,PageRequest.of(page,pageSize, direction,orderBy));
    }




}
