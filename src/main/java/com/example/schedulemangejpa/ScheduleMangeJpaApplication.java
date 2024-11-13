package com.example.schedulemangejpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class ScheduleMangeJpaApplication {

    public static void main(String[] args) {
        SpringApplication.run(ScheduleMangeJpaApplication.class, args);
    }

}
