package fr.raluy.chocoratage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ProcessLauncher {

    public static List<String> runProcessAndOutput(String... command) {
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

        return output;
    }

}
