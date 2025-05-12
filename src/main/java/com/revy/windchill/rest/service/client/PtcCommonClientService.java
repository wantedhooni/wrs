package com.revy.windchill.rest.service.client;

import com.revy.windchill.rest.service.client.common.AbstractWindchillClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PtcCommonClientService extends AbstractWindchillClientService {

    // URL 상수 정의
    private static final String URL_CSRF_TOKEN = "v5/PTC/GetCSRFToken()";

    /**
     * CSRFToken 토큰을 발급한다.
     * @return
     */
    public String getCSRFToken() {
        ResponseEntity<String> result = sendGetRequest(URL_CSRF_TOKEN, String.class);
        return result.getBody();
    }
}
