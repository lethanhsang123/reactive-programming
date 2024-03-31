package com.sanglt.redisperformance.services.utils;

import reactor.core.publisher.Mono;

public abstract class CacheTemplate<K, E> {

    public Mono<E> get(K key) {
        return getFromCache(key)
                .switchIfEmpty(
                        getFromSource(key)
                                .flatMap(e -> updateCache(key, e))
                );
    }

    public Mono<E> update(K key, E entity) {
        return updateSource(key, entity)
                .flatMap(e -> deleteFromCache(key).thenReturn(e));
    }

    public Mono<Void> delete(K key) {
        return deleteFromSource(key)
                .then(deleteFromCache(key));
    }


    abstract protected Mono<E> getFromSource(K key);
    abstract protected Mono<E> getFromCache(K key);
    abstract protected Mono<E> updateSource(K key, E entity);
    abstract protected Mono<E> updateCache(K key, E entity);
    abstract protected Mono<Void> deleteFromSource(K key);
    abstract protected Mono<Void> deleteFromCache(K key);

}
