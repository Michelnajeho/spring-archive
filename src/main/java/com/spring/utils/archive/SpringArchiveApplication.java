package com.spring.utils.archive;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;


@EnableAspectJAutoProxy
@SpringBootApplication
public class SpringArchiveApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringArchiveApplication.class, args);
    }

}