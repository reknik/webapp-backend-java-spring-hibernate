package com.reknik.webAppDemoBackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(exclude = HibernateJpaAutoConfiguration.class)
public class WebAppDemoBackendApplication {

  public static void main(String[] args) {
    SpringApplication.run(WebAppDemoBackendApplication.class, args);
  }

}
