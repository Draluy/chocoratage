package fr.raluy.chocoratage;

import java.awt.event.KeyEvent;

import static fr.raluy.chocoratage.Utils.robotTyping;
import static fr.raluy.chocoratage.Utils.runProcess;

public enum LockMethod {

  XDG_SCREENSAVER {
    public void lock() {

      // Ubuntu Gnome, Fedora Gnome, Debian Cinnamon, openSUSE
      // Also read this variant online: $!(sleep 10s ;  xset dpms force suspend) & xdg-screensaver lock
      runProcess("xdg-screensaver", "lock");
    }
  },

  /**
   * Obviously this is something that works if this GNOME component is installed,
   * which is the case on many distributions including Debian, Fedora, Ubuntu, Mint Cinnamon, Zorin
   * ...and FreeBSD
   */
  GNOME_SESSION_QUIT {
    @Override
    void lock() {
      runProcess("gnome-session-quit --no-prompt");
    }
  },

  /**
   * This works in very rare cases incl FreeBSD
   */
  DBUS_SEND {
    @Override
    void lock() {
      runProcess("dbus-send --type=method_call --dest=org.gnome.ScreenSaver", "/org/gnome/ScreenSaver", "org.gnome.ScreenSaver.Lock");
    }
  },

  /**
   * Solaris, and FreeBSD if xlock is installed
   *   FreeBSD might also answer to xscreensaver-command -suspend (or -activate)
   *   or xflock4 & startxfce4 (for xfce obviously)
   */
  XLOCK {
    @Override
    public void lock() {
      runProcess("xlock");
    }
  },

  /**
   * Control+Alt+L
   * Works on Debian, Chakra, Ubuntu, Lubuntu, Mint, openSUSE, Mageia, Zorin Lite, and supposedly some Fedoras
   */
  CTRL_ALT_L {
    @Override
    void lock() {
      robotTyping(KeyEvent.VK_CONTROL, KeyEvent.VK_ALT, KeyEvent.VK_L);
    }
  },


  WINDOWS {
    public void lock() {
      runProcess(System.getenv("windir") + "\\System32\\rundll32.exe", "user32.dll,LockWorkStation");
    }
  },

  /**
   * Control+Shift+Power or Control+Shift+Escape for older MacBooks with an optical drive won't work from Java
   * Use this: https://apple.stackexchange.com/questions/80058/lock-screen-command-one-liner
   * And caution it will only really work after the "Security & Privacy" settings are configured
   * to "Require password *immediately* after sleep or screensaver begins"
   * https://www.howtogeek.com/howto/32810/how-to-lock-your-mac-os-x-display-when-youre-away/
   */
  OSX {
    @Override
    public void lock() {
      runProcess("/System/Library/CoreServices/Menu\\ Extras/user.menu/Contents/Resources/CGSession -suspend");
    }
  };


  public static final String XFCE = "XFCE";


  public static LockMethod guess(Os os) {
    if(!os.isLockMethodKnown()) {
      String desktopEnvironment = Os.getDesktopEnvironment();
      if(XFCE.equals(desktopEnvironment)) {
        return CTRL_ALT_L;
      } else {
        return XDG_SCREENSAVER;
      }
    } else {
      return os.getDefaultLockMethod();
    }
  }

  abstract void lock();
}
