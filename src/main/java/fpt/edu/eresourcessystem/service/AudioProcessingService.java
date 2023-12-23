package fpt.edu.eresourcessystem.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class AudioProcessingService {

    public String processAudioFile(MultipartFile audioFile) throws IOException, InterruptedException {
        File convertedFile = convertAudioFile(audioFile);
        return transcribeAudio(convertedFile);
    }

    private File convertAudioFile(MultipartFile audioFile) throws IOException {
        File convertedFile = new File(System.getProperty("java.io.tmpdir") + "/" + audioFile.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile); InputStream is = audioFile.getInputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                fos.write(buffer, 0, bytesRead);
            }
        }
        return convertedFile;
    }

    private String transcribeAudio(File audioFile) throws IOException, InterruptedException {
        ProcessBuilder processBuilder = new ProcessBuilder("whisper", audioFile.getAbsolutePath());
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();

        // Read the output from the command
        String output = new String(process.getInputStream().readAllBytes());
        process.waitFor();

        // Delete the temporary file
        Files.deleteIfExists(Paths.get(audioFile.getAbsolutePath()));

        return output;
    }
}
