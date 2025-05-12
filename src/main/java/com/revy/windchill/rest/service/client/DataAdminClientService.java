package com.revy.windchill.rest.service.client;

import com.revy.windchill.rest.service.client.common.AbstractWindchillClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Map;

/**
 * DataAdmin 요청 Class
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DataAdminClientService extends AbstractWindchillClientService {

    // URL 상수 정의
    private static final String URL_PRODUCT_CONTAINERS = "v7/DataAdmin/Containers/PTC.DataAdmin.ProductContainer";
    private static final String URL_PRODUCT_CONTAINER_CABINET = "v7/DataAdmin/Containers('{ContainerId}')/Folders('{CabinetId}')";
    private static final String URL_PRODUCT_CONTAINER_CABINET_FOLDERS = "v7/DataAdmin/Containers('{ContainerId}')/Folders('{CabinetId}')/Folders('{FolderId}')";

    /**
     * Product Container 목록 조회
     * @return
     */
    public Map<String, Object> getProductContainers() {
        String uri = ServletUriComponentsBuilder.fromUriString(URL_PRODUCT_CONTAINERS)
                .queryParam("$expand", "Folders")
                .toUriString();
        return sendGetRequest(uri, Map.class).getBody();
    }

    /**
     * Container Cabinet 조회한다.
     * @param containerId
     * @param cabinetId
     * @return
     */
    public Map<String, Object> getProductContainerCabinet(String containerId, String cabinetId) {
        String uri = ServletUriComponentsBuilder.fromUriString(URL_PRODUCT_CONTAINER_CABINET)
                .queryParam("$expand", "Folders")
                .buildAndExpand(containerId, cabinetId)
                .toString();
        return sendGetRequest(uri, Map.class).getBody();
    }

    /**
     * Container Cabinet 의 contents를 조회한다.
     * @param containerId
     * @param cabinetId
     * @param subFolderId
     * @return
     */
    public Map<String, Object> getProductContainerCabinetContents(String containerId, String cabinetId, String subFolderId) {
        String uri = ServletUriComponentsBuilder.fromUriString(URL_PRODUCT_CONTAINER_CABINET_FOLDERS)
                .queryParam("$expand", "FolderContents")
                .buildAndExpand(containerId, cabinetId, subFolderId)
                .toString();
        return sendGetRequest(uri, Map.class).getBody();
    }
}
