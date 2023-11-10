package com.system.housesystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HouseSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(HouseSystemApplication.class, args);
    }

}
