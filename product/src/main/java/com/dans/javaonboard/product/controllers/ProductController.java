package com.dans.javaonboard.product.controllers;

import com.dans.javaonboard.product.dtos.ProductDataDto;
import com.dans.javaonboard.product.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("product/v1/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping()
    public ResponseEntity<ProductDataDto> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @PostMapping("{productId}/activate")
    public ResponseEntity<String> activateProduct(@PathVariable("productId") String productId) {
        return ResponseEntity.ok(productService.activateProduct(productId, "system"));
    }
}
