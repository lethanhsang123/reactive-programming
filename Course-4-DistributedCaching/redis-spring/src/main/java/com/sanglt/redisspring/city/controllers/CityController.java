package com.sanglt.redisspring.city.controllers;

import com.sanglt.redisspring.city.dtos.City;
import com.sanglt.redisspring.city.services.CityService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/city")
@AllArgsConstructor
public class CityController {

    private CityService cityService;

    @GetMapping("{zipcode}")
    public Mono<City> getCity(@PathVariable String zipcode) {
        return this.cityService.getCity(zipcode);
    }

}
