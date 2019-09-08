package nagios.exports;

import at.tewan.nagiosmc.Nagios;
import at.tewan.nagiosmc.NagiosExport;
import nagios.queries.QueryEntityCountAnimal;
import nagios.queries.QueryEntityCountTotal;

@Nagios
public class ExportEntityCount extends NagiosExport {

    public ExportEntityCount() {
        super("entity_count", QueryEntityCountTotal.class, QueryEntityCountAnimal.class);
    }
}
