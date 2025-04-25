package com.revy.windchill.rest;

import com.revy.windchill.rest.service.WindchillClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class WindchillRestApplication {

    public static void main(String[] args) {
        SpringApplication.run(WindchillRestApplication.class, args);
    }

}

@Controller
@RequiredArgsConstructor
class HomeController {
    private final WindchillClientService service;

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "Hello JSP!");
        return "example";
    }
}


@Configuration
class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}