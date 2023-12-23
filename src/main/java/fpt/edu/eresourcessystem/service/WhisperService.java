package fpt.edu.eresourcessystem.service;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WhisperService {

    @Value("${openai.api.key}")
    private String apiKey; // Inject your OpenAI API key from application.properties or application.yml

    private final String OPENAI_API_URL = "https://api.openai.com/v1/engines/whisper-1/completions";

    public String transcribeAudio(MultipartFile audioFile) {
        try {
            // Convert MultipartFile to byte array or handle file processing as needed
            byte[] audioBytes = audioFile.getBytes();

            // Prepare headers with API key
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + apiKey);
            headers.setContentType(MediaType.APPLICATION_JSON);

            // Prepare the request body
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("audio", Base64.getEncoder().encodeToString(audioBytes));

            // Make the API request
            HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> responseEntity = new RestTemplate().postForEntity(OPENAI_API_URL, requestEntity, Map.class);

            Thread.sleep(1000);
            // Handle the response and extract transcription
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                Map<String, Object> responseBody = responseEntity.getBody();
                if (responseBody != null && responseBody.containsKey("choices")) {
                    List<Map<String, String>> choices = (List<Map<String, String>>) responseBody.get("choices");
                    if (!choices.isEmpty() && choices.get(0).containsKey("text")) {
                        return choices.get(0).get("text");
                    }
                }
            }

            // Handle errors or return an appropriate default value
            return "Transcription not available";
        } catch (Exception e) {
            // Handle exceptions appropriately (e.g., log, throw a custom exception)
            e.printStackTrace();
            return "Error in transcription";
        }

    }


    private final RestTemplate restTemplate = new RestTemplate();

    public String transcribeAudio2(MultipartFile audioFile) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(apiKey);
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            ByteArrayResource byteArrayResource = new ByteArrayResource(audioFile.getBytes()) {
                @Override
                public String getFilename() {
                    return audioFile.getOriginalFilename();
                }
            };

            body.add("file", byteArrayResource);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(OPENAI_API_URL, requestEntity, String.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return response.getBody();
            } else {
                throw new ResponseStatusException(
                        response.getStatusCode(), "Error processing audio file: " + response.getBody());
            }
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to read audio file", e);
        } catch (RestClientException e) {
            if (e instanceof HttpClientErrorException.TooManyRequests) {
                throw new ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS, "API quota exceeded", e);
            }
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "API request failed", e);
        }
    }

}

