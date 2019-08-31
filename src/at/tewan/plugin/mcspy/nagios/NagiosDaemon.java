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

    public static File exportContainer;

    private int interval; // Interval in seconds
    private int delay; // Time in seconds to wait before exporting
    private boolean initialized;
    private boolean debug;

    public NagiosDaemon(Main main) {

        debug = main.getConfig().getBoolean(DEBUG_KEY);

        System.out.println("Starting Nagios Export Daemon.");

        exportContainer = new File(main.getDataFolder(), "output");

        if(exportContainer.mkdir()) {
            System.out.println("Output directory does not exist. Created directory '" + exportContainer.toString() + "'");
        } else {
            System.out.println("Output directory found");
        }

        System.out.println("Reflecting exporters and queries");

        Reflections reflections = new Reflections();
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(Nagios.class);

        ArrayList<NagiosExport> exports = new ArrayList<>();
        ArrayList<NagiosQuery> queries = new ArrayList<>();

        for(Class<?> annotatedClass : annotatedClasses) {

            try {
                Object nagiosClass = (Object) annotatedClass.newInstance();

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
                System.out.print("Export '" + export.getName() + "' uses queries: [");
                for(String queryName : export.getQueryNames())
                    System.out.print(queryName + ", ");
                System.out.print("].");
            }
        }

        System.out.print("- - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
        System.out.println(exports.size() + " Exports and " + queries + " Queries found.");
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

        }



    }
}
