//package fpt.edu.eresourcessystem.utils;
//
//import com.google.cloud.speech.v1.*;
//import com.google.protobuf.ByteString;
//
//import javax.sound.sampled.*;
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.List;
//
//public class AudioUtils {
//    public static void convertMp3ToWav(String mp3FilePath, String wavFilePath) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
//        File mp3File = new File(mp3FilePath);
//        File wavFile = new File(wavFilePath);
//
//        AudioInputStream mp3Stream = AudioSystem.getAudioInputStream(mp3File);
//        AudioFormat baseFormat = mp3Stream.getFormat();
//        AudioFormat targetFormat = new AudioFormat(
//                AudioFormat.Encoding.PCM_SIGNED,
//                baseFormat.getSampleRate(),
//                16,
//                baseFormat.getChannels(),
//                baseFormat.getChannels() * 2,
//                baseFormat.getSampleRate(),
//                false
//        );
//        AudioInputStream wavStream = AudioSystem.getAudioInputStream(targetFormat, mp3Stream);
//
//        AudioSystem.write(wavStream, AudioFileFormat.Type.WAVE, wavFile);
//
//        mp3Stream.close();
//        wavStream.close();
//    }
//
//    public static String transcribeAudio(String audioFilePath) throws Exception {
//        // Sử dụng Google Cloud Speech-to-Text API
//        try (SpeechClient speechClient = SpeechClient.create()) {
//            // Đọc dữ liệu từ file âm thanh
//            Path path = Paths.get(audioFilePath);
//            byte[] data = Files.readAllBytes(path);
//            ByteString audioBytes = ByteString.copyFrom(data);
//
//            // Tạo yêu cầu
//            RecognitionConfig config =
//                    RecognitionConfig.newBuilder()
//                            .setEncoding(RecognitionConfig.AudioEncoding.LINEAR16)
//                            .setLanguageCode("en-US")  // Chọn ngôn ngữ
//                            .build();
//            RecognitionAudio audio = RecognitionAudio.newBuilder().setContent(audioBytes).build();
//
//            // Gửi yêu cầu và nhận kết quả
//            RecognizeResponse response = speechClient.recognize(config, audio);
//            List<SpeechRecognitionResult> results;
//            results = response.getResults();
//
//            // Lấy văn bản từ kết quả
//            StringBuilder transcribedText = new StringBuilder();
//            for (SpeechRecognitionResult result : results) {
//                SpeechRecognitionAlternative alternative = result.getAlternatives(0);
//                transcribedText.append(alternative.getTranscript());
//            }
//
//            return transcribedText.toString();
//        }
//    }
//
//    public static void main(String[] args) {
//        try {
//            String audioFilePath = "path/to/your/audio/file.wav";
//            String transcribedText = transcribeAudio(audioFilePath);
//            System.out.println("Transcribed Text: " + transcribedText);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//}
