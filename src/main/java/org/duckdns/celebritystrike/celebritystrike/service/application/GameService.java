package org.duckdns.celebritystrike.celebritystrike.service.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.duckdns.celebritystrike.celebritystrike.dao.GameRepository;
import org.duckdns.celebritystrike.celebritystrike.dto.req.GameByNameReq;
import org.duckdns.celebritystrike.celebritystrike.dto.req.GameReqDto;
import org.duckdns.celebritystrike.celebritystrike.dto.req.GameUpdateReqDto;
import org.duckdns.celebritystrike.celebritystrike.dto.resp.GameRespDto;
import org.duckdns.celebritystrike.celebritystrike.entity.GameEntity;
import org.duckdns.celebritystrike.celebritystrike.exception.GameNotFoundException;
import org.duckdns.celebritystrike.celebritystrike.mapper.GameMapper;
import org.duckdns.celebritystrike.celebritystrike.model.Result;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {
  private final GameMapper gameMapper;
  private final GameRepository gameRepository;

  private final String GAME_NOT_FOUND_ID = "Game with id %d not found";
  private final String GAME_NOT_FOUND_NAME = "Game with name %s not found";
  private final String GAME_WITH_NAME_FOUND = "Game with name %s found";

  public Result<List<GameRespDto>> getGames() {
    try {
      List<GameRespDto> gameRespDtoList = gameMapper.toRespDtoList(gameRepository.findAll());
      return Result.<List<GameRespDto>>builder()
          .success(true)
          .data(gameRespDtoList)
          .message("Games")
          .build();
    } catch (Exception e) {
      log.error(e.getMessage());
      return Result.<List<GameRespDto>>builder()
          .success(false)
          .data(null)
          .message(e.getMessage())
          .build();
    }
  }

  public Result<GameRespDto> getGameById(@NonNull int id) {
    try {
      GameRespDto gameRespDto =
          gameMapper.toRespDto(
              gameRepository
                  .findById(id)
                  .orElseThrow(
                      () -> new GameNotFoundException(String.format(GAME_NOT_FOUND_ID, id))));
      String GAME_WITH_ID_FOUND = "Game with id %d found";
      return Result.<GameRespDto>builder()
          .success(true)
          .data(gameRespDto)
          .message(String.format(GAME_WITH_ID_FOUND, id))
          .build();
    } catch (Exception e) {
      log.error(e.getMessage());
      return Result.<GameRespDto>builder()
          .success(false)
          .data(null)
          .message(e.getMessage())
          .build();
    }
  }

  public Result<GameRespDto> getGameByName(@NonNull String name) {
    try {
      GameRespDto gameRespDto =
          gameMapper.toRespDto(
              gameRepository
                  .findByNameIgnoreCase(name)
                  .orElseThrow(
                      () -> new GameNotFoundException(String.format(GAME_NOT_FOUND_NAME, name))));
      return Result.<GameRespDto>builder()
          .success(true)
          .data(gameRespDto)
          .message(String.format(GAME_WITH_NAME_FOUND, name))
          .build();
    } catch (Exception e) {
      log.error(e.getMessage());
      return Result.<GameRespDto>builder()
          .success(false)
          .data(null)
          .message(e.getMessage())
          .build();
    }
  }

  public Result<String> saveGame(@NonNull GameReqDto gameReqDto) {
    try {
      GameEntity gameEntity = gameMapper.toEntity(gameReqDto);
      GameEntity saved = gameRepository.save(gameEntity);
      return Result.<String>builder()
          .success(true)
          .data("Game with id: " + saved.getId() + " saved")
          .message("Game saved to Database")
          .build();
    } catch (Exception e) {
      log.error("Error saving game: {}", e.getMessage(), e);
      return Result.<String>builder()
          .success(false)
          .data(null)
          .message("Error saving game: " + e.getMessage())
          .build();
    }
  }

  public Result<GameRespDto> updateGame(@NonNull String name, @NonNull GameUpdateReqDto gameUpdateReqDto) {
    try {
      GameEntity existingGame = gameRepository.findByNameIgnoreCase(name)
          .orElseThrow(() -> new GameNotFoundException(String.format(GAME_NOT_FOUND_NAME, name)));
      
      if (gameUpdateReqDto.title() != null) {
          existingGame.setTitle(gameUpdateReqDto.title());
      }
      if (gameUpdateReqDto.description() != null) {
          existingGame.setDescription(gameUpdateReqDto.description());
      }
      if (gameUpdateReqDto.instruction() != null) {
          existingGame.setInstruction(gameUpdateReqDto.instruction());
      }
      
      GameEntity updated = gameRepository.save(existingGame);
      
      return Result.<GameRespDto>builder()
          .success(true)
          .data(gameMapper.toRespDto(updated))
          .message("Game updated successfully")
          .build();
    } catch (Exception e) {
      log.error("Error updating game: {}", e.getMessage(), e);
      return Result.<GameRespDto>builder()
          .success(false)
          .data(null)
          .message("Error updating game: " + e.getMessage())
          .build();
    }
  }
}
