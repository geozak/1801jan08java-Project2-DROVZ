package com.revature.project2.JSON;

import com.revature.project2.model.Photo;

public class PhotoJSON {

	public final int id;
	public final String url;
	public final long added;
	public final TrainerJSON creator;

	public PhotoJSON(Photo photo) {
		super();
		this.id = photo.getId();
		this.url = photo.getUrl();
		this.added = photo.getAdded().getTime();
		this.creator = new TrainerJSON(photo.getCreator());
	}

}
