package com.egcodes.advertisement.api.controller;

import com.egcodes.advertisement.dto.AdvertisementDTO;
import com.egcodes.advertisement.service.advertisement.AdvertisementCommandService;
import com.egcodes.advertisement.service.advertisement.AdvertisementQueryService;
import com.egcodes.advertisement.service.status.StatusHistoryQueryService;
import com.egcodes.advertisement.dto.Statistics;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/dashboard/classifieds")
@Api(value = "Dashboard")
@Slf4j
public class DashboardController {

    private final AdvertisementCommandService advertisementCommandService;
    private final AdvertisementQueryService advertisementQueryService;
    private final StatusHistoryQueryService statusHistoryQueryService;

    @Autowired
    public DashboardController(AdvertisementCommandService advertisementCommandService, AdvertisementQueryService advertisementQueryService, StatusHistoryQueryService statusHistoryQueryService) {
        this.advertisementCommandService = advertisementCommandService;
        this.advertisementQueryService = advertisementQueryService;
        this.statusHistoryQueryService = statusHistoryQueryService;
    }

    @GetMapping(value = "/list")
    @ApiOperation(value = "Get advertisements", notes = "List all advertisements")
    public ResponseEntity<List<AdvertisementDTO>> list() {
        return ResponseEntity.ok(advertisementQueryService.getAllAdvertisements());
    }

    @GetMapping(value = "/statistics")
    @ApiOperation(value = "Get statistics by status", notes = "In which status, how many ads are there")
    public ResponseEntity<List<Statistics>> statistics() {
        return ResponseEntity.ok(advertisementQueryService.statistics());
    }

}
