package com.revature.project2.service;


import org.springframework.web.multipart.MultipartFile;

import com.revature.project2.model.Photo;
import com.revature.project2.model.Trainer;

public interface PhotoStorageService {

	public class PhotoStorageResponse {
		private final boolean success;
		private final String message;
		private final Photo photo;

		public PhotoStorageResponse(boolean success, String message, Photo photo) {
			this.success = success;
			this.message = message;
			this.photo = photo;
		}

		public boolean isSuccess() {
			return success;
		}

		public String getMessage() {
			return message;
		}

		public Photo getPhoto() {
			return photo;
		}

	}

//	void deleteFile(String url);

	PhotoStorageResponse storePhoto(MultipartFile file, Trainer creator);

}
