package com.revature.project2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project2.model.Post;
import com.revature.project2.repository.PostRepository;

@Service("postService")
public class PostServiceImpl implements PostService{

	@Autowired
	private PostRepository postRepository;
	
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
		List<Post> lPost =(List<Post>) postRepository.findAll();
		return lPost;
	}
	
	@Override
	public List<Post> getPostsByID(int id) {
		// TODO Auto-generated method stub
		List<Post> lPost =(List<Post>) postRepository.findByCreatorIdOrderByAddedDesc(id);
		return lPost;
	}

}
