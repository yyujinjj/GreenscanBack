//package com.io.greenscan.controller;
//
//import com.io.greenscan.service.ImageService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.http.*;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//@Slf4j
//@RestController
//@RequestMapping("/api/user")
//public class ImageController {
//
//    private final ImageService imageService;
//
//    @Autowired
//    public ImageController(ImageService imageService) {
//        this.imageService = imageService;
//        log.info("이건 되야지");
//
//    }
//
//    @PostMapping("/uploadImage")
//    public String uploadImage(@RequestParam("imageFile") MultipartFile imageFile) {
//        // 서비스에 이미지 처리를 요청
//        log.info("이건 당연히 되야지");
//
//        return imageService.processImage(imageFile);
//
//    }
//}

//
//
//
//
//package com.io.greenscan.controller;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.core.io.ClassPathResource;
//import org.springframework.http.HttpEntity;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpMethod;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.LinkedMultiValueMap;
//import org.springframework.util.MultiValueMap;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.client.RestTemplate;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.IOException;
//
//@RestController
//public class ImageController {
//
//    @Value("${flask.server.url}")
//    private String flaskServerUrl;
//
//    private final RestTemplate restTemplate = new RestTemplate();
//
//    @PostMapping("/api/send-image")
//    public ResponseEntity<String> sendImage(@RequestParam("image") MultipartFile image) throws IOException {
//        // Create headers
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
//
//        // Create body
//        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
//        body.add("image", new ClassPathResource(image.getOriginalFilename()));
//
//        // Create request entity
//        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
//
//        // Send request to Flask server
//        ResponseEntity<String> response = restTemplate.exchange(flaskServerUrl + "/detect", HttpMethod.POST, requestEntity, String.class);
//
//        return ResponseEntity.ok(response.getBody());
//    }
//}

//package com.io.greenscan.controller;
//
//import com.io.greenscan.service.ImageService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.multipart.MultipartFile;
//
//@Slf4j
//@RestController
//@RequestMapping("/api/user")
//public class ImageController {
//
//    private final ImageService imageService;
//
//    @Autowired
//    public ImageController(ImageService imageService) {
//        this.imageService = imageService;
//        log.info("이건 되야지");
//
//    }
//
//    @PostMapping("/uploadImage")
//    public String uploadImage(@RequestParam("imageFile") MultipartFile imageFile) {
//        // 서비스에 이미지 처리를 요청
//        log.info("이건 당연히 되야지");
//
//        return imageService.processImage(imageFile);
//
//    }
//}


//원본 이미지만 업로드 된 거
//package com.io.greenscan.controller;
//
//import com.io.greenscan.service.ImageService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//@Slf4j
//@RestController
//@RequestMapping("/api/user")
//public class ImageController {
//
//    private final ImageService imageService;
//
//    @Autowired
//    public ImageController(ImageService imageService) {
//        this.imageService = imageService;
//        log.info("이건 되야지");
//    }
//
//    @PostMapping("/uploadImage")
//    public ResponseEntity<String> uploadImage(@RequestParam("imageFile") MultipartFile imageFile) {
//        String result = imageService.processImage(imageFile);
//        return ResponseEntity.ok(result);
//    }
//
//    @PostMapping("/uploadProcessedImage")
//    public ResponseEntity<String> uploadProcessedImage(@RequestParam("imageFile") MultipartFile imageFile) {
//        try {
//            String uploadDir = "processedImages/";
//            File dir = new File(uploadDir);
//            if (!dir.exists()) {
//                dir.mkdirs();
//            }
//            Path filepath = Paths.get(uploadDir, imageFile.getOriginalFilename());
//            Files.write(filepath, imageFile.getBytes());
//
//            return ResponseEntity.ok("{\"filename\":\"" + imageFile.getOriginalFilename() + "\"}");
//        } catch (IOException e) {
//            return ResponseEntity.status(500).body("Error saving image");
//        }
//    }
//
//    @GetMapping("/getProcessedImage")
//    public ResponseEntity<InputStreamResource> getProcessedImage(@RequestParam("filename") String filename) throws IOException {
//        String uploadDir = "processedImages/";
//        Path filepath = Paths.get(uploadDir, filename);
//        File file = new File(filepath.toString());
//        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
//
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", file.getName()));
//
//        return ResponseEntity.ok()
//                .headers(headers)
//                .contentLength(file.length())
//                .contentType(MediaType.IMAGE_JPEG)
//                .body(resource);
//    }
//}


//package com.io.greenscan.controller;
//
//import com.io.greenscan.service.ImageService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.ByteArrayInputStream;
//import java.io.IOException;
//
//@Slf4j
//@RestController
//@RequestMapping("/api/user")
//public class ImageController {
//
//    private final ImageService imageService;
//
//    @Autowired
//    public ImageController(ImageService imageService) {
//        this.imageService = imageService;
//        log.info("이건 되야지");
//    }
//
//    @PostMapping("/uploadImage")
//    public ResponseEntity<InputStreamResource> uploadImage(@RequestParam("imageFile") MultipartFile imageFile) {
//        byte[] result = imageService.processImage(imageFile);
//        if (result != null) {
//            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(result);
//            InputStreamResource inputStreamResource = new InputStreamResource(byteArrayInputStream);
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Content-Disposition", "attachment; filename=processed_image.jpg");
//
//            return ResponseEntity.ok()
//                    .headers(headers)
//                    .contentType(MediaType.IMAGE_JPEG)
//                    .body(inputStreamResource);
//        } else {
//            return ResponseEntity.status(500).body(null);
//        }
//    }
//}














package com.io.greenscan.controller;

import com.io.greenscan.service.ImageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class ImageController {

    private final ImageService imageService;

    @Autowired
    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<String> uploadImage(@RequestParam("imageFile") MultipartFile imageFile) {
        String resultJson = imageService.processImage(imageFile);
        return ResponseEntity.ok(resultJson);
    }
}
