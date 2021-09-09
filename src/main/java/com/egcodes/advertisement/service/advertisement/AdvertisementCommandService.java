package com.egcodes.advertisement.service.advertisement;

import com.egcodes.advertisement.persistence.entity.Advertisement;
import com.egcodes.advertisement.enums.Status;

public interface AdvertisementCommandService {

    Advertisement add(Advertisement ad);
    Advertisement changeStatus(Long id, Status status);

}
