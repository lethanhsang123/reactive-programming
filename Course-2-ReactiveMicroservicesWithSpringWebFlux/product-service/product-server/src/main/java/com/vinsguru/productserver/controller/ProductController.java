package com.vinsguru.productserver.controller;

import com.vinsguru.productclient.dto.ProductDto;
import com.vinsguru.productserver.service.impl.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("")
    public Flux<ProductDto> getAll() {
        return this.productService.getAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ProductDto>> getById(@PathVariable String id) {
        return this.productService.getById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("")
    public Mono<ResponseEntity<ProductDto>> insert(@RequestBody Mono<ProductDto> dto) {
        return this.productService.insert(dto)
                .map(ResponseEntity::ok);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<ProductDto>> insert(@PathVariable String id, @RequestBody Mono<ProductDto> dto) {
        return this.productService.update(id, dto)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("{id}")
    public Mono<Void> delete(@PathVariable String id) {
        return this.productService.deleteProduct(id);
    }

    @GetMapping("price-range")
    public Flux<ProductDto> getByPriceRange(@RequestParam("min") int min,
                                            @RequestParam("max") int max) {
        return this.productService.getProductByPriceRange(min, max);
    }

}
