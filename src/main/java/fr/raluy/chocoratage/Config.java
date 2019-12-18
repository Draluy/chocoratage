package fr.raluy.chocoratage;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.Boolean.parseBoolean;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static java.util.Collections.unmodifiableList;
import static java.util.stream.Collectors.joining;

public class Config {

    public static final String FORBIDDEN_LIST_DEFAULT = "forbidden.lst";

    private static String forbiddenPhrasesPath;
    private static Charset forbiddenPhrasesCharset;
    private static List<ForbiddenPhrase> forbiddenPhrases;
    private static boolean debugMode;
    private static boolean testSessionLocking;
    private static Os forcedOs;
    private static boolean strict;
    private static boolean simulation;

    public static void parse(String... args) throws IOException, URISyntaxException {
        Iterator<String> it = asList(args).listIterator();
        if (!it.hasNext()) {
            help();
        }

        while (it.hasNext()) {
            String arg = it.next();
            String value = null;
            int i = arg.indexOf("=");
            if (i >= 0) {
                value = arg.substring(i + 1); // before altering arg, please!
                arg = arg.substring(0, i);
            }
            arg = arg.toLowerCase();

            switch (arg) {
                case "h":
                case "help":
                    help();
                    break;

                case "c":
                case "cs":
                case "charset":
                    forbiddenPhrasesCharset = Charset.forName(value);
                    break;

                case "d":
                case "debug":
                    debugMode = true;
                    break;

                case "f":
                case "forbidden":
                    forbiddenPhrasesPath = value;
                    break;

                case "l":
                case "lock":
                    testSessionLocking = true;
                    break;

                case "o":
                case "os":
                    forcedOs = Os.valueOf(value.toUpperCase());
                    break;

                case "strict":
                    strict = value == null || parseBoolean(value);
                    break;

                case "s":
                case "simu":
                    simulation = true;
                    break;
            }
        }

        if (forbiddenPhrasesCharset == null) {
            forbiddenPhrasesCharset = StandardCharsets.UTF_8;
        }
        if ((forbiddenPhrases = readForbiddenPhrases()).isEmpty()) {
            throw new IllegalArgumentException("No forbidden phrases supplied.");
        }
    }


    private static List<ForbiddenPhrase> readForbiddenPhrases() throws IOException, URISyntaxException {
        Path phrasesPath = forbiddenPhrasesPath == null ?
                Paths.get((Config.class.getResource("/" + FORBIDDEN_LIST_DEFAULT).toURI())) :
                Paths.get(forbiddenPhrasesPath);

        List<ForbiddenPhrase> words = Files.readAllLines(phrasesPath)
                .stream()
                .filter(s -> !s.isEmpty())
                .map(s -> new ForbiddenPhrase(s))
                .collect(Collectors.toList());

        return unmodifiableList(words);
    }


    private static void help() {
        System.out.println("Available options:");
        System.out.println("  h/help : this help");
        System.out.println("  d/debug : trace events on the console");
        System.out.println("  f/forbidden=<path> : supply a list of forbidden phrases, one per line, each may have several words, to replace the default one");
        System.out.println("  l/lock : lock session at startup");
        System.out.println("  o/os=<os> : force the os, available values are: "
                + stream(Os.values()).map(Os::name).collect(joining(", ", "[", "]")));
        System.out.println("  strict=<true/false> : pattern matching must match exactly, default false");
        System.out.println("  s/simu : log instead of locking");
    }

    public static boolean isDebugMode() {
        return debugMode;
    }

    public static boolean isTestSessionLocking() {
        return testSessionLocking;
    }

    public static boolean isStrict() {
        return strict;
    }

    public static boolean isSimulation() {
        return simulation;
    }

    public static Os getForcedOs() {
        return forcedOs;
    }

    public static List<ForbiddenPhrase> getForbiddenPhrases() {
        return forbiddenPhrases;
    }
}
