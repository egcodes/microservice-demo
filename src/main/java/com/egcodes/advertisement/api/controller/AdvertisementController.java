package com.egcodes.advertisement.api.controller;

import com.egcodes.advertisement.dto.AdvertisementDTO;
import com.egcodes.advertisement.dto.StatusHistoryDTO;
import com.egcodes.advertisement.enums.Status;
import com.egcodes.advertisement.mapper.AdvertisementMapper;
import com.egcodes.advertisement.service.advertisement.AdvertisementCommandService;
import com.egcodes.advertisement.service.advertisement.AdvertisementQueryService;
import com.egcodes.advertisement.service.status.StatusHistoryQueryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequestMapping("/advertisement")
@Api(value = "Advertisement")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AdvertisementController {

    private final AdvertisementCommandService advertisementCommandService;
    private final AdvertisementQueryService advertisementQueryService;
    private final StatusHistoryQueryService statusHistoryQueryService;
    private final AdvertisementMapper advertisementMapper;

    @PostMapping(value = "/add")
    @ApiOperation(value = "Add advertisement", notes = "Adding new advertisements with title, detail and category")
    public ResponseEntity<AdvertisementDTO> add(@Valid @RequestBody AdvertisementDTO advertisementDTO) {
        return ResponseEntity.ok(advertisementMapper.toDTO(advertisementCommandService.add(advertisementMapper.toEntity(advertisementDTO))));
    }

    @PostMapping(value = "/approve/{id}")
    @ApiOperation(value = "Approve advertisement", notes = "Approve waitingForApproval ads")
    public ResponseEntity<AdvertisementDTO> approve(@PathVariable("id") Long id) {
        return ResponseEntity.ok(advertisementMapper.toDTO(advertisementCommandService.changeStatus(id, Status.ACTIVE)));
    }

    @PostMapping(value = "/deactivate/{id}")
    @ApiOperation(value = "Deactivate advertisement", notes = "Deactivate 'active or waitingForApproval' ads")
    public ResponseEntity<AdvertisementDTO> deactivate(@PathVariable("id") Long id) {
        return ResponseEntity.ok(advertisementMapper.toDTO(advertisementCommandService.changeStatus(id, Status.DEACTIVATE)));
    }

    @GetMapping(value = "/statusChanges/{id}")
    @ApiOperation(value = "Get historic status changes", notes = "Fetches all status changes on a advertisement")
    public ResponseEntity<List<StatusHistoryDTO>> statusChanges(@PathVariable("id") Long id) {
        return ResponseEntity.ok(statusHistoryQueryService.statusChanges(id));
    }

}
