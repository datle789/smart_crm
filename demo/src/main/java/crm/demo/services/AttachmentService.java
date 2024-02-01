package crm.demo.services;

import org.springframework.web.multipart.MultipartFile;

import crm.demo.models.Attachment;

public interface AttachmentService {
    Attachment saveAttachment(MultipartFile file) throws Exception;

    Attachment getAttachment(String fileId) throws Exception;
}
