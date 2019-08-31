package at.tewan.plugin.mcspy.nagios;

import at.tewan.plugin.mcspy.Main;
import com.google.common.reflect.Reflection;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitScheduler;

import java.io.File;

public class NagiosExporter implements Runnable {

    public static final String INTERVAL_KEY = "interval";

    public static File exportContainer;

    private BukkitScheduler scheduler;
    private int interval; // Interval in seconds

    public NagiosExporter(Main main) {



        interval = main.getConfig().getInt("interval");

        scheduler = Bukkit.getScheduler();
        scheduler.runTaskTimer(main, this, );
    }

    @Override
    public void run() {

    }
}
