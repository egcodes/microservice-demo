package com.egcodes.advertisement.service.advertisement.impl;

import com.egcodes.advertisement.dto.AdvertisementDTO;
import com.egcodes.advertisement.dto.Statistics;
import com.egcodes.advertisement.mapper.AdvertisementMapper;
import com.egcodes.advertisement.persistence.repository.AdvertisementRepository;
import com.egcodes.advertisement.service.advertisement.AdvertisementQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AdvertisementQueryServiceImpl implements AdvertisementQueryService {

    private final AdvertisementRepository advertisementRepository;
    private final AdvertisementMapper advertisementMapperImpl;

    @Autowired
    public AdvertisementQueryServiceImpl(AdvertisementRepository advertisementRepository, AdvertisementMapper advertisementMapperImpl) {
        this.advertisementRepository = advertisementRepository;
        this.advertisementMapperImpl = advertisementMapperImpl;
    }

    @Override
    public List<AdvertisementDTO> getAllAdvertisements() {
        return advertisementMapperImpl.toDTOs(advertisementRepository.findAll());
    }

    @Override
    public List<Statistics> statistics() {
        var adsStatus = new HashMap<String, Integer>();
        return advertisementRepository.getAdvertisementCountByStatus();
    }
}
