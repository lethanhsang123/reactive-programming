package com.vinsguru.productserver.service;

import com.vinsguru.productclient.dto.ProductDto;
import com.vinsguru.productserver.repository.ProductRepository;
import com.vinsguru.productserver.service.impl.ProductService;
import com.vinsguru.productserver.util.EntityDtoUtil;
import org.springframework.data.domain.Range;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Flux<ProductDto> getAll() {
        return this.productRepository.findAll()
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> getById(String id) {
        return this.productRepository.findById(id).map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> insert(Mono<ProductDto> dto) {
        return dto.map(EntityDtoUtil::toEntity)
                .flatMap(this.productRepository::insert)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<ProductDto> update(String id, Mono<ProductDto> dto) {
        return this.productRepository.findById(id)
                .flatMap(p -> dto
                        .map(EntityDtoUtil::toEntity)
                        .doOnNext(e -> e.setId(id)))
                .flatMap(this.productRepository::save)
                .map(EntityDtoUtil::toDto);
    }

    public Mono<Void> deleteProduct(String id) {
        return this.productRepository.deleteById(id);
    }

    @Override
    public Flux<ProductDto> getProductByPriceRange(int min, int max) {
        return this.productRepository.findByPriceBetween(Range.closed(min, max))
                .map(EntityDtoUtil::toDto);
    }


}
