package com.example.edge;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy  //zuul: gateway, 网关的作用
public class EdgingServiceApplication {

    public static void main(String[] args){
        SpringApplication.run(EdgingServiceApplication.class, args);
    }
}