package at.tewan.plugin.mcspy.nagios;

public class NagiosData {

    private static final char VAL_SEPARATOR = ';';

    private float min, max, warning, critical, value;
    private String name;

    public NagiosData(int min, int max) {
        this(min, max, -1, -1);
    }

    public NagiosData(int min, int max, int warning, int critical) {
        this.min = min;
        this.max = max;
        this.warning = warning;
        this.critical = critical;
    }

    /**
     * @return Returns NagiosXI Performance data as a string to be placed after the | in performance outputs
     */
    @Override
    public String toString() {         // Ew
        return  name + VAL_SEPARATOR +
                value + VAL_SEPARATOR +
                (warning > 0 ? warning : "") + VAL_SEPARATOR +
                (critical > 0 ? critical : "") + VAL_SEPARATOR +
                min + VAL_SEPARATOR +
                max + VAL_SEPARATOR;
    }
}
