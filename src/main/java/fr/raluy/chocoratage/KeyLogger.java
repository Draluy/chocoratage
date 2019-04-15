package fr.raluy.chocoratage;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class KeyLogger implements NativeKeyListener {

    Logger logger = LoggerFactory.getLogger(KeyLogger.class);

    private Set<Runnable> chocoListeners = new HashSet<>();
    private CircularBuffer circularBuffer = new CircularBuffer();

    public KeyLogger()  {
        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
        } catch (NativeHookException e) {
            String errMsg = "Impossible to register listening hook";
            logger.error(errMsg, e);
            throw new ChocoratageException(errMsg);
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        if (nativeKeyEvent.isActionKey()){
            return;
        }

        circularBuffer.add(nativeKeyEvent.getKeyText(nativeKeyEvent.getKeyCode()));

        if (circularBuffer.contains(ForbiddenWords.words)){
            circularBuffer.clear();
            chocoListeners.stream().forEach(runnable -> runnable.run());
        }

    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }

    public void onChocoblastage(Runnable function) {
        chocoListeners.add(function);
    }
}
