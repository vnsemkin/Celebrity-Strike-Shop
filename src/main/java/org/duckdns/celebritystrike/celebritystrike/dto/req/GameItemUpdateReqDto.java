package org.duckdns.celebritystrike.celebritystrike.dto.req;

public record GameItemUpdateReqDto(
    String name,
    String type,
    String region,
    String details,
    Integer price
) {} 