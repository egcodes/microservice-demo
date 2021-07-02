package com.egcodes.advertisement.service.advertisement;

import com.egcodes.advertisement.dto.AdvertisementDTO;
import com.egcodes.advertisement.dto.Statistics;

import java.util.List;

public interface AdvertisementQueryService {

    List<AdvertisementDTO> getAllAdvertisements();
    List<Statistics> statistics();
}
