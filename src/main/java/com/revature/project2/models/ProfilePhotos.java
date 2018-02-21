package com.revature.project2.models;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="PROFILE_PHOTOS")
public class ProfilePhotos {
	
	@Id
	@Column(name="profile_photo_id", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int profile_photo_id;
	
	@Column(name="profile_photo_url", nullable=false)
	private String profile_photo_url;
	
	@Column(name="profile_photo_added", nullable=false)
	private Timestamp profile_photo_added;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="trainer_id", nullable=false)
	//@Column(name="trainer_id")
	private Trainer trainer;
	
	public ProfilePhotos() {
		// TODO Auto-generated constructor stub
	}
	
	public ProfilePhotos(String profile_photo_url, Timestamp profile_photo_added,
			Trainer trainer) {
		super();
		this.profile_photo_url = profile_photo_url;
		this.profile_photo_added = profile_photo_added;
		this.trainer = trainer;
	}

	public ProfilePhotos(int profile_photo_id, String profile_photo_url, Timestamp profile_photo_added,
			Trainer trainer) {
		super();
		this.profile_photo_id = profile_photo_id;
		this.profile_photo_url = profile_photo_url;
		this.profile_photo_added = profile_photo_added;
		this.trainer = trainer;
	}

	public int getProfile_photo_id() {
		return profile_photo_id;
	}

	public void setProfile_photo_id(int profile_photo_id) {
		this.profile_photo_id = profile_photo_id;
	}

	public String getProfile_photo_url() {
		return profile_photo_url;
	}

	public void setProfile_photo_url(String profile_photo_url) {
		this.profile_photo_url = profile_photo_url;
	}

	public Timestamp getProfile_photo_added() {
		return profile_photo_added;
	}

	public void setProfile_photo_added(Timestamp profile_photo_added) {
		this.profile_photo_added = profile_photo_added;
	}

	public Trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}

	@Override
	public String toString() {
		return "ProfilePhotos [profile_photo_id=" + profile_photo_id + ", profile_photo_url=" + profile_photo_url
				+ ", profile_photo_added=" + profile_photo_added + ", trainer=" + trainer + "]";
	}
	
	
}
