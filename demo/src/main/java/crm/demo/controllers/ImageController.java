package crm.demo.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import crm.demo.services.ImageService;

@RestController
@RequestMapping(path = "/api/image")
public class ImageController {
    @Autowired
    private ImageService ImageService;

    @PostMapping
    public ResponseEntity<?> uploadImage(@RequestParam("image") List<MultipartFile> files) throws IOException {
        Map<String, Object> response = new HashMap<>();
        try {
            List<String> uploadImage = ImageService.uploadImages(files);
            response.put("status", HttpStatus.OK.value());
            response.put("message", uploadImage);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("status", HttpStatus.BAD_REQUEST.value());
            response.put("message", "image already exists");
            return ResponseEntity.badRequest().body(response);
        }

    }

    @GetMapping("/{fileName}")
    public ResponseEntity<?> downloadImage(@PathVariable String fileName) {
        byte[] imageData = ImageService.downloadImage(fileName);
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.valueOf("image/png"))
                .body(imageData);
    }

}
