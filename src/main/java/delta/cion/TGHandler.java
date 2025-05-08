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
        Sender.setBot(this);
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
        if (TGCommand.isCommand(update))
            TGCommand.execute(update.getMessage().getText());
        else {
            if (Util.isDebug()) Sender.sendConsole("Command don`t detected!");
        }
    }
}