package com.revy.windchill.rest;

import com.revy.windchill.rest.service.WindchillClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
public class WindchillRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(WindchillRestApplication.class, args);
    }

}



