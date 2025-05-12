package com.revy.windchill.rest.service.client.common;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
public abstract class AbstractWindchillClientService {

    @Value("${windchill.baseurl}")
    private String baseUrl;

    @Value("${windchill.username}")
    private String userName;

    @Value("${windchill.userpassword}")
    private String userPassword;

    @Autowired
    @Setter
    private  RestTemplate restTemplate;

    /**
     * GET 방식 요청 발송 한다.
     * @param relativePath
     * @param responseType
     * @return
     * @param <T>
     */
    protected  <T> ResponseEntity<T> sendGetRequest(String relativePath, Class<T> responseType) {
        String url = UriComponentsBuilder.fromUriString(baseUrl).path(relativePath)
                .build(false)
                .toUriString();
        log.debug("[sendGetRequest] url: {}", url);
        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(userName, userPassword);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, responseType);
        log.debug("GET {} => {}", url, response.getStatusCode());

        return response;
    }
}
