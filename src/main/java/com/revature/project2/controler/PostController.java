package com.revature.project2.controler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.revature.project2.JSON.PostJSON;
import com.revature.project2.JSON.TrainerJSON;
import com.revature.project2.model.Photo;
import com.revature.project2.model.Post;
import com.revature.project2.model.Trainer;
import com.revature.project2.service.PhotoStorageService;
import com.revature.project2.service.TrainerService;
import com.revature.project2.service.PhotoStorageService.PhotoStorageResponse;
import com.revature.project2.service.PostService;
import com.revature.project2.session.SessionVariables;

import ch.qos.logback.core.net.SyslogOutputStream;

@RestController("postController")
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*")
public class PostController {
	
	@Autowired
	PostService postService;
	
	@Autowired
	PhotoStorageService storageService;
	
	@Autowired
	SessionVariables sessionVariables;
	
	@PostMapping("/addPost")
	public ResponseEntity<String> handleFileUpload(
			@RequestParam("file") MultipartFile file
			, @RequestParam("message") String message) {

		Trainer trainer = sessionVariables.getTrainer();
		if(trainer == null) {
			System.out.println("FAIL to upload not logged in");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("FAIL to upload not logged in");
		}
		
		PhotoStorageResponse photoStorageResponse = storageService.storePhoto(file, trainer);
		System.out.println(photoStorageResponse.isSuccess());
		if(photoStorageResponse.isSuccess()) {
			List<Photo> photos = new ArrayList<Photo>();
			photos.add(photoStorageResponse.getPhoto());
			Post post = new Post(message, trainer, photos);
			postService.savePost(post);
			return ResponseEntity.ok(photoStorageResponse.getMessage());
		}

		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(photoStorageResponse.getMessage());
	}
	
	@PostMapping("/getAllPosts")
	public ResponseEntity<List<PostJSON>> getAllPostsById(
			@RequestParam("id") int id) {
		return ResponseEntity.ok(postService.getAllById(id));
	}


}
