package at.tewan.plugin.mcspy.nagios;

import at.tewan.plugin.mcspy.Main;
import com.sun.istack.internal.NotNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class NagiosCommandHandler implements CommandExecutor {

    private static final String CMD_ROOT = "nagios";
    private static final String CMD_FORCE_EXPORT = "force";

    private Main main;

    public NagiosCommandHandler(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(command.getName().equalsIgnoreCase(CMD_ROOT)) {

            if(args.length > 0) {

                if(args[0].equalsIgnoreCase(CMD_FORCE_EXPORT)) {
                    main.getNagiosDaemon().run();
                    commandSender.sendMessage("Export has been forced");
                    return true;
                }

            } else {
                return false;
            }

        }

        return false;
    }
}
