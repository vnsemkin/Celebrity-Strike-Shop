package org.duckdns.celebritystrike.celebritystrike.service.update_handlers;


import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Slf4j
@Service
@RequiredArgsConstructor
public class UpdateHandlerService {
    private final static String HANDLER_NOT_FOUND = "Update handler {} not found";
    private final List<UpdateHandler> updateHandlers;

    public void handle(@NonNull Update update) {
        updateHandlers.stream()
                .filter(handler -> handler.canHandle().test(update))
                .findFirst()
                .ifPresentOrElse(handler -> handler.handle(update),
                        () -> log.info(HANDLER_NOT_FOUND, update.getClass().getSimpleName()));
    }
}