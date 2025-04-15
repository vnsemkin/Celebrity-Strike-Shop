package org.duckdns.celebritystrike.celebritystrike.dto.resp;

public record GameItemRespDto(
        Long id,
        String name,
        String type,
        String region,
        String details,
        Integer price
) {}
