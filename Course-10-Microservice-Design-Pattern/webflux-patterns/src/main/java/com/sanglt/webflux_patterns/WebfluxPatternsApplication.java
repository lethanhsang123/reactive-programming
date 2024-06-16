package com.sanglt.webflux_patterns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
//		"com.sanglt.webflux_patterns.sec01_gateway_aggregator",
//		"com.sanglt.webflux_patterns.sec02_scatter_gather",
//		"com.sanglt.webflux_patterns.sec03_orchestrator",
		"com.sanglt.webflux_patterns.sec04",
})
public class WebfluxPatternsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxPatternsApplication.class, args);
	}

}
