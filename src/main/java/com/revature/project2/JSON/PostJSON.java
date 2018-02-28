package com.revature.project2.JSON;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.revature.project2.model.Post;

public class PostJSON {

	public final int id;
	public final String text;
	public final long added;
	public final TrainerJSON creator;
	public final List<PhotoJSON> postPhotos;
	public final Set<TrainerJSON> likedBy;

	public PostJSON(Post post) {
		super();
		this.id = post.getId();
		this.text = post.getText();
		this.added = post.getAdded().getTime();
		this.creator = new TrainerJSON(post.getCreator());
		this.postPhotos = new ArrayList<PhotoJSON>();
		post.getPostPhotos().forEach(photo -> this.postPhotos.add(new PhotoJSON(photo)));
		this.likedBy = new HashSet<TrainerJSON>();
		post.getLikedBy().forEach(trainer -> this.likedBy.add(new TrainerJSON(trainer)));
	}

}
