package org.duckdns.celebritystrike.celebritystrike.service.web_handlers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.duckdns.celebritystrike.celebritystrike.dto.req.MessageToChatReq;
import org.duckdns.celebritystrike.celebritystrike.model.Result;
import org.duckdns.celebritystrike.celebritystrike.sender.MessageSender;
import org.duckdns.celebritystrike.celebritystrike.util.MessageComposer;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DefaultWebHandler {
  private final MessageSender sender;

  public Result<String> send(long chatId, @NonNull MessageToChatReq messageToChatReq) {
    String message = messageToChatReq.message();
    try {
      log.info("Sending message to chat {}: {}", chatId, message);
      sender.send(MessageComposer.composeSendMessage(chatId, message));
      return Result.<String>builder()
          .success(true)
          .data("Message sent successfully")
          .message("Message sent to chat " + chatId)
          .build();
    } catch (Exception e) {
      log.error("Error sending message: {}", e.getMessage(), e);
      return Result.<String>builder()
          .success(false)
          .data(null)
          .message("Error sending message: " + e.getMessage())
          .build();
    }
  }
}
