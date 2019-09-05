package nagios.queries;

import at.tewan.plugin.mcspy.nagios.Nagios;
import at.tewan.plugin.mcspy.nagios.NagiosQuery;
import org.bukkit.Bukkit;
import org.bukkit.World;

@Nagios
public class QueryTotalEntityCount implements NagiosQuery {


    @Override
    public float getValue() {

        int amount = 0;

        for(World world : Bukkit.getWorlds()) {
            amount += world.getEntities().size();
        }

        return amount;
    }

    @Override
    public float getMin() {
        return 0;
    }

    @Override
    public float getMax() {
        return 1024;
    }

    @Override
    public String getName() {
        return "Total Entity Count";
    }
}
