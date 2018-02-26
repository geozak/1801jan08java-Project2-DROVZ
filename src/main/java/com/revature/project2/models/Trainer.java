package com.revature.project2.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="TRAINERS")
public class Trainer {
	
	@Id
	@Column(name="trainer_id", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int trainer_id;
	
	@Column(name="trainer_url", unique=true, nullable=false)
	private String trainer_url;
	
	@Column(name="trainer_first_name", nullable=false)
	private String trainer_first_name;
	
	@Column(name="trainer_last_name", nullable=false)
	private String trainer_last_name;
	
	@Column(name="trainer_salt", nullable=false)
	private String trainer_salt;
	
	@Column(name="trainer_password", nullable=false)
	private String trainer_password;
	
	@Column(name="trainer_email", unique=true, nullable=false)
	private String trainer_email;
	
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="profile_photo_id")
	private ProfilePhotos profile_picture_id;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "LIKED_POSTS", joinColumns = { @JoinColumn(name = "trainer_id") }, inverseJoinColumns = { @JoinColumn(name = "posts_id") })
	private List<Posts> likedPosts;
	
	public Trainer() {
		// TODO Auto-generated constructor stub
	}
	
	public Trainer(String trainer_url, String trainer_first_name, String trainer_last_name,
			String trainer_salt, String trainer_password, String trainer_email, ProfilePhotos profile_picture_id,
			List<Posts> likedPosts) {
		super();
		this.trainer_url = trainer_url;
		this.trainer_first_name = trainer_first_name;
		this.trainer_last_name = trainer_last_name;
		this.trainer_salt = trainer_salt;
		this.trainer_password = trainer_password;
		this.trainer_email = trainer_email;
		this.profile_picture_id = profile_picture_id;
		this.likedPosts = likedPosts;
	}

	public Trainer(int trainer_id, String trainer_url, String trainer_first_name, String trainer_last_name,
			String trainer_salt, String trainer_password, String trainer_email, ProfilePhotos profile_picture_id,
			List<Posts> likedPosts) {
		super();
		this.trainer_id = trainer_id;
		this.trainer_url = trainer_url;
		this.trainer_first_name = trainer_first_name;
		this.trainer_last_name = trainer_last_name;
		this.trainer_salt = trainer_salt;
		this.trainer_password = trainer_password;
		this.trainer_email = trainer_email;
		this.profile_picture_id = profile_picture_id;
		this.likedPosts = likedPosts;
	}

	public int getTrainer_id() {
		return trainer_id;
	}

	public void setTrainer_id(int trainer_id) {
		this.trainer_id = trainer_id;
	}

	public String getTrainer_url() {
		return trainer_url;
	}

	public void setTrainer_url(String trainer_url) {
		this.trainer_url = trainer_url;
	}

	public String getTrainer_first_name() {
		return trainer_first_name;
	}

	public void setTrainer_first_name(String trainer_first_name) {
		this.trainer_first_name = trainer_first_name;
	}

	public String getTrainer_last_name() {
		return trainer_last_name;
	}

	public void setTrainer_last_name(String trainer_last_name) {
		this.trainer_last_name = trainer_last_name;
	}

	public String getTrainer_salt() {
		return trainer_salt;
	}

	public void setTrainer_salt(String trainer_salt) {
		this.trainer_salt = trainer_salt;
	}

	public String getTrainer_password() {
		return trainer_password;
	}

	public void setTrainer_password(String trainer_password) {
		this.trainer_password = trainer_password;
	}

	public String getTrainer_email() {
		return trainer_email;
	}

	public void setTrainer_email(String trainer_email) {
		this.trainer_email = trainer_email;
	}

	public ProfilePhotos getProfile_picture_id() {
		return profile_picture_id;
	}

	public void setProfile_picture_id(ProfilePhotos profile_picture_id) {
		this.profile_picture_id = profile_picture_id;
	}

	public List<Posts> getLikedPosts() {
		return likedPosts;
	}

	public void setLikedPosts(List<Posts> likedPosts) {
		this.likedPosts = likedPosts;
	}

	@Override
	public String toString() {
		return "Trainer [trainer_id=" + trainer_id + ", trainer_url=" + trainer_url + ", trainer_first_name="
				+ trainer_first_name + ", trainer_last_name=" + trainer_last_name + ", trainer_salt=" + trainer_salt
				+ ", trainer_password=" + trainer_password + ", trainer_email=" + trainer_email
				+ ", profile_picture_id=" + profile_picture_id + ", likedPosts=" + likedPosts + "]";
	}
	
	
}
