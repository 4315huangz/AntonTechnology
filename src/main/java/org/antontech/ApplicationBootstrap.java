package org.antontech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"org.antontech"})
public class ApplicationBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationBootstrap.class, args);
    }
}
