package delta.cion;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class Sender {

    private static TelegramLongPollingBot BOT;

    public static void setBot(TelegramLongPollingBot bot) {
        BOT = bot;
    }

    public static void sendTelegram(String m) {
        SendMessage message = new SendMessage();
        message.setChatId("1934589854");
        message.setText(m);

        try {
            BOT.execute(message);
        } catch (TelegramApiException ignored) {}
    }

    public static void sendConsole(String m) {
        String p = "&5&lKataraLSD &7&l» ";
        Bukkit.getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', p+m));
    }
}
