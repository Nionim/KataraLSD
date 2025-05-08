package delta.cion;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public class KataraLSD extends JavaPlugin {

    private static int taskID;

    public static Plugin getInstance() {
        return getPlugin(KataraLSD.class);
    }

    public static void runScheduler() {
        BukkitTask bt = Bukkit.getScheduler().runTaskTimer(getInstance(), Pinger::ping, 0, getInstance().getConfig().getLong("Timer") * 20);
        taskID = bt.getTaskId();
    }

    public static void reloadScheduler() {
        Bukkit.getScheduler().cancelTask(taskID);
        runScheduler();
    }

    public void onEnable() {
        saveDefaultConfig();
        TGHandler tgHandler = new TGHandler();
        runScheduler();
        Bukkit.getCommandMap().register(Util.reload().getName(), Util.reload());
    }
}
