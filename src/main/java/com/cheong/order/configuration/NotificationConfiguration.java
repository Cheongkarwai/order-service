package com.cheong.order.configuration;

import jakarta.persistence.Persistence;
import org.hibernate.reactive.stage.Stage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class NotificationConfiguration {

    @Bean
    public Stage.SessionFactory sessionFactory(){
        return Persistence.createEntityManagerFactory("postgres")
                .unwrap(Stage.SessionFactory.class);
    }

}
