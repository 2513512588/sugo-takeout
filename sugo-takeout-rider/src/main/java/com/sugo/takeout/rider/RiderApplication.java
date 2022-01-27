package com.sugo.takeout.rider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.sugo.takeout"})
public class RiderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RiderApplication.class, args);
    }

}
