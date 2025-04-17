package org.duckdns.celebritystrike.celebritystrike.mapper; // Choose your mapper package

import org.duckdns.celebritystrike.celebritystrike.dto.req.GameItemCreateReqDto;
import org.duckdns.celebritystrike.celebritystrike.dto.req.GameItemUpdateReqDto;
import org.duckdns.celebritystrike.celebritystrike.dto.resp.GameItemRespDto;
import org.duckdns.celebritystrike.celebritystrike.entity.GameItemEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GameItemMapper {

    GameItemRespDto toRespDto(GameItemEntity entity);

    List<GameItemRespDto> toRespDtoList(List<GameItemEntity> entities);

    @Mapping(target = "id", ignore = true) // Don't map ID from request
    @Mapping(target = "game", ignore = true) // Game reference will be set by the GameMapper
    GameItemEntity toEntity(GameItemCreateReqDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "game", ignore = true)
    void updateEntityFromDto(GameItemUpdateReqDto dto, @MappingTarget GameItemEntity entity);
}
