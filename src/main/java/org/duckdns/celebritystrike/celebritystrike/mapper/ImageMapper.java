package org.duckdns.celebritystrike.celebritystrike.mapper; // Choose your mapper package

import org.duckdns.celebritystrike.celebritystrike.dto.req.ImageReqDto;
import org.duckdns.celebritystrike.celebritystrike.dto.resp.ImageRespDto;
import org.duckdns.celebritystrike.celebritystrike.entity.ImageEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
    componentModel = "spring", // Integrates with Spring DI
    unmappedTargetPolicy = ReportingPolicy.IGNORE) // Optional: Ignore unmapped target properties
public interface ImageMapper {
  ImageRespDto toRespDto(ImageEntity entity);

  List<ImageRespDto> toRespDtoList(List<ImageEntity> entities);

  @Mapping(target = "id", ignore = true) // Don't map ID from request
  @Mapping(target = "game", ignore = true) // Game reference will be set by the GameMapper
  ImageEntity toEntity(ImageReqDto dto);

  List<ImageEntity> toEntityList(List<ImageReqDto> dtos);
}
