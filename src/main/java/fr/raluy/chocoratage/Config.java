package fr.raluy.chocoratage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

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
    private static Matcher matcher;
    private static boolean simulation;

    public static void parse(String... args) throws IOException {
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

                case "m":
                case "mode":
                    matcher = Matcher.valueOf(value.toUpperCase());
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
        if(matcher == null) {
            matcher = Matcher.LEVENSHTEIN;
        }
    }


    private static List<ForbiddenPhrase> readForbiddenPhrases() throws IOException {
        Locale locale = null;
        List<ForbiddenPhrase> phrases = new ArrayList<>();
        try (BufferedReader forbiddenPhrasesReader = (forbiddenPhrasesPath != null)
                ? Files.newBufferedReader(Paths.get(forbiddenPhrasesPath), forbiddenPhrasesCharset)
                : new BufferedReader(new InputStreamReader(Config.class.getResourceAsStream("/" + FORBIDDEN_LIST_DEFAULT), forbiddenPhrasesCharset))) {
            String phrase;
            while ((phrase = forbiddenPhrasesReader.readLine()) != null) {
                if (!phrase.trim().isEmpty()) {
                    phrases.add(new ForbiddenPhrase(phrase, locale));
                }
            }
        }
        return unmodifiableList(phrases);
    }


    private static void help() {
        System.out.println("Available options:");
        System.out.println("  h/help : this help");
        System.out.println("  d/debug : trace events on the console");
        System.out.println("  f/forbidden=<path> : supply a list of forbidden phrases, one per line, each may have several words, to replace the default one");
        System.out.println("  l/lock : lock session at startup");
        System.out.println("  o/os=<os> : force the os, available values are: "
                + stream(Os.values()).map(Os::name).collect(joining(", ", "[", "]")));
        System.out.println("  m/mode=<equal/levenshtein/normalizer> : matching algorithm, default is Levenshtein");
        System.out.println("  s/simu : log instead of locking");
    }

    public static boolean isDebugMode() {
        return debugMode;
    }

    public static boolean isTestSessionLocking() {
        return testSessionLocking;
    }

    public static Matcher getMatchingMode() {
        return matcher;
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
