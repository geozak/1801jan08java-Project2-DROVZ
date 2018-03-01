package com.revature.project2.service;

import java.io.File;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.revature.project2.model.Photo;
import com.revature.project2.service.S3PhotoStorageServiceImpl.PhotoStorageResponse;

public interface PhotoStorageService {

	void deleteFile(String url);

	PhotoStorageResponse storePhoto(MultipartFile file, Photo photo);
}
