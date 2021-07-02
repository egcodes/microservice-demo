package com.egcodes.advertisement.service.status.impl;

import com.egcodes.advertisement.mapper.StatusHistoryMapper;
import com.egcodes.advertisement.persistence.repository.StatusHistoryRepository;
import com.egcodes.advertisement.dto.StatusHistoryDTO;
import com.egcodes.advertisement.service.status.StatusHistoryQueryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class StatusHistoryQueryServiceImpl implements StatusHistoryQueryService {

    private final StatusHistoryRepository statusHistoryRepository;
    private final StatusHistoryMapper statusHistoryMapper;

    public StatusHistoryQueryServiceImpl(StatusHistoryRepository statusHistoryRepository, StatusHistoryMapper statusHistoryMapper) {
        this.statusHistoryRepository = statusHistoryRepository;
        this.statusHistoryMapper = statusHistoryMapper;
    }

    @Override
    public List<StatusHistoryDTO> statusChanges(Long advertisementId) {
        return statusHistoryMapper.toDTOs(statusHistoryRepository.findByAdvertisementId(advertisementId));
    }
}
