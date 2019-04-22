package fr.raluy.chocoratage;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

public class KeyLogger implements NativeKeyListener {

    private Set<Runnable> chocoListeners = new HashSet<>();
    private CircularBuffer circularBuffer = new CircularBuffer();

    public KeyLogger() {
        try {
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeKeyListener(this);
        } catch (NativeHookException e) {
            String errMsg = "Impossible to register listening hook";
            System.out.println(errMsg);
            throw new ChocoratageException(errMsg, e);
        }
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {

        getIfNiceKey(nativeKeyEvent)
                .ifPresent(keyEvent -> circularBuffer.add(keyEvent));

        if (circularBuffer.containsUppercase(ForbiddenWords.words)) {
            circularBuffer.clear();
            chocoListeners.stream().forEach(runnable -> runnable.run());
        }
    }

    public Optional<NativeKeyEvent> getIfNiceKey(NativeKeyEvent nativeKeyEvent) {
        if (nativeKeyEvent == null || nativeKeyEvent.isActionKey()) {
            return Optional.empty();
        }
        return Optional.of(nativeKeyEvent);
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }

    public void onChocoblastage(Runnable function) {
        chocoListeners.add(function);
    }
}
