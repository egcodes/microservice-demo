package com.egcodes.advertisement.service.status;

import com.egcodes.advertisement.persistence.entity.StatusHistory;

public interface StatusHistoryCommandService {

    StatusHistory add(StatusHistory statusHistory);

}
