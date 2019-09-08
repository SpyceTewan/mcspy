package at.tewan.nagiosmc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public abstract class NagiosExport {

    private static final char SEPARATOR = '|';

    private File targetFile;
    private String name;
    private Class[] queryClasses;
    private NagiosQuery[] queries;

    public NagiosExport(String exportName, Class... queries) {
        targetFile = new File(NagiosDaemon.exportContainer, exportName + ".txt");
        this.name = exportName;
        this.queryClasses = queries;
        this.queries = new NagiosQuery[queries.length];
    }

    @Override
    public String toString() {

        String summary = "MCSpy Performance Export - " + name;
        StringBuilder data = new StringBuilder();

        for(NagiosQuery query : getQueries()) {
            data.append(query.getData());
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

    public Class[] getQueryClasses() {
        return queryClasses;
    }

    public NagiosQuery[] getQueries() {
        return queries;
    }

    public String getName() {
        return name;
    }
}
