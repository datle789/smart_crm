package crm.demo.services;

import com.cloudinary.Cloudinary;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FileUploadImpl implements FileUpload {

    private final Cloudinary cloudinary;

    @Override
    public List<String> uploadFile(List<MultipartFile> multipartFile) throws IOException {
        List<String> successMessages = new ArrayList<>();
        for (MultipartFile file : multipartFile) {
            // cloudinary.uploader()
            // .upload(file.getBytes(),
            // Map.of("public_id", UUID.randomUUID().toString()))
            // .get("url")
            // .toString();
            successMessages.add("File uploaded successfully: " + cloudinary.uploader()
                    .upload(file.getBytes(),
                            Map.of("public_id", UUID.randomUUID().toString()))
                    .get("url")
                    .toString());
        }
        return successMessages;
    }
}
