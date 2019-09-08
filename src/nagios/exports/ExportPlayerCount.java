package nagios.exports;

import at.tewan.nagiosmc.Nagios;
import at.tewan.nagiosmc.NagiosExport;
import nagios.queries.QueryPlayerCountTotal;

@Nagios
public class ExportPlayerCount extends NagiosExport {

    public ExportPlayerCount() {
        super("player_count", QueryPlayerCountTotal.class);
    }

}
