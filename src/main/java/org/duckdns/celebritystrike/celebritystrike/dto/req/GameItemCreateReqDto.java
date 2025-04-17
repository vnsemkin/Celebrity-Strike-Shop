package org.duckdns.celebritystrike.celebritystrike.dto.req;

public record GameItemCreateReqDto(
    String name,
    String type,
    String region,
    String details,
    Integer price,
    String gameName
) {} 