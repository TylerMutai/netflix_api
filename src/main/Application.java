package main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaRepositories(basePackages="main.repositories")
@EnableTransactionManagement
@EnableJpaAuditing //Support for automatic filling of fields annotated with @CreatedDate
@EntityScan(basePackages={"main.entities","main.models"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}