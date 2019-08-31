package at.tewan.plugin.mcspy.nagios;

import java.io.File;

import static at.tewan.plugin.mcspy.nagios.NagiosExporter.exportContainer;

public abstract class NagiosPerformanceExport {

    private static final char SEPARATOR = '|';

    private NagiosPerformanceData[] dataSets;
    private File targetFile;
    private String exportName;

    public NagiosPerformanceExport(String exportName, String... queries) {
        targetFile = new File(exportContainer, exportName + ".txt");
        this.exportName = exportName;
    }

    @Override
    public String toString() {

        String summary = "MCSpy Performance Export - " + exportName;
        String data = "";

        for(NagiosPerformanceData dataSet : dataSets) {
            data += dataSet.toString();
        }

        return summary + SEPARATOR + data;
    }
}
