package com.egcodes.advertisement.dto;

import com.egcodes.advertisement.enums.Category;
import com.egcodes.advertisement.enums.Status;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

@Builder
@Data
public class AdvertisementDTO {

    @ApiModelProperty(hidden=true)
    private Long id;

    @NotEmpty
    @Size(min=10, max=50)
    @Pattern(regexp = "^[a-zA-Z0-9çşğüöıÇŞĞÜÖİ]+")
    @ApiModelProperty(position = 1, required = true)
    private String title;

    @NotEmpty
    @Size(min=20, max=200)
    @ApiModelProperty(position = 2, required = true)
    private String detail;

    @NotNull
    @ApiModelProperty(position = 3, required = true)
    private Category category;

    @ApiModelProperty(hidden=true)
    private Status status;

    @ApiModelProperty(hidden=true)
    private OffsetDateTime createDate;

    @ApiModelProperty(hidden=true)
    private OffsetDateTime endDate;

}
