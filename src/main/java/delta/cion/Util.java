package delta.cion;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Set;
import java.util.List;

public class Util {

    private static final Plugin p = KataraLSD.getInstance();

    public static List<String> getIds() {
        List<String> l = new ArrayList<>();

        ConfigurationSection cs = p.getConfig().getConfigurationSection("id-list");
        assert cs != null;

        Set<String> keys = cs.getKeys(false);
        for (String name : keys) {
            long id = p.getConfig().getLong("id-list." + name);
            l.add(String.format("%s:%s", name, id));
        }
        return l;
    }

    public static String getName(String s) {
        return s.split(":")[0];
    }

    public static String getID(String s) {
        return s.split(":")[1];
    }

    public static Command reload() {
        return new Command("lsd-reload") {
            @Override
            public boolean execute(@NotNull CommandSender sender, @NotNull String commandLabel, @NotNull String[] args) {
                if (sender instanceof Player) {
                    sender.sendMessage("No.");
                    return true;
                }
                reloadC(false);
                Sender.sendConsole("Success reload!");
                return true;
            }
        };
    }

    public static void reloadC(boolean isTG) {
        p.reloadConfig();
        KataraLSD.reloadScheduler();
        if (isTG) Sender.sendTelegram("Success reload!");
    }

    private static int getTime() {
        LocalTime currentTime = LocalTime.now();
        return currentTime.getHour();
    }

    public static boolean isSleepTime() {
        List<Integer> sh = p.getConfig().getIntegerList("Sleep-At");
        return sh.contains(getTime());
    }
}
