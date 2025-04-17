package org.duckdns.celebritystrike.celebritystrike.service.application;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.duckdns.celebritystrike.celebritystrike.dao.GameRepository;
import org.duckdns.celebritystrike.celebritystrike.dao.ImageRepository;
import org.duckdns.celebritystrike.celebritystrike.dto.req.ImageCreateReqDto;
import org.duckdns.celebritystrike.celebritystrike.dto.req.ImageUpdateReqDto;
import org.duckdns.celebritystrike.celebritystrike.dto.resp.ImageRespDto;
import org.duckdns.celebritystrike.celebritystrike.entity.GameEntity;
import org.duckdns.celebritystrike.celebritystrike.entity.ImageEntity;
import org.duckdns.celebritystrike.celebritystrike.exception.GameNotFoundException;
import org.duckdns.celebritystrike.celebritystrike.exception.ImageNotFoundException;
import org.duckdns.celebritystrike.celebritystrike.mapper.ImageMapper;
import org.duckdns.celebritystrike.celebritystrike.model.Result;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageMapper imageMapper;
    private final ImageRepository imageRepository;
    private final GameRepository gameRepository;

    public Result<ImageRespDto> createImage(@NonNull ImageCreateReqDto dto) {
        try {
            GameEntity game = gameRepository.findByNameIgnoreCase(dto.gameName())
                .orElseThrow(() -> new GameNotFoundException("Game with name " + dto.gameName() + " not found"));
            
            ImageEntity image = imageMapper.toEntity(dto);
            image.setGame(game);
            ImageEntity saved = imageRepository.save(image);
            
            return Result.<ImageRespDto>builder()
                .success(true)
                .data(imageMapper.toRespDto(saved))
                .message("Image created successfully")
                .build();
        } catch (Exception e) {
            log.error("Error creating image: {}", e.getMessage(), e);
            return Result.<ImageRespDto>builder()
                .success(false)
                .data(null)
                .message("Error creating image: " + e.getMessage())
                .build();
        }
    }

    public Result<ImageRespDto> updateImage(@NonNull Long id, @NonNull ImageUpdateReqDto dto) {
        try {
            ImageEntity image = imageRepository.findById(id)
                .orElseThrow(() -> new ImageNotFoundException("Image with id " + id + " not found"));
            
            imageMapper.updateEntityFromDto(dto, image);
            ImageEntity updated = imageRepository.save(image);
            
            return Result.<ImageRespDto>builder()
                .success(true)
                .data(imageMapper.toRespDto(updated))
                .message("Image updated successfully")
                .build();
        } catch (Exception e) {
            log.error("Error updating image: {}", e.getMessage(), e);
            return Result.<ImageRespDto>builder()
                .success(false)
                .data(null)
                .message("Error updating image: " + e.getMessage())
                .build();
        }
    }

    public Result<String> deleteImage(@NonNull Long id) {
        try {
            if (!imageRepository.existsById(id)) {
                throw new ImageNotFoundException("Image with id " + id + " not found");
            }
            
            imageRepository.deleteById(id);
            return Result.<String>builder()
                .success(true)
                .data("Image deleted successfully")
                .message("Image with id " + id + " has been deleted")
                .build();
        } catch (Exception e) {
            log.error("Error deleting image: {}", e.getMessage(), e);
            return Result.<String>builder()
                .success(false)
                .data(null)
                .message("Error deleting image: " + e.getMessage())
                .build();
        }
    }
} 