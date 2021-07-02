package com.egcodes.advertisement.dto;

import com.egcodes.advertisement.enums.Status;
import lombok.*;

import java.time.OffsetDateTime;

@Builder
@Data
public class StatusHistoryDTO {

    private OffsetDateTime updatedDate;

    private Status status;

}
