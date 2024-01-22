package crm.demo.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import crm.demo.models.Image;
import crm.demo.repo.ImageRepo;
import crm.demo.utils.ImageUtil;

@Service
public class ImageService {
    @Autowired
    private ImageRepo imageRepo;

    public List<String> uploadImages(List<MultipartFile> files) throws IOException {
        List<String> successMessages = new ArrayList<>();

        for (MultipartFile file : files) {
            Image imageData = imageRepo.save(Image.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imageData(ImageUtil.compressImage(file.getBytes())).build());

            if (imageData != null) {
                successMessages.add("File uploaded successfully: " + file.getOriginalFilename());
            }
        }

        return successMessages;
    }

    public byte[] downloadImage(String fileName) {
        Optional<Image> dbImageData = imageRepo.findByName(fileName);
        byte[] images = ImageUtil.decompressImage(dbImageData.get().getImageData());
        return images;
    }
}
