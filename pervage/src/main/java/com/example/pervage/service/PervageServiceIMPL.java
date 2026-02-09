package com.example.pervage.service;

import com.example.pervage.ImageMetaData;
import com.example.pervage.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PervageServiceIMPL implements PervageService {

    @Autowired
    private ImageRepository imageRepository;

    @Value("${pervage.file.upload-dir}")
    private String uploadDir;

    @Override
    public ImageMetaData uploadImage(MultipartFile file) throws IOException {
        // Create directory if it doesn't exist
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(uploadDir, fileName);

        //Save 
        Files.write(filePath, file.getBytes());

        //METADATA
        ImageMetaData metaData = new ImageMetaData();
        metaData.setFileName(file.getOriginalFilename());
        metaData.setPath(filePath.toString());
        metaData.setFileSize(String.valueOf(file.getSize()));
        metaData.setUploadedAt(LocalDateTime.now());

        return imageRepository.save(metaData);
    }

    @Override
    public List<ImageMetaData> getAllImages() {
        return imageRepository.findAllByOrderByUploadedAtDesc();
    }

    @Override
    public ImageMetaData getImageById(long id) {
        return imageRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteImage(long id) {
        Optional<ImageMetaData> metaData = imageRepository.findById(id);
        if (metaData.isPresent()) {
           
            File file = new File(metaData.get().getPath());
            if (file.exists()) {
                file.delete();
            }
          
            imageRepository.deleteById(id);
        }
    }

    @Override
    public List<ImageMetaData> getImagesByDate(java.time.LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();
        return imageRepository.findByUploadedAtBetween(startOfDay, endOfDay);
    }

    @Override
    public byte[] getImageData(long id) throws IOException {
        ImageMetaData metaData = getImageById(id);
        if (metaData != null) {
            Path path = Paths.get(metaData.getPath());
            return Files.readAllBytes(path);
        }
        return null;
    }
}
