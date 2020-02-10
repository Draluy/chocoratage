package fr.raluy.chocoratage;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class KeyLogger implements NativeKeyListener {
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(KeyLogger.class);

    private Set<Runnable> listeners = new HashSet<>();
    private KeyBuffer buffer = new KeyBuffer(Config.isStrict());

    public KeyLogger() {
        try {
            GlobalScreen.registerNativeHook();
            //GlobalScreen.setEventDispatcher(new DefaultDispatchService()); // https://github.com/kwhat/jnativehook/issues/277
            GlobalScreen.addNativeKeyListener(this);
        } catch (NativeHookException e) {
            String errMsg = "Impossible to register listening hook";
            log.info(errMsg);
            throw new ChocoratageException(errMsg, e);
        }
    }

    /**
     * keyCode is defined here, not keyChar
     * @param nativeKeyEvent
     */
    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {}

    /**
     * keyChar is defined here, not keyCode
     * Thankfully for us, backspace is a CHARACTER (ASCII control BS = 8),
     * else we would have to identify it in nativeKeyPressed (nativeKeyEvent.getKeyCode() == NativeKeyEvent.VC_BACKSPACE)
     * and we wouldn't be able to filter it out here
     * @param nativeKeyEvent
     */
    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {
        char keyChar = nativeKeyEvent.getKeyChar();
        log.debug("TYPED code: {} / char: {} ({})", nativeKeyEvent.getKeyCode(), keyChar, (int)keyChar);
        if(!nativeKeyEvent.isActionKey()) { // Caution: BACKSPACE, DELETE, ESCAPE, ENTER, TAB are NOT action keys, they are control CHARS!
            boolean bufferChanged = buffer.submitChar(keyChar);

            if (Config.isDebugMode()) {
                log.info("Buffer state = {}", buffer);
            }

            if (bufferChanged && buffer.containsIgnoreCase(Config.getForbiddenPhrases())) {
                buffer.clear();
                listeners.forEach(Runnable::run);
            }
        }
    }

    /**
     * keyCode is defined here, not keyChar
     * @param nativeKeyEvent
     */
    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {}

    public void addListener(Runnable function) {
        listeners.add(function);
    }
}
