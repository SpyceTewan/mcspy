package at.tewan.nagiosmc;

import com.sun.istack.internal.NotNull;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NagiosCommandHandler implements CommandExecutor, TabCompleter {

    static final String CMD_ROOT = "nagios";
    private static final String CMD_FORCE_EXPORT = "force";

    private Main main;

    public NagiosCommandHandler(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command cmd, @NotNull String s, @NotNull String[] args) {

        if(cmd.getName().equalsIgnoreCase(CMD_ROOT)) {

            if(args.length > 0) {

                if(args[0].equalsIgnoreCase(CMD_FORCE_EXPORT)) {
                    main.getNagiosDaemon().run();
                    sender.sendMessage("Export has been forced");
                    return true;
                }

            } else {
                return false;
            }

        }

        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String s, String[] args) {

        ArrayList<String> list = new ArrayList<>();

        if(args.length == 1) {
            list.add(CMD_FORCE_EXPORT);
        }

        return list;
    }
}
