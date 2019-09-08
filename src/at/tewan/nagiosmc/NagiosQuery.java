package at.tewan.nagiosmc;

import org.bukkit.plugin.Plugin;

public interface NagiosQuery {

    public default void init(Plugin plugin) {

    }

    public float getValue();

    public float getMin();
    public float getMax();
    public String getName();

    public default float getWarning() {
        return -1;
    }

    public default float getCritical() {
        return -1;
    }

    /** @return Returns the data in the nagios performance format: \n name;value;warning;critical;min;max \n If warning or critical are -1, they will be omitted
     *
     *
     * */
    public default String getData() {

        char VALUE_SEPARATOR = ';';

        return getName() + VALUE_SEPARATOR +
                getValue() + VALUE_SEPARATOR +
                (getWarning() >= 0 ? getWarning() : "") + VALUE_SEPARATOR +
                (getCritical() >= 0 ? getCritical() : "") + VALUE_SEPARATOR +
                getMin() + VALUE_SEPARATOR +
                getMax() + VALUE_SEPARATOR;
    }

}
