package nagios.queries;

import at.tewan.nagiosmc.Nagios;
import at.tewan.nagiosmc.NagiosQuery;

@Nagios
public class QueryEntityCountAnimal implements NagiosQuery {
    @Override
    public float getValue() {
        return 0;
    }

    @Override
    public float getMin() {
        return 0;
    }

    @Override
    public float getMax() {
        return 0;
    }

    @Override
    public String getName() {
        return "Animal Entity Count";
    }
}
