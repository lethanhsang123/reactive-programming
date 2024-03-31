package com.sanglt.redisspring.city.services;

import com.sanglt.redisspring.city.clients.CityClient;
import com.sanglt.redisspring.city.dtos.City;
import org.redisson.api.RMapCacheReactive;
import org.redisson.api.RMapReactive;
import org.redisson.api.RedissonReactiveClient;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CityService {

    private final CityClient client;

    private RMapReactive<String, City> cityMap;

    public CityService(RedissonReactiveClient redissonReactiveClient, CityClient client) {
        this.cityMap = redissonReactiveClient.getMap("city", new TypedJsonJacksonCodec(String.class, City.class));
        this.client = client;
    }

    public Mono<City> getCity(final String zipCode) {
        return this.cityMap.get(zipCode)
                .switchIfEmpty(this.client.getCity(zipCode))
                .onErrorResume(ex -> this.client.getCity(zipCode));
    }

    @Scheduled(fixedRate = 10_00)
    public void updateCity() {
        this.client.getAll()
                .collectList()
                .map(list -> list.stream().collect(Collectors.toMap(City::getZip, Function.identity())))
                .flatMap(this.cityMap::putAll)
                .subscribe();
    }


    /**
     * Get from cache
     * if empty -> get from resource
     * and put in cache
     * @param zipCode
     * @return
     */
//    public Mono<City> getCity(final String zipCode) {
//        return this.cityMap.get(zipCode)
//                .switchIfEmpty(
//                        this.client.getCity(zipCode)
//                                .flatMap(c -> this.cityMap.fastPut(zipCode, c).thenReturn(c))
//                );
//    }

}
