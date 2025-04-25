package com.revy.windchill.rest.endpoint;

import com.revy.windchill.rest.service.WindchillClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RequiredArgsConstructor
@RestController
@ResponseBody
public class CommonController {
    private final WindchillClientService service;

    @GetMapping("/getCSRFToken")
    public String home(Model model) {
        service.getCSRFToken();
        return service.getCSRFToken();
    }

    @GetMapping("/PartListItems")
    public Map getPartListItems() {
        return service.getPartListItems();
    }

    @GetMapping("/ManufacturerParts")
    public Map getManufacturerParts(){
        return service.getManufacturerParts();
    }

}
