package com.revy.windchill.rest.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class WindchillClientService {

    @Value("${windchill.baseurl}")
    private String baseUrl;

    @Value("${windchill.username}")
    private String userName;

    @Value("${windchill.userpassword}")
    private String userPassword;

    private final RestTemplate restTemplate;

    // URL 상수 정의
    private static final String URL_CSRF_TOKEN = "/servlet/odata/v5/PTC/GetCSRFToken()";
    private static final String URL_PART_LIST_ITEMS = "/servlet/odata/v6/PartListMgmt/PartListItems";
    private static final String URL_PART_LISTS = "/servlet/odata/v6/PartListMgmt/PartLists";
    private static final String URL_MANUFACTURER_PARTS = "/servlet/odata/v7/ProdMgmt/ManufacturerParts";
    private static final String URL_MANUFACTURER_PARTS_FOLDERS_ID = "/servlet/odata/v7/ProdMgmt/ManufacturerParts('{ManufacturerPartId}')/Context/Folders";
    private static final String URL_MANUFACTURER_PARTS_FOLDERS = "/servlet/odata/v7/ProdMgmt/ManufacturerParts('{ManufacturerPartId}')/Context/Folders('{FolderId}')";



    @PostConstruct
    public void init() {
        log.info("Windchill Base URL: {}", baseUrl);
    }

    public String getCSRFToken() {
        ResponseEntity<String> result = sendGetRequest(URL_CSRF_TOKEN, String.class);
        return result.getBody();
    }

    public Map<String, Object> getManufacturerPartsFoldersID(String ManufacturerPartId) {
        String uri = UriComponentsBuilder.fromUriString(URL_MANUFACTURER_PARTS_FOLDERS_ID).buildAndExpand(ManufacturerPartId).toUriString();
        log.debug("[getManufacturerPartsFoldersID] uri: {}", uri);
        return sendGetRequest(uri, Map.class).getBody();
    }

    public Map<String, Object> getManufacturerPartsFolders(String ManufacturerPartId, String FolderId) {
        String uri = UriComponentsBuilder.fromUriString(URL_MANUFACTURER_PARTS_FOLDERS)
                .buildAndExpand(ManufacturerPartId, FolderId)
                .toUriString();
        log.debug("[getManufacturerPartsFolders] uri: {}", uri);
        return sendGetRequest(uri, Map.class).getBody();
    }

    public Map<String, Object> getPartListItems() {
        return sendGetRequest(URL_PART_LIST_ITEMS, Map.class).getBody();
    }

    public Map<String, Object> getPartLists() {
        return sendGetRequest(URL_PART_LISTS, Map.class).getBody();
    }

    public Map<String, Object> getManufacturerParts() {
        return sendGetRequest(URL_MANUFACTURER_PARTS, Map.class).getBody();
    }

    private <T> ResponseEntity<T> sendGetRequest(String relativePath, Class<T> responseType) {
        String url = UriComponentsBuilder.fromUriString(baseUrl).path(relativePath).encode().toUriString();
        log.debug("[sendGetRequest] url: {}", url);
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(userName, userPassword);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
        log.debug("GET {} => {}", url, response.getStatusCode());

        return response;
    }
}
