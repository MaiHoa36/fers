package fpt.edu.eresourcessystem.utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class AudioUtils {
    public static void convertMp3ToWav(String mp3FilePath, String wavFilePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        File mp3File = new File(mp3FilePath);
        File wavFile = new File(wavFilePath);

        AudioInputStream mp3Stream = AudioSystem.getAudioInputStream(mp3File);
        AudioFormat baseFormat = mp3Stream.getFormat();
        AudioFormat targetFormat = new AudioFormat(
                AudioFormat.Encoding.PCM_SIGNED,
                baseFormat.getSampleRate(),
                16,
                baseFormat.getChannels(),
                baseFormat.getChannels() * 2,
                baseFormat.getSampleRate(),
                false
        );
        AudioInputStream wavStream = AudioSystem.getAudioInputStream(targetFormat, mp3Stream);

        AudioSystem.write(wavStream, AudioFileFormat.Type.WAVE, wavFile);

        mp3Stream.close();
        wavStream.close();
    }
}
