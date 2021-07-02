package com.egcodes.advertisement.mapper;

import com.egcodes.advertisement.persistence.entity.StatusHistory;
import com.egcodes.advertisement.dto.StatusHistoryDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface StatusHistoryMapper {
    StatusHistoryDTO toDTO(StatusHistory entity);

    List<StatusHistoryDTO> toDTOs(List<StatusHistory> entities);

    StatusHistory toEntity(StatusHistoryDTO dto);
}