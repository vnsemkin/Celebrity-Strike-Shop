package org.duckdns.celebritystrike.celebritystrike.dto.resp;

import java.util.List;

public record GameRespDto(
         Long id,
         String name,
         String title,
         String description,
         String instruction,
         List<ImageRespDto>images,
         List<GameItemRespDto> items
) {}
