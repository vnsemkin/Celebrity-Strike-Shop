package org.duckdns.celebritystrike.celebritystrike.service.update_handlers.message_handlers.text_message.command_handlers;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.duckdns.celebritystrike.celebritystrike.config.Commands;
import org.duckdns.celebritystrike.celebritystrike.sender.MessageSender;
import org.duckdns.celebritystrike.celebritystrike.util.MessageComposer;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.message.Message;

@Service
@RequiredArgsConstructor
public class DefaultCommandHandler implements CommandHandler {
    private final MessageSender sender;
    private final static String UNKNOWN_COMMAND = "I don't know this command";

    @Override
    public void handle(@NonNull Message message) {
        sender.send(MessageComposer.composeSendMessage(message.getChatId(), UNKNOWN_COMMAND));
    }

    @Override
    public String canHandle() {
        return Commands.DEFAULT.getValue();
    }
}
