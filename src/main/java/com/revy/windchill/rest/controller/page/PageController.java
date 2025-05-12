package com.revy.windchill.rest.controller.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PageController {

    /**
     * Product container 조회 페이지
     * @param model
     * @return
     */
    @GetMapping("/main")
    public String main(Model model) {
        return "main";
    }

}

