package crm.demo.controllers;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.ui.Model;
import crm.demo.services.FileUpload;
import crm.demo.utils.ErrorUtil;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = { "http://localhost:3000" })
@RequestMapping("/api/upload")
public class FileUploadController {

    private final FileUpload fileUpload;
    private static final Logger logger = LoggerFactory.getLogger(CrmController.class);
    ErrorUtil errorUtil = new ErrorUtil();

    @Value("${spring.servlet.multipart.max-request-size}")
    private String maxRequestSize;

    @PostMapping("/")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN') or hasRole('MODERATOR')")
    public ResponseEntity<Map<String, Object>> uploadFile(@RequestParam("image") List<MultipartFile> multipartFile)
            throws IOException {
        Map<String, Object> response = new HashMap<>();
        // long maxRequestSizeBytes = parseMaxRequestSize(maxRequestSize);
        // long sumOfFile = 0L;
        // for (MultipartFile file : multipartFile) {
        // long size = file.getSize();
        // sumOfFile += size;
        // }

        // if (sumOfFile > maxRequestSizeBytes) {
        // errorUtil.badStatus("kích thước file quá lớn");
        // }
        List<String> imageURL = fileUpload.uploadFile(multipartFile);
        response.put("status", HttpStatus.OK.value());
        response.put("imageURL", imageURL);
        return ResponseEntity.ok(response);
    }

    // chuyển đổi MB sang số
    private long parseMaxRequestSize(String maxRequestSize) {
        String[] parts = maxRequestSize.split("(?<=\\d)(?=\\D)");
        long size = Long.parseLong(parts[0].trim());
        String unit = parts[1].trim();
        if (unit.equalsIgnoreCase("KB")) {
            size *= 1024;
        } else if (unit.equalsIgnoreCase("MB")) {
            size *= 1024 * 1024;
        } else if (unit.equalsIgnoreCase("GB")) {
            size *= 1024 * 1024 * 1024;
        }
        return size;
    }
}
