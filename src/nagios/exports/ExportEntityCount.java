package nagios.exports;

import at.tewan.plugin.mcspy.nagios.Nagios;
import at.tewan.plugin.mcspy.nagios.NagiosExport;

@Nagios
public class ExportEntityCount extends NagiosExport {

    public ExportEntityCount() {
        super("entity_count", "Total Entity Count");
    }
}
