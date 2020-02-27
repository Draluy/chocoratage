package fr.raluy.chocoratage;

import fr.raluy.chocoratage.locking.LockMethod;

import java.awt.event.KeyEvent;
import java.util.Arrays;
import java.util.HashMap;

import static java.util.Arrays.copyOfRange;

/**
 * Tries different locking combinations on the host
 */
public class OsTest {

  private static final String[] DEFAULT_KEYS_COMBINATIONS = {"Win+L", "Ctrl+Alt+L", "Ctrl+Shift+Esc"}; // Windows and some Linux, some Linux, Some OSX
  private static final LockMethod[] UNIX_LOCK_METHODS = {LockMethod.XDG_SCREENSAVER, LockMethod.GNOME_SESSION_QUIT, LockMethod.DBUS_SEND, LockMethod.XLOCK};

  private final static HashMap<String, Integer> KEY_MAPPING = new HashMap<>();
  private static final long ATTEMPT_DELAY = 2000L;


  private static final String ARG_TEST_KEYS = "keys";
  private static final String ARG_TEST_CMD = "cmd";
  private static final String ARG_TEST_ALL = "all";
  public static final String HELP = "Use 'keys', 'cmd' or 'all' as first argument to test locking key combinations, command-line combinations, or both.\nIf using keys or all, next arguments shall be one or several key combinations (eg: Ctrl+Alt+L). Else some are tried by default.";


  static {
    KEY_MAPPING.put("CTRL", KeyEvent.VK_CONTROL);
    KEY_MAPPING.put("ALT", KeyEvent.VK_ALT);
    KEY_MAPPING.put("ALTGR", KeyEvent.VK_ALT_GRAPH);
    KEY_MAPPING.put("SHIFT", KeyEvent.VK_SHIFT);
    KEY_MAPPING.put("WIN", KeyEvent.VK_WINDOWS); // PCs in general
    KEY_MAPPING.put("META", KeyEvent.VK_META); // Mac
    KEY_MAPPING.put("ESC", KeyEvent.VK_ESCAPE);
    KEY_MAPPING.put("DEL", KeyEvent.VK_DELETE);
    KEY_MAPPING.put("BS", KeyEvent.VK_BACK_SPACE);
  }

  public static void main(String... args) throws InterruptedException {
    if(args.length == 0) {
      System.out.println(HELP);
    }
    else {
      String arg = args[0].toLowerCase();
      boolean testKeys, testCmd;
      if(ARG_TEST_ALL.equals(arg)) {
        testKeys = testCmd = true;
      } else {
        testKeys = ARG_TEST_KEYS.equals(arg);
        testCmd = ARG_TEST_CMD.equals(arg);
      }

      if(!testKeys && !testCmd) {
        System.out.println(HELP);
      }
      else {
        // OS identification
        Arrays.stream(Os.getOsInfos()).forEach(System.out::println);

        // Key combination tests
        if(testKeys) {
          System.out.println();
          String[] combinations = (args.length -1 > 0) ? copyOfRange(args, 1, args.length) : DEFAULT_KEYS_COMBINATIONS; // first arg is the test type
          attemptKeyboardLocking(combinations);
        }

        // Command-line tests
        if(testCmd) {
          System.out.println();
          attemptCommandLineLocking();
        }

        System.out.println();
        System.out.println("Done.");
      }
    }
  }

  public static void attemptKeyboardLocking(String[] combinations) throws InterruptedException {
    for (String combination : combinations) {
      System.out.println("Attempting: " + combination);
      int[] codes = combinationToKeyCodes(combination);
      try {
        LockMethod.robotTyping(codes);
        Thread.sleep(ATTEMPT_DELAY);
      }
      catch(IllegalArgumentException e) { // We'll have one if we invoke the META key from another machine than a Mac for instance.
        e.printStackTrace();
      }
    }
  }

  public static void attemptCommandLineLocking() throws InterruptedException {
    Os guessedOs = Os.guess();
    switch (guessedOs) {
      case LINUX:
      case FREEBSD:
        for (LockMethod lockMethod : UNIX_LOCK_METHODS) {
          System.out.println("Attempting usual lock method: " + lockMethod);
          lockMethod.lock();
          Thread.sleep(ATTEMPT_DELAY);
        }
        break;

      default:
        if (guessedOs.isLockMethodKnown()) {
          LockMethod defaultLockMethod = guessedOs.getDefaultLockMethod();
          System.out.println("Attempting usual lock method: " + defaultLockMethod);
          defaultLockMethod.lock();
        }
    }
  }

  /**
   * Transforms a combination (eg: Win+L) into an array of virtual keyboard key codes
   *
   * @param combination
   * @return
   */
  private static int[] combinationToKeyCodes(String combination) {
    String[] keys = combination.toUpperCase().split("\\+");

    int[] codes = new int[keys.length];
    for (int i = 0; i < keys.length; i++) {
      String key = keys[i];
      if (key.length() == 1) { // just a letter
        codes[i] = KeyEvent.getExtendedKeyCodeForChar(key.charAt(0));
      } else {
        codes[i] = KEY_MAPPING.computeIfAbsent(key, s -> { throw new IllegalArgumentException("Can't recognize key: " + key); });
      }
    }
    return codes;
  }
}