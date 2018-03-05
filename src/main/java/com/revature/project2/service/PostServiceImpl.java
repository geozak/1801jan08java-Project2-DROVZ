package com.revature.project2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project2.model.Post;
import com.revature.project2.model.Trainer;
import com.revature.project2.repository.PostRepository;
import com.revature.project2.repository.TrainerRepository;

@Service("postService")
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private TrainerRepository trainerRepository;
	
	@Override
	public boolean savePost(Post p) {
		// TODO Auto-generated method stub
		
		Post newP=postRepository.save(p);
		
		if(newP==null)
			return false;
		else
			return true;
		
	}

	@Override
	public List<Post> getPosts() {
		// TODO Auto-generated method stub
		List<Post> lPost =(List<Post>) postRepository.findAllByOrderByAddedDesc();
		return lPost;
	}
	
	@Override
	public List<Post> getPostsByID(int id) {
		// TODO Auto-generated method stub
		List<Post> lPost =(List<Post>) postRepository.findByCreatorIdOrderByAddedDesc(id);
		return lPost;
	}
	
	@Override
	public Post getPostById(int id) {
		return postRepository.findOne(id);
	}

	@Override
	public boolean likePost(Post post, Trainer trainer) {
		List<Post> likedPosts = trainer.getLikedPosts();
		likedPosts.add(post);
		Trainer obj = trainerRepository.save(trainer);
		if (obj == null)
			return false;
		return true;
	}

	@Override
	public boolean unlikePost(Post post, Trainer trainer) {
		List<Post> likedPosts = trainer.getLikedPosts();
		boolean removed = likedPosts.remove(post);
		if (removed == false)
			System.out.println("didn't remove post");
		trainer.setLikedPosts(likedPosts);
		trainerRepository.save(trainer);
		return true;
	}
}
