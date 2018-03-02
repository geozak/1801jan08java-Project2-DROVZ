package com.revature.project2.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "trainer")
@Embeddable
public class Trainer {

	@Id
	@Column(name = "trainer_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "trainer_url", unique = true, nullable = false)
	private String url;

	@Column(name = "trainer_first_name", nullable = false)
	private String firstName;

	@Column(name = "trainer_last_name", nullable = false)
	private String lastName;

	@Column(name = "trainer_salt", nullable = false)
	private String salt;

	@Column(name = "trainer_password", nullable = false)
	private String password;

	@Column(name = "trainer_email", unique = true, nullable = false)
	private String email;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name = "profile_photo_id", unique = true, nullable = true)
	private Photo profilePicture;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "liked_post", 
		joinColumns = { @JoinColumn(name = "trainer_id") }, 
		inverseJoinColumns = { @JoinColumn(name = "post_id") })
	private List<Post> likedPosts;

	public Trainer() {
		super();
	}

	public Trainer(String url, String firstName, String lastName, String salt, String password, String email) {
		super();
		this.url = url;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salt = salt;
		this.password = password;
		this.email = email;
	}

	public Trainer(int id, String url, String firstName, String lastName, String salt, String password, String email) {
		super();
		this.id = id;
		this.url = url;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salt = salt;
		this.password = password;
		this.email = email;
	}

	public Trainer(int id, String url, String firstName, String lastName, String salt, String password, String email,
			Photo profilePicture) {
		super();
		this.id = id;
		this.url = url;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salt = salt;
		this.password = password;
		this.email = email;
		this.profilePicture = profilePicture;
	}

	public Trainer(int id, String url, String firstName, String lastName, String salt, String password, String email,
			Photo profilePicture, List<Post> likedPosts) {
		super();
		this.id = id;
		this.url = url;
		this.firstName = firstName;
		this.lastName = lastName;
		this.salt = salt;
		this.password = password;
		this.email = email;
		this.profilePicture = profilePicture;
		this.likedPosts = likedPosts;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Photo getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(Photo profilePicture) {
		this.profilePicture = profilePicture;
	}

	public List<Post> getLikedPosts() {
		return likedPosts;
	}

	public void setLikedPosts(List<Post> likedPosts) {
		this.likedPosts = likedPosts;
	}

	@Override
	public String toString() {
		return "Trainer [id=" + id + ", url=" + url + ", firstName=" + firstName + ", lastName=" + lastName + ", salt="
				+ salt + ", password=" + password + ", email=" + email + ", profilePicture="
				+ profilePicture.toStringTwo() + "]";
	}
}
