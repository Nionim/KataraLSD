package delta.cion;

import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.ArrayList;
import java.util.List;

public class TGCommand {

    private static List<String> commands = new ArrayList<>();

    static {
        commands.add("lsd-reload");
        commands.add("lsd-ping");
    }

    public static boolean isCommand(Update u) {
        if (Util.isDebug()) Sender.sendConsole(commands.toString());
        if (!u.hasMessage() || !u.getMessage().hasText()) return false;
        return commands.contains(u.getMessage().getText());
    }

    public static void execute(String command) {
        if (Util.isDebug()) Sender.sendConsole(command);
        switch (command) {
            case "lsd-ping" -> Pinger.PING();
            case "lsd-reload" -> Util.reloadC(false);
        }
    }
}
