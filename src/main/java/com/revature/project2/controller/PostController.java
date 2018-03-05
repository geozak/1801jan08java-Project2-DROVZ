package com.revature.project2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.config.RepositoryNameSpaceHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.revature.project2.JSON.MessageJSON;
import com.revature.project2.JSON.PostJSON;
import com.revature.project2.JSON.TrainerJSON;
import com.revature.project2.model.Photo;
import com.revature.project2.model.Post;
import com.revature.project2.model.Trainer;
import com.revature.project2.service.PhotoStorageService.PhotoStorageResponse;
import com.revature.project2.service.PostService;
import com.revature.project2.service.S3PhotoStorageServiceImpl;
import com.revature.project2.service.TrainerService;
import com.revature.project2.session.SessionVariables;

@RestController("postController")
@CrossOrigin(origins = "*")
public class PostController {

	@Autowired
	private PostService postService;

	@Autowired
	private TrainerService trainerService;

	@Autowired
	private S3PhotoStorageServiceImpl storageService;

	@Autowired
	SessionVariables sessionVariables;

	@PostMapping("/createPost")
	public @ResponseBody ResponseEntity<MessageJSON> createPost(
			// @RequestParam("author") String author,
			@RequestParam("post") String post) {

		Trainer trainer = sessionVariables.getTrainer();
		if (trainer == null) {
			System.out.println("FAIL to create post not logged in");
			return new ResponseEntity<MessageJSON>(HttpStatus.UNAUTHORIZED);
		}

		// System.out.println("Post from user id: " + author);
		System.out.println("Post: " + post);
		// Trainer t=trainerService.getTrainerById(Integer.parseInt(author));
		Post newP = new Post(post, trainer);
		if (postService.savePost(newP) == true) {
			System.out.println("Success");
			return new ResponseEntity<MessageJSON>(new MessageJSON("Success"), HttpStatus.OK);
		} else {
			System.out.println("Failure");
			return new ResponseEntity<MessageJSON>(new MessageJSON("Failure"), HttpStatus.OK);
		}
	}

	@GetMapping("/getPosts")
	public @ResponseBody ResponseEntity<List<PostJSON>> getPosts() {

		Trainer trainer = sessionVariables.getTrainer();
		if (trainer == null) {
			System.out.println("FAIL to get posts not logged in");
			return new ResponseEntity<List<PostJSON>>(HttpStatus.UNAUTHORIZED);
		}

		List<Post> lPost = postService.getPostsByID(trainer.getId());
		List<PostJSON> output = new ArrayList<>();
		lPost.forEach(post -> output.add(new PostJSON(post)));
		return new ResponseEntity<>(output, HttpStatus.OK);

		// return new ResponseEntity<MessageJSON>(new MessageJSON("Success"),
		// HttpStatus.OK);

	}

	@PostMapping("/getPostByUrl")
	public @ResponseBody ResponseEntity<List<PostJSON>> getPostByUrl(@RequestParam("url") String url) {
		if (sessionVariables.getTrainer() == null) {
			System.out.println("FAIL to get posts not logged in");
			return new ResponseEntity<List<PostJSON>>(HttpStatus.UNAUTHORIZED);
		}

		System.out.println("URL: " + url);
		Trainer t = trainerService.getTrainerByUrl(url);
		if (t == null) {
			List<PostJSON> output = new ArrayList<>();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			List<Post> lPost = postService.getPostsByID(t.getId());
			List<PostJSON> output = new ArrayList<>();
			lPost.forEach(post -> output.add(new PostJSON(post)));
			return new ResponseEntity<>(output, HttpStatus.OK);
		}

	}

	@GetMapping("/getAllPosts")
	public @ResponseBody ResponseEntity<List<PostJSON>> getAllPosts() {
		Trainer trainer = sessionVariables.getTrainer();
		if (trainer == null) {
			System.out.println("FAIL to get posts not logged in");
			return new ResponseEntity<List<PostJSON>>(HttpStatus.UNAUTHORIZED);
		}

		List<Post> posts = postService.getPosts();
		List<PostJSON> output = new ArrayList<>();
		posts.forEach(post -> output.add(new PostJSON(post)));
		return new ResponseEntity<>(output, HttpStatus.OK);
	}

