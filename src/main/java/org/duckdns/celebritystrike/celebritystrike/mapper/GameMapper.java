package org.duckdns.celebritystrike.celebritystrike.mapper;

import org.duckdns.celebritystrike.celebritystrike.dto.req.GameItemReqDto;
import org.duckdns.celebritystrike.celebritystrike.dto.req.GameReqDto;
import org.duckdns.celebritystrike.celebritystrike.dto.req.ImageReqDto;
import org.duckdns.celebritystrike.celebritystrike.dto.resp.GameItemRespDto;
import org.duckdns.celebritystrike.celebritystrike.dto.resp.GameRespDto;
import org.duckdns.celebritystrike.celebritystrike.dto.resp.ImageRespDto;
import org.duckdns.celebritystrike.celebritystrike.entity.GameEntity;
import org.duckdns.celebritystrike.celebritystrike.entity.GameItemEntity;
import org.mapstruct.Mapper;
import org.duckdns.celebritystrike.celebritystrike.entity.ImageEntity;
import org.mapstruct.Mapping;
import org.mapstruct.AfterMapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GameMapper {
    @Mapping(target = "images", source = "imageEntities")
    GameRespDto toGameRespDto(GameEntity entity);
    
    List<GameRespDto> toGameRespDtoList(List<GameEntity> entities);
    
    GameItemRespDto toGameItemRespDto(GameItemEntity entity);
    
    ImageRespDto toImageRespDto(ImageEntity entity);
    
    @Mapping(target = "imageEntities", ignore = true)
    @Mapping(target = "items", ignore = true)
    GameEntity toGameEntity(GameReqDto dto);
    
    GameItemEntity toGameItemEntity(GameItemReqDto dto);
    
    ImageEntity toImageEntity(ImageReqDto dto);
    
    @AfterMapping
    default void linkEntities(GameReqDto dto, @org.mapstruct.MappingTarget GameEntity gameEntity) {
        if (dto.images() != null) {
            dto.images().forEach(imageReqDto -> {
                ImageEntity imageEntity = toImageEntity(imageReqDto);
                gameEntity.addImage(imageEntity);
            });
        }
        
        if (dto.items() != null) {
            dto.items().forEach(itemReqDto -> {
                GameItemEntity itemEntity = toGameItemEntity(itemReqDto);
                gameEntity.addItem(itemEntity);
            });
        }
    }
}
