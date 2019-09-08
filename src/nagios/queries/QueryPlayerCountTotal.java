package nagios.queries;

import at.tewan.nagiosmc.Nagios;
import at.tewan.nagiosmc.NagiosQuery;
import org.bukkit.Bukkit;

@Nagios
public class QueryPlayerCountTotal implements NagiosQuery {

    @Override
    public float getValue() {
        return Bukkit.getOnlinePlayers().size();
    }

    @Override
    public float getMin() {
        return 0;
    }

    @Override
    public float getMax() {
        return Bukkit.getMaxPlayers();
    }

    @Override
    public String getName() {
        return "Total Player Count";
    }
}
