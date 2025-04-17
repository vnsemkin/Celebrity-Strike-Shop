package org.duckdns.celebritystrike.celebritystrike.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Builder
@AllArgsConstructor
public class Result<T> {
    @Getter
    private final boolean success;
    private final T data;
    private final String message;

    public Optional<T> getData() {
        return Optional.ofNullable(data);
    }

    public Optional<String> getMessage() {
        return Optional.ofNullable(message);
    }
}
