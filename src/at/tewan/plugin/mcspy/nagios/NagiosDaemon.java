package at.tewan.plugin.mcspy.nagios;

import at.tewan.plugin.mcspy.Main;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;
import org.reflections.Reflections;
import sun.reflect.Reflection;
import sun.reflect.misc.ReflectUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Set;

public class NagiosDaemon implements Runnable {

    public static final String INTERVAL_KEY = "interval";
    public static final String DELAY_KEY = "delay";
    public static final String DEBUG_KEY = "debug";

    private static final String NAGIOS_PACKAGE = "nagios";

    private static final String OUTPUT_FOLDER = "exports";

    public static File exportContainer;

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

        exportContainer = new File(main.getDataFolder(), OUTPUT_FOLDER);

        if(exportContainer.mkdir()) {
            System.out.println("Output directory does not exist. Created directory '" + exportContainer.toString() + "'");
        } else {
            System.out.println("Output directory found");
        }

        System.out.println("Reflecting exporters and queries");

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
            for(String queryName : export.getQueryNames()) {
                for(NagiosQuery query : queries) {
                    if(query.getName().equalsIgnoreCase(queryName))
                        export.addQuery(query);
                }
            }

            // Debug output
            if(debug) {
                StringBuilder querySummary = new StringBuilder();

                for(String queryName : export.getQueryNames())
                    querySummary.append(queryName).append("|");

                System.out.print("Export '" + export.getName() + "' uses queries: " + querySummary.toString());
            }
        }

        System.out.print("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
        System.out.println(exports.size() + " Exports and " + queries.size() + " Queries found.");
        System.out.print("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");

        interval = main.getConfig().getInt(INTERVAL_KEY);
        delay = main.getConfig().getInt(DELAY_KEY);

        System.out.println("Running on an interval of " + interval + "s and a delay of " + delay + "s.");

        BukkitScheduler scheduler = Bukkit.getScheduler();
        scheduler.scheduleSyncRepeatingTask(main, this, delay, interval);
    }



    @Override
    public void run() {

        if(!initialized) {
            initialized = true;

            System.out.println("=== Passed " + delay + "s delay. Starting to export on an interval of " + interval + "s. ===");
            return;
        }

        for(NagiosExport export : exports) {
            export.write();
        }

    }
}
