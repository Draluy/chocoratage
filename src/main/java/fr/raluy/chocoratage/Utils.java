package fr.raluy.chocoratage;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Supplier;

public class Utils {

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

}
