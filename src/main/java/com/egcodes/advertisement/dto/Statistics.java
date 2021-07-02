package com.egcodes.advertisement.dto;

import com.egcodes.advertisement.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Statistics {

    private Status status;

    private Long count;

}