	@PostMapping("/like")
	public @ResponseBody ResponseEntity<MessageJSON> likePost(@RequestParam("postID") int postId) {
		Trainer trainer = sessionVariables.getTrainer();
		if (trainer == null)
			return new ResponseEntity<MessageJSON>(new MessageJSON("login"), HttpStatus.UNAUTHORIZED);
		
		Post post = postService.getPostById(postId);
		if (post == null)
			return new ResponseEntity<MessageJSON>(new MessageJSON("post"), HttpStatus.OK);
		
		boolean completed = postService.likePost(post, trainer);
		if (completed == false)
			return new ResponseEntity<MessageJSON>(new MessageJSON("failure"), HttpStatus.OK);
		
		return new ResponseEntity<MessageJSON>(new MessageJSON("success"), HttpStatus.OK);
	}
	
	@PostMapping("/unlike")
	public @ResponseBody ResponseEntity<MessageJSON> unlikePost(@RequestParam("postID") int postId) {
		Trainer trainer = sessionVariables.getTrainer();
		if (trainer == null)
			return new ResponseEntity<MessageJSON>(new MessageJSON("login"), HttpStatus.UNAUTHORIZED);
		
		Post post = postService.getPostById(postId);
		if (post == null)
			return new ResponseEntity<MessageJSON>(new MessageJSON("post"), HttpStatus.OK);
		
		boolean completed = postService.unlikePost(post, trainer);
		if (completed == false)
			return new ResponseEntity<MessageJSON>(new MessageJSON("failure"), HttpStatus.OK);
		
		return new ResponseEntity<MessageJSON>(new MessageJSON("success"), HttpStatus.OK);
	}
	@PostMapping("/updatePost")
	public @ResponseBody ResponseEntity<MessageJSON> updatePost(
			@RequestParam("file") MultipartFile file,
			@RequestParam("id") int id) {

		Trainer trainer = sessionVariables.getTrainer();
		if (trainer == null) {
			System.out.println("FAIL to upload not logged in");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageJSON("FAIL to upload not logged in"));
		}
		
		Post post = postService.getPostById(id);
		if(post.getCreator() != sessionVariables.getTrainer())
			return ResponseEntity.ok(new MessageJSON("You don't have control of this post"));

		PhotoStorageResponse photoStorageResponse = storageService.storePhoto(file, trainer);
		System.out.println(photoStorageResponse.isSuccess());
		if (photoStorageResponse.isSuccess()) {
//			List<Photo> photos = new ArrayList<Photo>();
			List<Photo> photos = post.getPostPhotos();
			if (photos == null) {
				photos = new ArrayList<Photo>();
				post.setPostPhotos(photos);
			}
			photos.add(photoStorageResponse.getPhoto());
			
//			post.setPostPhotos(photos);
			
			postService.savePost(post);
			return ResponseEntity.ok(new MessageJSON(photoStorageResponse.getMessage()));
		}
		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
				.body(new MessageJSON(photoStorageResponse.getMessage()));
	}
		
//		@PostMapping("/updatePost")
//		public @ResponseBody ResponseEntity<String> updatePost(
//				@RequestParam("file") MultipartFile file,
//				@RequestParam("id") int id) {
//
//			Trainer trainer = sessionVariables.getTrainer();
//			if (trainer == null) {
//				System.out.println("FAIL to upload not logged in");
//				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("FAIL to upload not logged in");
//			}
//
//			PhotoStorageResponse photoStorageResponse = storageService.storePhoto(file, trainer);
//			System.out.println(photoStorageResponse.isSuccess());
//			if (photoStorageResponse.isSuccess()) {
//				List<Photo> photos = new ArrayList<Photo>();
//				photos.add(photoStorageResponse.getPhoto());
//				Post post = postService.getPostByID(id);
//				
//				if(post.getCreator() != sessionVariables.getTrainer())
//					return ResponseEntity.ok("You don't have control of this post");
//				
//				post.setPostPhotos(photos);
//				
//				postService.savePost(post);
//				return ResponseEntity.ok(photoStorageResponse.getMessage());
//			}
//
//		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED)
//				.body(photoStorageResponse.getMessage());
//
//	}
}
