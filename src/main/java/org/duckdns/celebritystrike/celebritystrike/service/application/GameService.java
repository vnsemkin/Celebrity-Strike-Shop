package org.duckdns.celebritystrike.celebritystrike.service.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.duckdns.celebritystrike.celebritystrike.dao.GameRepository;
import org.duckdns.celebritystrike.celebritystrike.dto.req.GameReqDto;
import org.duckdns.celebritystrike.celebritystrike.dto.resp.GameRespDto;
import org.duckdns.celebritystrike.celebritystrike.entity.GameEntity;
import org.duckdns.celebritystrike.celebritystrike.mapper.GameMapper;
import org.duckdns.celebritystrike.celebritystrike.model.Result;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GameService {
    private final GameMapper gameMapper;
    private final GameRepository gameRepository;

    public Result<List<GameRespDto>> getGames() {
        try {
            List<GameRespDto> gameRespDtoList = gameMapper.toGameRespDtoList(gameRepository.findAll());
            return Result.<List<GameRespDto>>builder()
                    .success(true)
                    .data(gameRespDtoList)
                    .message("Games found")
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

    @Transactional
    public Result<String> saveGames(@NonNull GameReqDto gameReqDto) {
        try {
            log.info("Post req received: {}", gameReqDto);
            GameEntity gameEntity = gameMapper.toGameEntity(gameReqDto);
            
            // Initialize collections if null
            if (gameEntity.getImageEntities() == null) {
                gameEntity.setImageEntities(List.of());
            }
            if (gameEntity.getItems() == null) {
                gameEntity.setItems(List.of());
            }
            
            log.info("Game entity: {}", gameEntity);
            GameEntity saved = gameRepository.save(gameEntity);
            return Result.<String>builder()
                    .success(true)
                    .data("Game with id: " + saved.getId() + " saved")
                    .message("Game saved")
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
}
