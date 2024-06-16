package com.sanglt.webflux_patterns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {
//		"com.sanglt.webflux_patterns.sec01_gateway_aggregator",
//		"com.sanglt.webflux_patterns.sec02_scatter_gather",
//		"com.sanglt.webflux_patterns.sec03_orchestrator",
//		"com.sanglt.webflux_patterns.sec04",
//		"com.sanglt.webflux_patterns.sec05_splitter_pattern",
//		"com.sanglt.webflux_patterns.sec06_timeout_pattern",
//		"com.sanglt.webflux_patterns.sec07_retry_pattern",
//		"com.sanglt.webflux_patterns.sec08_circuit_breaker_pattern",
//		"com.sanglt.webflux_patterns.sec09_rate_limiter_pattern",
		"com.sanglt.webflux_patterns.sec10_bulk_head_pattern",
})
public class WebfluxPatternsApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxPatternsApplication.class, args);
	}

}
