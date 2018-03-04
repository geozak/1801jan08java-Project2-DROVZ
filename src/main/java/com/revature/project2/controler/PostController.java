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

import com.revature.project2.JSON.MessageJSON;
import com.revature.project2.JSON.PostJSON;
import com.revature.project2.JSON.TrainerJSON;
import com.revature.project2.model.Post;
import com.revature.project2.model.Trainer;
import com.revature.project2.service.PostService;
import com.revature.project2.service.TrainerService;

@RestController("postController")
@CrossOrigin(origins = "*")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private TrainerService trainerService;
	
	@PostMapping("/createPost")
	public @ResponseBody ResponseEntity<MessageJSON> createPost(
			@RequestParam("author") String author, 
			@RequestParam("post") String post) {
		
		System.out.println("Post from user id: " + author);
		System.out.println("Post: " + post);
		Trainer t=trainerService.getTrainerById(Integer.parseInt(author));
		Post newP = new Post(post,  t);
		if(postService.savePost(newP)==true) {
			System.out.println("Success");
		}else {
			System.out.println("Failure");
		}
		
		return new ResponseEntity<MessageJSON>(new MessageJSON("Success"), HttpStatus.OK);
	}
	
	@GetMapping("getPosts")
	public @ResponseBody ResponseEntity<List<PostJSON>> getPosts(){
		
		List<Post> lPost=postService.getPosts();
		List<PostJSON> output = new ArrayList<>();
		lPost.forEach(post -> output.add(new PostJSON(post)));
		return new ResponseEntity<>(output, HttpStatus.OK);
		
		//return new ResponseEntity<MessageJSON>(new MessageJSON("Success"), HttpStatus.OK);
		
	}

}
