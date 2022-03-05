package me.tuhin.oidc.login;

import me.tuhin.oidc.utils.YamlLoaderInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.*;

@SpringBootApplication
public class SpringOidcLoginApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringOidcLoginApplication.class);
        ApplicationContextInitializer<ConfigurableApplicationContext> yamlInitializer = new YamlLoaderInitializer("login-application.yml");
        application.addInitializers(yamlInitializer);
        application.run(args);
    }

}
