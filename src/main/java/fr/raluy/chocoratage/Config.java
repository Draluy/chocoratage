package fr.raluy.chocoratage;

import java.util.Iterator;

import static java.lang.Boolean.parseBoolean;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

public class Config {

    private static boolean debugMode;
    private static boolean testSessionLocking;
    private static boolean simulation;
    private static boolean relax;
    private static Os forcedOs;

    public static void parse(String[] args) {
        Iterator<String> it = asList(args).listIterator();
        if(!it.hasNext()) {
            help();
        }

        while(it.hasNext()) {
            String arg = it.next();
            String value = null;
            int i = arg.indexOf("=");
            if(i >= 0) {
                value = arg.substring(i+1); // before altering arg, please!
                arg = arg.substring(0, i);
            }
            arg = arg.toLowerCase();

            switch (arg) {
                case "h":
                case "help":
                    help();
                    break;
                case "d":
                case "debug":
                    debugMode = true;
                    break;
                case "l":
                case "lock":
                    testSessionLocking = true;
                    break;
                case "o":
                case "os":
                    if(it.hasNext()) {
                        forcedOs = Os.valueOf(value.toUpperCase());
                    }
                    break;
                case "r":
                case "relax":
                    relax = value == null || parseBoolean(value);
                    break;
                case "s":
                case "simu":
                    simulation = true;
                    break;
            }
        }
    }

    private static void help() {
        System.out.println("Available options:");
        System.out.println("  h/help : this help");
        System.out.println("  d/debug : trace events on the console");
        System.out.println("  l/lock : lock session at startup");
        System.out.println("  o/os=<os> : force the os, available values are: "
                + stream(Os.values()).map(Os::name).collect(joining(", ", "[", "]")));
        System.out.println("  r/relax=<true/false> : pattern matching is more lenient, default false");
        System.out.println("  s/simu : log instead of locking");
    }

    public static boolean isDebugMode() {
        return debugMode;
    }

    public static boolean isTestSessionLocking() {
        return testSessionLocking;
    }

    public static boolean isRelax() {
        return relax;
    }

    public static boolean isSimulation() {
        return simulation;
    }

    public static Os getForcedOs() {
        return forcedOs;
    }
}
