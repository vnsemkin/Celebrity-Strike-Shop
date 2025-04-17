package org.duckdns.celebritystrike.celebritystrike.service.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.duckdns.celebritystrike.celebritystrike.dao.GameRepository;
import org.duckdns.celebritystrike.celebritystrike.dto.resp.GameRespDto;
import org.duckdns.celebritystrike.celebritystrike.mapper.GameMapper;
import org.duckdns.celebritystrike.celebritystrike.model.Result;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImagesService {
private final GameMapper gameMapper;
private final GameRepository gameRepository;

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
}
