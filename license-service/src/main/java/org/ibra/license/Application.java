package org.ibra.license;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableCircuitBreaker
@RefreshScope
public class Application {

	@LoadBalanced
	@Bean
	public RestTemplate getRibbonRestTemplate() {
		
		return new RestTemplate();
	}
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}