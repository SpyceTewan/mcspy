package at.tewan.plugin.mcspy;

import com.sun.istack.internal.NotNull;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler implements CommandExecutor, TabCompleter {

    private static final String COMMAND_CONFIG = "mcspy";
    private static final String COMMAND_WIRETAP = "wiretap";
    private static final String PERM_CONFIG = "mcspy.config";
    private static final String PERM_WIRETAP = "mcspy.wiretap";

    private Main plugin;

    CommandHandler(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if(command.getName().equalsIgnoreCase(COMMAND_CONFIG)) {

            commandSender.sendMessage("Todo: Implement this");
            return true;

        } else if(command.getName().equalsIgnoreCase(COMMAND_WIRETAP)) {

            if(!(commandSender instanceof Player)) {

                commandSender.sendMessage("Can only be used by players");
                return true;
            }

            if(args.length == 2) {

                String targetName = args[0];
                String action = args[1];

                ArrayList<Player> targets = new ArrayList<>();

                if(targetName.equalsIgnoreCase("*")) { // Wildcard = Affect all players

                } else {

                    targets.add(Bukkit.getPlayer(targetName));

                }

                for(Player target : targets)

                    if(target != null) {

                        if(action.equalsIgnoreCase("start")) {

                            commandSender.sendMessage(plugin.getWiretap().wiretap((Player) commandSender, target, true));

                        } else if (action.equalsIgnoreCase("stop")) {

                            commandSender.sendMessage(plugin.getWiretap().wiretap((Player) commandSender, target, false));

                        } else {
                            return false;
                        }

                    } else {
                        commandSender.sendMessage("Player " + targetName + " does not exist");
                    }

                return true;

            } else {
                return false;
            }

        } else {
            return false;
        }
    }


    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {

        ArrayList<String> callback = new ArrayList<>();

        if(command.getName().equalsIgnoreCase(COMMAND_CONFIG)) {




        } else if(command.getName().equalsIgnoreCase(COMMAND_WIRETAP)) {

            if(args.length == 0) {

                callback.add("*");
                for(Player p : Bukkit.getOnlinePlayers())
                    callback.add(p.getName());

            } else if(args.length == 1) {
                callback.add("start");
                callback.add("stop");
            }

        }

        return callback;
    }
}
