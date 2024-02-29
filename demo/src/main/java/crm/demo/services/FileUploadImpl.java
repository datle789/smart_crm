package crm.demo.services;

import com.cloudinary.Cloudinary;

import crm.demo.controllers.CrmController;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class FileUploadImpl implements FileUpload {

    private final Cloudinary cloudinary;

    private static final Logger logger = LoggerFactory.getLogger(CrmController.class);

    @Value("${max.file.size}") // Cấu hình dung lượng file tối đa từ application.properties
    private long maxFileSize;

    @Override
    public List<String> uploadFile(List<MultipartFile> multipartFiles) throws IOException {
        List<String> successMessages = new ArrayList<>();
        for (MultipartFile file : multipartFiles) {
            if (file.getSize() > maxFileSize) {
                // File size exceeds limit, handle accordingly
                successMessages.add("File size exceeds the limit: " +
                        file.getOriginalFilename());
            } else {
                successMessages.add(cloudinary.uploader()
                        .upload(file.getBytes(),
                                Map.of("public_id", UUID.randomUUID().toString()))
                        .get("url")
                        .toString());
            }
        }
        return successMessages;
    }
}
