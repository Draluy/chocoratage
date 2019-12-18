package fr.raluy.chocoratage;

import org.slf4j.LoggerFactory;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.IntStream;

public class Utils {
  private static final org.slf4j.Logger log = LoggerFactory.getLogger(Utils.class);

  public static String trimToNull(String s) {
    String result = null;
    if(s != null) {
      s = s.trim();
      if(!s.isEmpty()) {
        result = s;
      }
    }
    return result;
  }

  private static String unquote(String s) {
    String result = null;
    if(s != null) {
      int length = s.length();
      int start = s.charAt(0) == '"' ? 1 : 0;
      int end = s.charAt(length-1) == '"' ? length -1 : length;
      result = s.substring(start, end);
    }
    return result;
  }

  public static Optional<String> getFirstOptional(Collection<String> props) {
    return Optional.ofNullable(getFirst(props));
  }

  public static String getFirst(Collection<String> props) {
    return props.isEmpty() ? null : props.iterator().next();
  }

  /**
   * "Polyfill"-like for Stream#or present in Java 9+
   * @param optional
   * @param alternatives
   * @param <T>
   * @return
   */
  public static <T> Optional<T> or(Optional optional, Supplier<? extends Optional<T>>... alternatives) {
    if(optional.isPresent()) {
      return optional;
    } else {
      for(Supplier<? extends Optional<T>> alternative : alternatives) {
        Optional<T> altOptional = alternative.get();
        if(altOptional.isPresent()) {
          return altOptional;
        }
      }
    }
    return Optional.empty();
  }

  public static void runProcess(String... command) {
    if(Config.isDebugMode()) {
      log.info("Running command: " + String.join(" ", command));
    }

    ProcessBuilder processBuilder = new ProcessBuilder(command);
    Process process = null;
    try {
      process = processBuilder.start();
      process.waitFor();
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
      if (process != null) {
        process.destroy();
      }
    }
  }

  public static List<String> runProcessAndOutput(String... command) {
    if(Config.isDebugMode()) {
      log.info("Running command: " + String.join(" ", command));
    }

    ProcessBuilder processBuilder = new ProcessBuilder(command);
    Process process = null;
    List<String> output = new ArrayList<>(100);
    try {
      process = processBuilder.start();
      try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
        String line;
        while ((line = br.readLine()) != null) {
          output.add(line);
        }
      }
      process.waitFor();
    } catch (IOException | InterruptedException e) {
      throw new RuntimeException(e);
    } finally {
      if (process != null) {
        process.destroy();
      }
    }

    output.forEach(log::info);
    return output;
  }


  public static Optional<String> getValueForKey(String key, String separator, Collection<String> props) {
    String value = null;
    for (String prop : props) {
      int eq = prop.indexOf(separator);
      if (eq >= 0 && prop.substring(0, eq).equals(key)) {
        value = trimToNull(unquote(prop.substring(eq + separator.length())));
        break;
      }
    }
    return Optional.ofNullable(value);
  }

  public static Optional<String> getValueBefore(String separator, String line) {
    String value = null;
    if(separator == null) {
      value = line;
    }
    else if(line != null) {
      int eq = line.indexOf(separator);
      if (eq >= 0) {
        value = line.substring(0, eq);
      }
    }
    return Optional.ofNullable(trimToNull(unquote(value)));
  }

  public static Optional<String> getValueAfter(String separator, String line) {
    String value = null;
    if(separator == null) {
      value = line;
    }
    else if(line != null) {
      int eq = line.indexOf(separator);
      if (eq >= 0) {
        value = line.substring(eq + separator.length());
      }
    }
    return Optional.ofNullable(trimToNull(unquote(value)));
  }

  public static void robotTyping(int... keys) {
    try {
      Robot robot = new Robot();
      IntStream.of(keys).forEach(robot::keyPress);

      Thread.sleep(100L);

      IntStream.of(keys).forEach(robot::keyRelease);
    } catch (AWTException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}
