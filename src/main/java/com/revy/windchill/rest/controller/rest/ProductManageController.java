package com.revy.windchill.rest.controller.rest;

import com.revy.windchill.rest.service.client.DataAdminClientService;
import com.revy.windchill.rest.service.client.PtcCommonClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class ProductManageController {
    private final PtcCommonClientService productManageClientService;
    private final DataAdminClientService dataAdminService;

    /**
     * product Container 목록을 조회한다.
     * @return
     */
    @GetMapping("/productContainer")
    public Map getProductContainer() {
        return dataAdminService.getProductContainers();
    }

    /**
     * Cabinet의 폴더를 조회한다.
     * @param containerId
     * @param cabinetId
     * @return
     */
    @GetMapping("/Containers({ContainerId})/CabinetId({CabinetId})")
    public Map getContainerFolders(@PathVariable("ContainerId") String containerId,
                                   @PathVariable("CabinetId") String cabinetId) {
        return dataAdminService.getProductContainerCabinet(containerId, cabinetId);
    }

    /**
     * Cabinet의 폴더(SubFolder)의 Contents를 조회한다.
     * @param containerId
     * @param cabinetId
     * @param subFolderId
     * @return
     */
    @GetMapping("/Containers({ContainerId})/CabinetId({CabinetId})/Folders({SubFolderId})")
    public Map getProductContainerCabinetContents(@PathVariable("ContainerId") String containerId,
                                                  @PathVariable("CabinetId") String cabinetId,
                                                  @PathVariable("SubFolderId") String subFolderId    ) {
        return dataAdminService.getProductContainerCabinetContents(containerId, cabinetId, subFolderId);
    }
}
