package com.revature.project2.JSON;

import com.revature.project2.model.Trainer;

public class TrainerJSON {

	public final int id;
	public final String url;
	public final String firstName;
	public final String lastName;
	public final String email;
	public final String profilePictureUrl;

	public TrainerJSON(Trainer trainer) {
		this(trainer, false);
	}

	public TrainerJSON(Trainer trainer, boolean withEmail) {
		super();
		this.id = trainer.getId();
		this.url = trainer.getUrl();
		this.firstName = trainer.getFirstName();
		this.lastName = trainer.getLastName();
		this.email = withEmail ? trainer.getEmail() : "";
		this.profilePictureUrl = trainer.getProfilePicture() != null ? trainer.getProfilePicture().getUrl() : null;
	}

}
