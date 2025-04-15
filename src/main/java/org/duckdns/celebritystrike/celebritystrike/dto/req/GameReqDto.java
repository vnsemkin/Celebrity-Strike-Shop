package org.duckdns.celebritystrike.celebritystrike.dto.req;

import java.util.List;

public record GameReqDto(
        String title,
        String description,
        String instruction,
        List<ImageReqDto> images,
        List<GameItemReqDto> items
) {}
