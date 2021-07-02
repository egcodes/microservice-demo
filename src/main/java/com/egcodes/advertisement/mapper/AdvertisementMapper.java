package com.egcodes.advertisement.mapper;

import com.egcodes.advertisement.persistence.entity.Advertisement;
import com.egcodes.advertisement.dto.AdvertisementDTO;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdvertisementMapper {
    AdvertisementDTO toDTO(Advertisement entity);

    List<AdvertisementDTO> toDTOs(List<Advertisement> entities);

    Advertisement toEntity(AdvertisementDTO dto);
}