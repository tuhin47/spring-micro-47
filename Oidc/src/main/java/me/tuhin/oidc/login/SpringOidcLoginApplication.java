package me.tuhin.oidc.login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class SpringOidcLoginApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringOidcLoginApplication.class);
//        ApplicationContextInitializer<ConfigurableApplicationContext> yamlInitializer = new YamlLoaderInitializer("login-application.yml");
//        application.addInitializers(yamlInitializer);
        application.run(args);
    }

}
