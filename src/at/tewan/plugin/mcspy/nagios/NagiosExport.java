package at.tewan.plugin.mcspy.nagios;

import java.io.File;

import static at.tewan.plugin.mcspy.nagios.NagiosDaemon.exportContainer;

public abstract class NagiosExport {

    private static final char SEPARATOR = '|';

    private NagiosData[] dataSets;
    private File targetFile;
    private String name;
    private String[] queryNames;
    private NagiosQuery[] queries;

    public NagiosExport(String exportName, String... queries) {
        targetFile = new File(exportContainer, exportName + ".txt");
        this.name = exportName;
        this.queryNames = queries;
        this.queries = new NagiosQuery[queries.length];
    }

    @Override
    public String toString() {

        String summary = "MCSpy Performance Export - " + name;
        String data = "";

        for(NagiosData dataSet : dataSets) {
            data += dataSet.toString();
        }

        return summary + SEPARATOR + data;
    }

    public boolean addQuery(NagiosQuery query) {

        for(NagiosQuery q : queries) {
            if(q == query) return false;

            if(q == null) {
                q = query;
                return true;
            }

        }

        return false;

    }

    public String[] getQueryNames() {
        return queryNames;
    }

    public NagiosQuery[] getQueries() {
        return queries;
    }

    public String getName() {
        return name;
    }
}
