package com.sanglt.redisperformance.services.utils;

import com.sanglt.redisperformance.entities.Product;
import com.sanglt.redisperformance.repositories.ProductRepository;
import org.redisson.api.LocalCachedMapOptions;
import org.redisson.api.RLocalCachedMap;
import org.redisson.api.RedissonClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import reactor.core.publisher.Mono;

//@Service
public class ProductLocalCacheTemplate extends CacheTemplate<Integer, Product> {

    private final ProductRepository productRepository;
    private final RLocalCachedMap<Integer, Product> map;

    public ProductLocalCacheTemplate(ProductRepository productRepository, RedissonClient client) {
        this.productRepository = productRepository;
        LocalCachedMapOptions<Integer, Product> mapOptions = LocalCachedMapOptions.<Integer, Product>defaults()
                .syncStrategy(LocalCachedMapOptions.SyncStrategy.UPDATE)
                .reconnectionStrategy(LocalCachedMapOptions.ReconnectionStrategy.CLEAR);
        this.map = client.getLocalCachedMap("product", new TypedJsonJacksonCodec(Integer.class, Product.class), mapOptions);
    }

    @Override
    protected Mono<Product> getFromSource(Integer id) {
        return this.productRepository.findById(id);
    }

    @Override
    protected Mono<Product> getFromCache(Integer id) {
        return Mono.justOrEmpty(this.map.get(id));
    }

    @Override
    protected Mono<Product> updateSource(Integer id, Product product) {
        return this.productRepository.findById(id)
                .doOnNext(p -> product.setId(id))
                .flatMap(p -> this.productRepository.save(product));
    }

    @Override
    protected Mono<Product> updateCache(Integer id, Product product) {
        return Mono.create(sink -> {
            this.map.fastPutAsync(id, product)
                    .thenAccept(b -> sink.success(product))
                    .exceptionally(ex -> {
                        sink.error(ex);
                        return null;
                    });
        });
    }

    @Override
    protected Mono<Void> deleteFromSource(Integer id) {
        return this.productRepository.deleteById(id);
    }

    @Override
    protected Mono<Void> deleteFromCache(Integer id) {
        return Mono.create(sink -> {
            this.map.fastRemoveAsync(id)
                    .thenAccept(b -> sink.success())
                    .exceptionally(ex -> {
                        sink.error(ex);
                        return null;
                    });
        });
    }
}
