package at.tewan.nagiosmc;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private NagiosDaemon nagiosDaemon;

    @Override
    public void onEnable() {

        saveDefaultConfig();

        nagiosDaemon = new NagiosDaemon(this);

        NagiosCommandHandler nagiosCmdHandler = new NagiosCommandHandler(this);

        getCommand(NagiosCommandHandler.CMD_ROOT).setExecutor(nagiosCmdHandler);
        getCommand(NagiosCommandHandler.CMD_ROOT).setTabCompleter(nagiosCmdHandler);
    }

    public NagiosDaemon getNagiosDaemon() {
        return nagiosDaemon;
    }
}
