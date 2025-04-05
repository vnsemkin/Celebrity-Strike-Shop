package org.duckdns.celebritystrike.celebritystrike.service.update_handlers;

import java.util.function.Predicate;
import org.springframework.lang.NonNull;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateHandler {
    Predicate<Update> canHandle();
    void handle(@NonNull Update update);
}