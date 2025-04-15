package org.duckdns.celebritystrike.celebritystrike.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.duckdns.celebritystrike.celebritystrike.dto.req.GameReqDto;
import org.duckdns.celebritystrike.celebritystrike.dto.resp.GameRespDto;
import org.duckdns.celebritystrike.celebritystrike.mapper.GameMapper;
import org.duckdns.celebritystrike.celebritystrike.model.Result;
import org.duckdns.celebritystrike.celebritystrike.service.application.GameService;
import org.duckdns.celebritystrike.celebritystrike.service.web_handlers.DefaultWebHandler;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AppController {
    private final GameService gameService;
    private final DefaultWebHandler handler;

    @GetMapping("/games")
    public Result<List<GameRespDto>> getGames() {
        // TODO add pagination
        return gameService.getGames();
    }

    @PostMapping("/games")
    public Result<String> saveGames(@RequestBody GameReqDto gameReqDto) {
        // TODO add validation and wrap to Result
        if(gameReqDto == null) {
            log.error("Post req received: null");
            return Result.<String>builder().success(false).data(null).message("Post req received: null").build();
        }
        return gameService.saveGames(gameReqDto);
    }
}
