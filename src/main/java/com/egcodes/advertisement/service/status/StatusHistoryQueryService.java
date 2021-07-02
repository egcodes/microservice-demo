package com.egcodes.advertisement.service.status;

import com.egcodes.advertisement.dto.StatusHistoryDTO;

import java.util.List;

public interface StatusHistoryQueryService {

    List<StatusHistoryDTO> statusChanges(Long advertisementId);

}
