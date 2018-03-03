package com.revature.project2.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "post")
public class Post {

	@Id
	@Column(name = "post_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "post_text", nullable = false)
	private String text;

	@CreationTimestamp
	@Column(name = "post_added", nullable = false)
	private Timestamp added;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "trainer_id", nullable = false)
	private Trainer creator;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "post_photo",
		joinColumns = { @JoinColumn(name = "post_id") }, 
		inverseJoinColumns = { @JoinColumn(name = "photo_id") })
	private List<Photo> postPhotos;

	@ManyToMany(mappedBy = "likedPosts", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Trainer> likedBy;

	public Post() {
	}

	public Post(String text, Trainer creator) {
		super();
		this.text = text;
//		this.added = new Timestamp(System.currentTimeMillis());
		this.creator = creator;
	}
	
	

	public Post(String text, Trainer creator, List<Photo> postPhotos) {
		super();
		this.text = text;
		this.creator = creator;
		this.added = new Timestamp(System.currentTimeMillis());
		this.postPhotos = postPhotos;
	}

	public Post(String text, Timestamp added, Trainer creator) {
		super();
		this.text = text;
		this.added = added;
		this.creator = creator;
	}

	public Post(int id, String text, Timestamp added, Trainer creator) {
		super();
		this.id = id;
		this.text = text;
		this.added = added;
		this.creator = creator;
	}

	public Post(int id, String text, Timestamp added, Trainer creator, List<Photo> postPhotos) {
		super();
		this.id = id;
		this.text = text;
		this.added = added;
		this.creator = creator;
		this.postPhotos = postPhotos;
	}

	public Post(int id, String text, Timestamp added, Trainer creator, List<Photo> postPhotos, Set<Trainer> likedBy) {
		super();
		this.id = id;
		this.text = text;
		this.added = added;
		this.creator = creator;
		this.postPhotos = postPhotos;
		this.likedBy = likedBy;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Timestamp getAdded() {
		return added;
	}

	public void setAdded(Timestamp added) {
		this.added = added;
	}

	public Trainer getCreator() {
		return creator;
	}

	public void setCreator(Trainer creator) {
		this.creator = creator;
	}

	public List<Photo> getPostPhotos() {
		return postPhotos;
	}

	public void setPostPhotos(List<Photo> postPhotos) {
		this.postPhotos = postPhotos;
	}

	public Set<Trainer> getLikedBy() {
		return likedBy;
	}

	public void setLikedBy(Set<Trainer> likedBy) {
		this.likedBy = likedBy;
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", text=" + text + ", added=" + added + ", creator=" + creator + ", postPhotos="
				+ postPhotos + ", likedBy=" + likedBy + "]";
	}

}
