package com.example.dispatcher;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
<<<<<<< HEAD
import org.springframework.cloud.openfeign.FeignAutoConfiguration;

@SpringBootApplication
@EnableFeignClients
@ImportAutoConfiguration({FeignAutoConfiguration.class})
=======

@SpringBootApplication
@EnableFeignClients(basePackages = {"com.example.dispatcher.client"})
>>>>>>> 82d0ac8 (add user dtos & mappers & endpoint to retrieve a user by id)
public class DispatcherApplication {

    public static void main(String[] args) {
        SpringApplication.run(DispatcherApplication.class, args);
    }

}
