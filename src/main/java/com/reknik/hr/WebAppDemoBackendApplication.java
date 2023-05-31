package com.reknik.hr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class WebAppDemoBackendApplication {

    public static void main(String[] args) {


        SpringApplication.run(WebAppDemoBackendApplication.class, args);


    }

}
