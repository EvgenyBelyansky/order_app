package ru.pet_project.order_app.telegram.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface Command {

    SendMessage process(Update update);

}
