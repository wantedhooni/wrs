package com.revy.windchill.rest.controller;

import com.revy.windchill.rest.service.WindchillClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PageController {

    private final WindchillClientService service;

    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("message", "Hello JSP!");
        return "example";
    }
}

