package com.maveric.gatewayservice.configuration;

import com.maveric.gatewayservice.filter.JwtAuthFilter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.stream.Collectors;

@Configuration
public class GatewayConfiguration {

    @Value("${authentication-authorization-service.path}")
    public String authServicePath;
    @Value("${authentication-authorization-service.uri}")
    public String authServiceUri;
    @Value("${user-service.path}")
    public String userServicePath;
    @Value("${user-service.uri}")
    public String userServiceUri;

    @Value("${account-service.path}")
    public String accountServicePath;

    @Value("${account-service.uri}")
    public String accountServiceUri;

    @Value("${balance-service.path}")
    public String balanceServicePath;

    @Value("${balance-service.uri}")
    public String balanceServiceUri;

    @Value("${transaction-service.path}")
    public String transactionServicePath;

    @Value("${transaction-service.uri}")
    public String transactionServiceUri;

    @Autowired
    private JwtAuthFilter filter;

    @Bean
    public RouteLocator gatewayRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("authentication-authorization-service",
                        r -> r.path(authServicePath,"/api/v1/hello").filters(f -> f.filter(filter)).uri(authServiceUri))
                .route("user-service",
                        r -> r.path(userServicePath).filters(f -> f.filter(filter)).uri(userServiceUri))
                .route("account-service",
                        r -> r.path(accountServicePath).filters(f -> f.filter(filter)).uri(accountServiceUri))
                .route("balance-service",
                        r -> r.path(balanceServicePath).filters(f -> f.filter(filter)).uri(balanceServiceUri))
                .route("transaction-service",
                        r -> r.path(transactionServicePath).filters(f -> f.filter(filter)).uri(transactionServiceUri))
                .build();
    }

    /*@Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters (ObjectProvider<HttpMessageConverter<?>> converters)
    {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    } */
}
