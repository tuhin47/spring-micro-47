package me.tuhin47.awssamples.s3.config;

import io.r2dbc.spi.ConnectionFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.r2dbc.connection.init.ConnectionFactoryInitializer;
import org.springframework.r2dbc.connection.init.ResourceDatabasePopulator;

//@Configuration
//@EnableR2dbcRepositories
@RequiredArgsConstructor
public class DbConfig {


    private final ConnectionFactory connectionFactory;

    @Bean
    public ConnectionFactoryInitializer initializer() {
        ConnectionFactoryInitializer initializer = new ConnectionFactoryInitializer();
        initializer.setConnectionFactory(connectionFactory);
        initializer.setDatabasePopulator(
            new ResourceDatabasePopulator(
                new ClassPathResource("init.sql")
            )
        );
        return initializer;
    }
}