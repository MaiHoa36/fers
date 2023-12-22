package fpt.edu.eresourcessystem.utils;

import com.google.cloud.speech.v1.*;
import com.google.protobuf.ByteString;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

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

    public static String convertAudioToText(byte[] audioBytes) throws Exception {
        try (SpeechClient speechClient = SpeechClient.create()) {
            RecognitionConfig config =
                    RecognitionConfig.newBuilder()
                            .setEncoding(RecognitionConfig.AudioEncoding.MP3)
                            .setLanguageCode("en-US")
                            .setSampleRateHertz(16000)
                            .build();
            RecognitionAudio audio = RecognitionAudio.newBuilder()
                    .setContent(ByteString.copyFrom(audioBytes))
                    .build();

            RecognizeResponse response = speechClient.recognize(config, audio);
            StringBuilder transcript = new StringBuilder();
            for (SpeechRecognitionResult result : response.getResultsList()) {
                SpeechRecognitionAlternative alternative = result.getAlternativesList().get(0);
                transcript.append(alternative.getTranscript());
            }
            return transcript.toString();
        }
    }


}
