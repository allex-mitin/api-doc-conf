package org.example.backend.apifirst.config;

import org.example.info.client.pet.client.PetApi;
import org.example.info.client.user.client.UserApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
public class ClientConfig {

    @Bean
    @Scope("prototype")
    RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }

    @Bean
    public PetApi petApi(org.example.info.client.pet.ApiClient apiClient) {
        return new PetApi(apiClient);
    }

    @Bean
    public org.example.info.client.pet.ApiClient petApiClient(
            RestTemplateBuilder restTemplateBuilder,
            @Value("${rest-client.pet-url}") String url
    ) {
        return new org.example.info.client.pet.ApiClient(
                restTemplateBuilder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(url))
                        .build()
        );
    }

    @Bean
    public UserApi userApi(org.example.info.client.user.ApiClient apiClient) {
        return new UserApi(apiClient);
    }

    @Bean
    public org.example.info.client.user.ApiClient userApiClient(
            RestTemplateBuilder restTemplateBuilder,
            @Value("${rest-client.user-url}") String url
    ) {
        return new org.example.info.client.user.ApiClient(
                restTemplateBuilder
                        .uriTemplateHandler(new DefaultUriBuilderFactory(url))
                        .build()
        );
    }


}
