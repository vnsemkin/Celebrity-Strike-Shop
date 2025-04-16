package org.duckdns.celebritystrike.celebritystrike.dto.req;

import java.util.List;

public record GameReqDto(
        String name,
        String title,
        String description,
        String instruction,
        List<ImageReqDto> images,
        List<GameItemReqDto> items
) {}
