package at.tewan.plugin.mcspy;

import at.tewan.plugin.mcspy.nagios.NagiosDaemon;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;

import static at.tewan.plugin.mcspy.nagios.NagiosDaemon.*;

public class Main extends JavaPlugin {

    private Wiretap wiretap;
    private NagiosDaemon nagiosDaemon;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        nagiosDaemon = new NagiosDaemon(this);

        CommandHandler cmdHandler = new CommandHandler(this);

        getCommand("mcspy").setExecutor(cmdHandler);
        getCommand("wiretap").setExecutor(cmdHandler);

        wiretap = new Wiretap(this);
        getServer().getPluginManager().registerEvents(wiretap, this);
    }

    public NagiosDaemon getNagiosDaemon() {
        return nagiosDaemon;
    }

    public Wiretap getWiretap() {
        return wiretap;
    }
}
