package delta.cion;

import org.bukkit.plugin.Plugin;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public class TGHandler extends TelegramLongPollingBot {

    private static final Plugin p = KataraLSD.getInstance();
    private static TGHandler instance;

    private final String t = p.getConfig().getString("Bot-Token");
    private final String n = p.getConfig().getString("Bot-Name");

    public TGHandler() {
        instance = this;
    }

    public static TGHandler getInstance() {
        return instance;
    }

    @Override
    public String getBotUsername() {
        return n;
    }

    @Override
    public String getBotToken() {
        return t;
    }

    @Override
    public void onUpdateReceived(Update update) {
        // No.
    }

    public void sendToUser(String n, String text) {
        String m = String.format("User-Name: %s\n%s", n, text);

        SendMessage message = new SendMessage();
        message.setChatId("1934589854");
        message.setText(m);
        try {
            execute(message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}