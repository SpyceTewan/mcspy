package at.tewan.plugin.mcspy.nagios;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static at.tewan.plugin.mcspy.nagios.NagiosDaemon.exportContainer;

public abstract class NagiosExport {

    private static final char SEPARATOR = '|';

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
        StringBuilder data = new StringBuilder();

        for(NagiosQuery query : getQueries()) {
            data.append(query.getSummary());
        }

        return summary + SEPARATOR + data;
    }

    public void write() {

        try (FileWriter targetFileWriter = new FileWriter(targetFile)) {

            targetFileWriter.write(toString());

        } catch(IOException ex) {
            ex.printStackTrace();
        }

    }

    public boolean addQuery(NagiosQuery query) {

        for(int i = 0; i < queries.length; i++) {
            if(queries[i] == query) return false;

            if(queries[i] == null) {
                queries[i] = query;
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
