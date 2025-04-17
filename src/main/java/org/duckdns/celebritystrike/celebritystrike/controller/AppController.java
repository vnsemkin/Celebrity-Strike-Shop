package org.duckdns.celebritystrike.celebritystrike.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.duckdns.celebritystrike.celebritystrike.dto.req.GameReqDto;
import org.duckdns.celebritystrike.celebritystrike.dto.req.GameUpdateReqDto;
import org.duckdns.celebritystrike.celebritystrike.dto.req.ImageCreateReqDto;
import org.duckdns.celebritystrike.celebritystrike.dto.req.ImageUpdateReqDto;
import org.duckdns.celebritystrike.celebritystrike.dto.req.GameItemCreateReqDto;
import org.duckdns.celebritystrike.celebritystrike.dto.req.GameItemUpdateReqDto;
import org.duckdns.celebritystrike.celebritystrike.dto.resp.GameRespDto;
import org.duckdns.celebritystrike.celebritystrike.dto.resp.ImageRespDto;
import org.duckdns.celebritystrike.celebritystrike.dto.resp.GameItemRespDto;
import org.duckdns.celebritystrike.celebritystrike.model.Result;
import org.duckdns.celebritystrike.celebritystrike.service.application.GameService;
import org.duckdns.celebritystrike.celebritystrike.service.application.ImageService;
import org.duckdns.celebritystrike.celebritystrike.service.application.GameItemService;
import org.duckdns.celebritystrike.celebritystrike.service.web_handlers.DefaultWebHandler;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AppController {
    private final GameService gameService;
    private final ImageService imageService;
    private final GameItemService gameItemService;
    private final DefaultWebHandler handler;

    @GetMapping("/games")
    public Result<List<GameRespDto>> getGames() {
        // TODO add pagination
        log.info("Get request data: method getGames");
        return gameService.getGames();
    }

    @GetMapping("/games/id/{id:[0-9]+}")
    public Result<GameRespDto> getGamesById(@PathVariable int id) {
        log.info("Get request data: method getGamesById: {}", id);
        return gameService.getGameById(id);
    }

    @GetMapping("/games/{name}")
    public Result<GameRespDto> getGamesByName(@PathVariable String name) {
        log.info("Get request data: method getGamesByName: {}", name);
        return gameService.getGameByName(name);
    }

    @PostMapping("/games")
    public Result<String> saveGames(@RequestBody GameReqDto gameReqDto) {
        // TODO add validation and wrap to Result
        if(gameReqDto == null) {
            log.error("Post req received: null");
            return Result.<String>builder().success(false).data(null).message("Post req received: null").build();
        }
        return gameService.saveGame(gameReqDto);
    }

    @PutMapping("/games/{name}")
    public Result<GameRespDto> updateGame(@PathVariable String name, @RequestBody GameUpdateReqDto gameUpdateReqDto) {
        log.info("Put request data: method updateGame: name={}", name);
        return gameService.updateGame(name, gameUpdateReqDto);
    }

    @PostMapping("/images")
    public Result<ImageRespDto> createImage(@RequestBody ImageCreateReqDto imageCreateReqDto) {
        log.info("Post request data: method createImage");
        return imageService.createImage(imageCreateReqDto);
    }

    @PutMapping("/images/{id}")
    public Result<ImageRespDto> updateImage(@PathVariable Long id, @RequestBody ImageUpdateReqDto imageUpdateReqDto) {
        log.info("Put request data: method updateImage: id={}", id);
        return imageService.updateImage(id, imageUpdateReqDto);
    }

    @DeleteMapping("/images/{id}")
    public Result<String> deleteImage(@PathVariable Long id) {
        log.info("Delete request data: method deleteImage: id={}", id);
        return imageService.deleteImage(id);
    }

    @PostMapping("/game-items")
    public Result<GameItemRespDto> createGameItem(@RequestBody GameItemCreateReqDto gameItemCreateReqDto) {
        log.info("Post request data: method createGameItem");
        return gameItemService.createGameItem(gameItemCreateReqDto);
    }

    @PutMapping("/game-items/{id}")
    public Result<GameItemRespDto> updateGameItem(@PathVariable Long id, @RequestBody GameItemUpdateReqDto gameItemUpdateReqDto) {
        log.info("Put request data: method updateGameItem: id={}", id);
        return gameItemService.updateGameItem(id, gameItemUpdateReqDto);
    }

    @DeleteMapping("/game-items/{id}")
    public Result<String> deleteGameItem(@PathVariable Long id) {
        log.info("Delete request data: method deleteGameItem: id={}", id);
        return gameItemService.deleteGameItem(id);
    }
}
