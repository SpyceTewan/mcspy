package at.tewan.plugin.mcspy.nagios;

import org.bukkit.plugin.Plugin;

public interface NagiosQuery {

    public default void preInit(Plugin plugin) {

    }

    public default void postInit(Plugin plugin) {

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

    public default String getSummary() {

        char VALUE_SEPARATOR = ';';

        return getName() + VALUE_SEPARATOR +
                getValue() + VALUE_SEPARATOR +
                (getWarning() >= 0 ? getWarning() : "") + VALUE_SEPARATOR +
                (getCritical() >= 0 ? getCritical() : "") + VALUE_SEPARATOR +
                getMin() + VALUE_SEPARATOR +
                getMax() + VALUE_SEPARATOR;
    }

}
