package org.duckdns.celebritystrike.celebritystrike.mapper; // Choose your mapper package

import org.duckdns.celebritystrike.celebritystrike.dto.req.GameReqDto;
import org.duckdns.celebritystrike.celebritystrike.dto.resp.GameRespDto;
import org.duckdns.celebritystrike.celebritystrike.entity.GameEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring",
        uses = {ImageMapper.class, GameItemMapper.class}, // IMPORTANT: Use the nested mappers
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE // Optional: Good for updates
)
public interface GameMapper {

    GameRespDto toRespDto(GameEntity entity);

    List<GameRespDto> toRespDtoList(List<GameEntity> entities);

    @Mapping(target = "id", ignore = true)
    GameEntity toEntity(GameReqDto dto);

    /**
     * IMPORTANT: Establishes the bidirectional relationship after mapping from DTO to Entity.
     * Sets the 'game' property on each child ImageEntity and GameItemEntity.
     * This is crucial for JPA relationships to work correctly when saving the GameEntity.
     *
     * @param gameEntity The target GameEntity, already mapped from GameReqDto.
     */
    @AfterMapping
    default void linkChildren(@MappingTarget GameEntity gameEntity) {
        if (gameEntity.getImages() != null) {
            gameEntity.getImages().forEach(image -> image.setGame(gameEntity));
        }
        if (gameEntity.getItems() != null) {
            gameEntity.getItems().forEach(item -> item.setGame(gameEntity));
        }
    }

    @Mapping(target = "id", ignore = true) // Do not update the ID
    void updateEntityFromDto(GameReqDto dto, @MappingTarget GameEntity entity);
}