package com.example.pervage.service;

/*
    KAZU 2026
 */

import com.example.pervage.ImageMetaData;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;

public interface PervageService {
    ImageMetaData uploadImage(MultipartFile file) throws IOException;

    List<ImageMetaData> getAllImages();

    ImageMetaData getImageById(long id);

    void deleteImage(long id);

    List<ImageMetaData> getImagesByDate(java.time.LocalDate date);

    byte[] getImageData(long id) throws IOException;
}
