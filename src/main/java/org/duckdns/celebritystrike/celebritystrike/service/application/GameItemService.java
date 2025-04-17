package org.duckdns.celebritystrike.celebritystrike.service.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.duckdns.celebritystrike.celebritystrike.dao.GameItemRepository;
import org.duckdns.celebritystrike.celebritystrike.dao.GameRepository;
import org.duckdns.celebritystrike.celebritystrike.dto.req.GameItemCreateReqDto;
import org.duckdns.celebritystrike.celebritystrike.dto.req.GameItemUpdateReqDto;
import org.duckdns.celebritystrike.celebritystrike.dto.resp.GameItemRespDto;
import org.duckdns.celebritystrike.celebritystrike.entity.GameEntity;
import org.duckdns.celebritystrike.celebritystrike.entity.GameItemEntity;
import org.duckdns.celebritystrike.celebritystrike.exception.GameNotFoundException;
import org.duckdns.celebritystrike.celebritystrike.exception.GameItemNotFoundException;
import org.duckdns.celebritystrike.celebritystrike.mapper.GameItemMapper;
import org.duckdns.celebritystrike.celebritystrike.model.Result;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameItemService {
  private final GameItemMapper gameItemMapper;
  private final GameItemRepository gameItemRepository;
  private final GameRepository gameRepository;

  public Result<GameItemRespDto> createGameItem(@NonNull GameItemCreateReqDto dto) {
    try {
      GameEntity game =
          gameRepository
              .findByNameIgnoreCase(dto.gameName())
              .orElseThrow(
                  () ->
                      new GameNotFoundException("Game with name " + dto.gameName() + " not found"));

      GameItemEntity gameItem = gameItemMapper.toEntity(dto);
      gameItem.setGame(game);
      GameItemEntity saved = gameItemRepository.save(gameItem);

      return Result.<GameItemRespDto>builder()
          .success(true)
          .data(gameItemMapper.toRespDto(saved))
          .message("Game item created successfully")
          .build();
    } catch (Exception e) {
      log.error("Error creating game item: {}", e.getMessage(), e);
      return Result.<GameItemRespDto>builder()
          .success(false)
          .data(null)
          .message("Error creating game item: " + e.getMessage())
          .build();
    }
  }

  public Result<GameItemRespDto> updateGameItem(
      @NonNull Long id, @NonNull GameItemUpdateReqDto dto) {
    try {
      GameItemEntity gameItem =
          gameItemRepository
              .findById(id)
              .orElseThrow(
                  () -> new GameItemNotFoundException("Game item with id " + id + " not found"));

      gameItemMapper.updateEntityFromDto(dto, gameItem);
      GameItemEntity updated = gameItemRepository.save(gameItem);

      return Result.<GameItemRespDto>builder()
          .success(true)
          .data(gameItemMapper.toRespDto(updated))
          .message("Game item updated successfully")
          .build();
    } catch (Exception e) {
      log.error("Error updating game item: {}", e.getMessage(), e);
      return Result.<GameItemRespDto>builder()
          .success(false)
          .data(null)
          .message("Error updating game item: " + e.getMessage())
          .build();
    }
  }

  public Result<String> deleteGameItem(@NonNull Long id) {
    try {
      if (!gameItemRepository.existsById(id)) {
        throw new GameItemNotFoundException("Game item with id " + id + " not found");
      }

      gameItemRepository.deleteById(id);
      return Result.<String>builder()
          .success(true)
          .data("Game item deleted successfully")
          .message("Game item with id " + id + " has been deleted")
          .build();
    } catch (Exception e) {
      log.error("Error deleting game item: {}", e.getMessage(), e);
      return Result.<String>builder()
          .success(false)
          .data(null)
          .message("Error deleting game item: " + e.getMessage())
          .build();
    }
  }
}
