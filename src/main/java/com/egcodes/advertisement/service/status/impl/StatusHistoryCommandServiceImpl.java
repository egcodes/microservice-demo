package com.egcodes.advertisement.service.status.impl;

import com.egcodes.advertisement.mapper.StatusHistoryMapper;
import com.egcodes.advertisement.persistence.entity.StatusHistory;
import com.egcodes.advertisement.persistence.repository.StatusHistoryRepository;
import com.egcodes.advertisement.service.status.StatusHistoryCommandService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class StatusHistoryCommandServiceImpl implements StatusHistoryCommandService {

    private final StatusHistoryMapper statusHistoryMapper;
    private final StatusHistoryRepository statusHistoryRepository;

    @Override
    public StatusHistory add(StatusHistory statusHistory) {
        return statusHistoryRepository.save((statusHistory));
    }
}
