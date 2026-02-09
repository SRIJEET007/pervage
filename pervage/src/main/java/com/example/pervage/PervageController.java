package com.example.pervage;

import com.example.pervage.service.PervageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pervage")
public class PervageController {

    @Autowired
    private PervageService pervageService;

    @GetMapping("/images")
    public ResponseEntity<List<ImageMetaData>> getAllPhotos() {
        return ResponseEntity.ok(pervageService.getAllImages());
    }

    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> getPhoto(@PathVariable long id) {
        try {
            byte[] imageData = pervageService.getImageData(id);
            if (imageData != null) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_JPEG) 
                        .body(imageData);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/upload")
    public ResponseEntity<ImageMetaData> uploadPhoto(@RequestParam("file") MultipartFile file) {
        try {
            ImageMetaData metaData = pervageService.uploadImage(file);
            return ResponseEntity.status(HttpStatus.CREATED).body(metaData);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deletePhoto(@PathVariable long id) {
        pervageService.deleteImage(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/images/date")
    public ResponseEntity<List<ImageMetaData>> getPhotosByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(pervageService.getImagesByDate(date));
    }
}
