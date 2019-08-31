package at.tewan.plugin.mcspy.nagios.exports;

import at.tewan.plugin.mcspy.nagios.Nagios;
import at.tewan.plugin.mcspy.nagios.NagiosExport;

@Nagios
public class ExportPlayerCount extends NagiosExport {

    public ExportPlayerCount() {
        super("Player Count", "TotalPlayerCount");
    }

}
