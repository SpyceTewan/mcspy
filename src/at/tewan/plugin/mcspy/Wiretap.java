package at.tewan.plugin.mcspy;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.HashMap;

public class Wiretap implements Listener {

    private Main plugin;

    private HashMap<Player, ArrayList<Player>> wiretapList = new HashMap<>();

    Wiretap(Main plugin) {
        this.plugin = plugin;
    }

    public String wiretap(Player spy, Player target, boolean start) {

        if(!wiretapList.containsKey(spy)) {
            wiretapList.put(spy, new ArrayList<>());
        }

        if(start) {
            if(!wiretapList.get(spy).contains(target)) {
                wiretapList.get(spy).add(target);
                return "Target has been wiretapped";
            } else
                return "Target is already wiretapped";
        } else {
            if(wiretapList.get(spy).contains(target)) {
                wiretapList.get(spy).remove(target);
                return "Wiretap has been removed from target";
            } else
                return "Target was not wiretapped";
        }

    }
}
