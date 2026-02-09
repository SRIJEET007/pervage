package com.example.pervage.repository;

import com.example.pervage.ImageMetaData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ImageRepository extends JpaRepository<ImageMetaData, Long> {
    List<ImageMetaData> findAllByOrderByUploadedAtDesc();
    List<ImageMetaData> findByUploadedAtBetween(java.time.LocalDateTime start, java.time.LocalDateTime end);
}
