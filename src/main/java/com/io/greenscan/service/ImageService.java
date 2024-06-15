//
//package com.io.greenscan.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//
//@Service
//public class ImageService {
//
//    private final RestTemplate restTemplate;
//
//    @Autowired
//    public ImageService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    public String processImage(MultipartFile imageFile) {
//        try {
//            if (imageFile.isEmpty()) {
//                return "Image file is empty";
//            }
//
//            String flaskUrl = "http://127.0.0.1:5000/processImage";
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//            headers.setAcceptCharset(List.of(StandardCharsets.UTF_8));
//
//            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//            ByteArrayResource resource = new ByteArrayResource(imageFile.getBytes()) {
//                @Override
//                public String getFilename() {
//                    return imageFile.getOriginalFilename();
//                }
//            };
//
//            body.add("imageFile", resource);
//
//            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//
//            ResponseEntity<String> response = restTemplate.postForEntity(flaskUrl, requestEntity, String.class);
//
//            return response.getBody();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "Failed to process image: " + e.getMessage();
//        }
//    }
//}




//원본 이미지만 업로드 된 거
//package com.io.greenscan.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//
//@Service
//public class ImageService {
//
//    private final RestTemplate restTemplate;
//
//    @Autowired
//    public ImageService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    public String processImage(MultipartFile imageFile) {
//        try {
//            if (imageFile.isEmpty()) {
//                return "Image file is empty";
//            }
//
//            String flaskUrl = "http://127.0.0.1:5000/processImage";
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//            headers.setAcceptCharset(List.of(StandardCharsets.UTF_8));
//
//            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//            ByteArrayResource resource = new ByteArrayResource(imageFile.getBytes()) {
//                @Override
//                public String getFilename() {
//                    return imageFile.getOriginalFilename();
//                }
//            };
//
//            body.add("imageFile", resource);
//
//            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//
//            ResponseEntity<String> response = restTemplate.postForEntity(flaskUrl, requestEntity, String.class);
//
//            return response.getBody();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return "Failed to process image: " + e.getMessage();
//        }
//    }
//}


//package com.io.greenscan.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.ByteArrayResource;
//import org.springframework.http.*;
//import org.springframework.stereotype.Service;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//import java.nio.charset.StandardCharsets;
//import java.util.List;
//
//@Service
//public class ImageService {
//
//    private final RestTemplate restTemplate;
//
//    @Autowired
//    public ImageService(RestTemplate restTemplate) {
//        this.restTemplate = restTemplate;
//    }
//
//    public byte[] processImage(MultipartFile imageFile) {
//        try {
//            if (imageFile.isEmpty()) {
//                return null;
//            }
//
//            String flaskUrl = "http://127.0.0.1:5000/processImage";
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//            headers.setAcceptCharset(List.of(StandardCharsets.UTF_8));
//
//            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//            ByteArrayResource resource = new ByteArrayResource(imageFile.getBytes()) {
//                @Override
//                public String getFilename() {
//                    return imageFile.getOriginalFilename();
//                }
//            };
//
//            body.add("imageFile", resource);
//
//            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//
//            ResponseEntity<byte[]> response = restTemplate.postForEntity(flaskUrl, requestEntity, byte[].class);
//
//            return response.getBody();
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }
//}















package com.io.greenscan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class ImageService {

    private final RestTemplate restTemplate;

    @Autowired
    public ImageService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String processImage(MultipartFile imageFile) {
        try {
            if (imageFile.isEmpty()) {
                return "Image file is empty";
            }

            String flaskUrl = "http://127.0.0.1:5000/processImage";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.MULTIPART_FORM_DATA);
            headers.setAcceptCharset(List.of(StandardCharsets.UTF_8));

            MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
            ByteArrayResource resource = new ByteArrayResource(imageFile.getBytes()) {
                @Override
                public String getFilename() {
                    return imageFile.getOriginalFilename();
                }
            };

            body.add("imageFile", resource);

            HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(flaskUrl, requestEntity, String.class);

            System.out.println("Response from Flask: " + response.getBody());

            return response.getBody();
        } catch (IOException e) {
            e.printStackTrace();
            return "Failed to process image: " + e.getMessage();
        }
    }
}
