package nagios.exports;

import at.tewan.plugin.mcspy.nagios.Nagios;
import at.tewan.plugin.mcspy.nagios.NagiosExport;

@Nagios
public class ExportPlayerCount extends NagiosExport {

    public ExportPlayerCount() {
        super("player_count", "Total Player Count");
    }

}
