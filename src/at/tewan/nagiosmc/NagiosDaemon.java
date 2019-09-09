package at.tewan.nagiosmc;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import org.reflections.Reflections;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

public class NagiosDaemon implements Runnable {

    private static final String INTERVAL_KEY = "nagios.interval";
    private static final String DELAY_KEY = "nagios.delay";
    private static final String DEBUG_KEY = "nagios.debug";

    private static final String NAGIOS_PACKAGE = "nagios";

    static File exportContainer;

    private int interval; // Interval in seconds
    private int delay; // Time in seconds to wait before exporting
    private boolean initialized;
    private boolean debug;

    private ArrayList<NagiosExport> exports;
    private ArrayList<NagiosQuery> queries;

    public NagiosDaemon(Main main) {

        try {
            Class.forName("org.reflections.Reflections");
        } catch (ClassNotFoundException ex) {
            System.err.println("[MCSpy] CRITICAL. FAILED TO LOAD REFLECTIONS LIBRARY. ABORTING");
            main.onDisable();
            return;
        }

        debug = main.getConfig().getBoolean(DEBUG_KEY);

        System.out.println("Starting Nagios Export Daemon.");

        System.out.println("Debug mode: " + debug);

        exportContainer = new File(main.getDataFolder(), "exports");

        if(exportContainer.mkdir()) {
            debug("Output directory does not exist. Created directory '" + exportContainer.toString() + "'");
        } else {
            debug("Output directory found");
        }

        debug("Reflecting exporters and queries");

        Reflections reflections = new Reflections(NAGIOS_PACKAGE);
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(Nagios.class);

        exports = new ArrayList<>();
        queries = new ArrayList<>();

        for(Class<?> annotatedClass : annotatedClasses) {

            try {
                Object nagiosClass = annotatedClass.newInstance();

                if(nagiosClass instanceof NagiosExport) {
                    NagiosExport export = (NagiosExport) nagiosClass;
                    exports.add(export);
                }

                if(nagiosClass instanceof NagiosQuery) {
                    NagiosQuery query = (NagiosQuery) nagiosClass;
                    queries.add(query);
                }

            } catch (InstantiationException | IllegalAccessException e) {
                e.printStackTrace();
            }

        }

        // Assigning queries to exporters
        for(NagiosExport export : exports) {
            for(Class queryClass : export.getQueryClasses()) {
                for(NagiosQuery query : queries) {
                    if(query.getClass() == queryClass)
                        export.addQuery(query);
                }
            }

            // Debug output
            if(debug) {
                StringBuilder querySummary = new StringBuilder();

                for(Class queryClass : export.getQueryClasses())
                    querySummary.append(queryClass.getSimpleName()).append("|");

                debug("Export '" + export.getName() + "' uses queries: " + querySummary.toString());
            }
        }

        debug("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
        debug(exports.size() + " Exports and " + queries.size() + " Queries found.");
        debug("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");

        interval = main.getConfig().getInt(INTERVAL_KEY, 1000);
        delay = main.getConfig().getInt(DELAY_KEY, 300);

        debug("Running on an interval of " + interval + "s and a delay of " + delay + "s.");

        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.scheduleSyncRepeatingTask(main, this, delay, interval);
    }



    @Override
    public void run() {

        if(!initialized) {
            initialized = true;

            debug("=== Passed " + delay + "s delay. Starting to export on an interval of " + interval + "s. ===");
            return;
        }

        debug("Exporting..");

        for(NagiosExport export : exports) {
            export.write();
        }

    }

    private void debug(String message) {
        if(debug)
            System.out.println(message);
    }

    public ArrayList<NagiosExport> getExports() {
        return exports;
    }

    public ArrayList<NagiosQuery> getQueries() {
        return queries;
    }
}
