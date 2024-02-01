package crm.demo.services;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileUpload {
    List<String> uploadFile(List<MultipartFile> multipartFile) throws IOException;
}
