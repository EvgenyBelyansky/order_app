package ru.pet_project.order_app.telegram.command;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class HelpCommand implements Command {
    @Override
    public SendMessage process(Update update) {
        return new SendMessage(
                update.getMessage().getChatId().toString(),
                ("Привет %s! Список команд:\n" +
                        "/start — регистрация\n" +
                        "/neworder Имя | Описание — создать заказ\n" +
                        "/orders — мои заказы\n" +
                        "/order N — заказ №N\n" +
                        "/process N — в обработку\n" +
                        "/ship N — отправлен\n" +
                        "/done N — выполнен\n" +
                        "/cancel N — отменить").formatted(update.getMessage().getFrom().getFirstName())
        );
    }
}
