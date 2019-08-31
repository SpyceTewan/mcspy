package at.tewan.plugin.mcspy.nagios.queries;

import at.tewan.plugin.mcspy.nagios.Nagios;
import at.tewan.plugin.mcspy.nagios.NagiosQuery;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

@Nagios
public class QueryTotalPlayerCount implements NagiosQuery {

    @Override
    public void preInit(Plugin plugin) {

    }

    @Override
    public void postInit(Plugin plugin) {

    }

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
        return "TotalPlayerCount";
    }
}
