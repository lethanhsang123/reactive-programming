package com.vinsguru.orderserver.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "com.vinsguru.orderserver.repositories")
public class JpaDataSourceConfiguration {
}
