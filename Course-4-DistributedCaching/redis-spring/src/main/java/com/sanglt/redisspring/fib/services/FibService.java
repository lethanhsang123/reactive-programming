package com.sanglt.redisspring.fib.services;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class FibService {

    @Cacheable(value = "math:fib", key = "#index")
    public int getFib(int index) {
        System.out.println("calculating lib for " + index);
        return this.fib(index);
    }

    @CacheEvict(value = "math:fib", key = "#index")
    public void clearCache(int index) {
        System.out.println("Clearing cache");
    }

    private int fib(int index) {
        if (index < 2) return index;
        return fib(index - 1) + fib(index - 2);
    }

}
