package com.huiun.fizzybudget.userservice.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@ComponentScan(basePackages = "com.huiun.fizzybudget.common")
@EnableJpaRepositories(basePackages = "com.huiun.fizzybudget.common.repository")
@EntityScan(basePackages = "com.huiun.fizzybudget.common.entities") // Adjust the package to your shared entities package
public class AppConfig {
}
