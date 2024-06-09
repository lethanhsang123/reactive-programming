package com.vinsguru.productserver.configurations;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.vinsguru.productserver.repositories")
public class R2dbConfiguration {
}
