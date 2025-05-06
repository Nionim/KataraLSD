package delta.cion;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class KataraLSD extends JavaPlugin {

    public static Plugin getInstance() {
        return getPlugin(KataraLSD.class);
    }

    public void onEnable() {
        saveDefaultConfig();
        Bukkit.getScheduler().runTaskTimer(this, Pinger::ping, 0, 72000);
        Bukkit.getCommandMap().register(Util.reload().getName(), Util.reload());
    }
}
