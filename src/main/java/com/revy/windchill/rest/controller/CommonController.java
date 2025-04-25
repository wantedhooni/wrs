package com.revy.windchill.rest.controller;

import com.revy.windchill.rest.service.WindchillClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
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

    @GetMapping("/ManufacturerParts({manufacturerPartId})/Context/Folders")
    public Map getManufacturerPartsFoldersID(@PathVariable("manufacturerPartId") String ManufacturerPartId) {
        log.info("[getManufacturerPartsFolders] ManufacturerPartId: {}", ManufacturerPartId);
        return service.getManufacturerPartsFoldersID(ManufacturerPartId);
    }

    @GetMapping("/ManufacturerParts({manufacturerPartId})/Context/Folders({folderId})")
    public Map getManufacturerPartsFolders(
            @PathVariable("manufacturerPartId") String manufacturerPartId,
            @PathVariable("folderId") String folderId){
        log.info("[getManufacturerPartsFolders] ManufacturerPartId: {}", manufacturerPartId);
        return service.getManufacturerPartsFolders(manufacturerPartId, folderId);
    }


}
