package ru.pet_project.order_app.telegram.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class StartCommand implements Command {
    @Override
    public SendMessage process(Update update) {
        return new SendMessage(
                update.getMessage().getChatId().toString(),
                "Привет %s! Ты зарегистрирован.".formatted(
                        update.getMessage().getFrom().getFirstName())
        );
    }
}
