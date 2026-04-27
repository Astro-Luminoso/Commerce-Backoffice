package dev.nbcsparta.assignment.commerce_backoffice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@EnableJpaAuditing
@SpringBootApplication
public class CommerceBackofficeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommerceBackofficeApplication.class, args);
    }

}
