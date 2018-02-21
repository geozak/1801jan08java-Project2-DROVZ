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
import javax.persistence.Table;

@Entity
@Table(name="POSTS_PHOTOS")
public class PostsPhotos {

	@Id
	@Column(name="post_photo_id", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int post_photo_id;
	
	@Column(name="post_photo_url", nullable=false)
	private String post_photo_url;
	
	@Column(name="post_photo_added", nullable=false)
	private Timestamp post_photo_added;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="posts_id", nullable=false)
	private Posts post;
	
	public PostsPhotos() {
		// TODO Auto-generated constructor stub
	}
	
	public PostsPhotos(String post_photo_url, Timestamp post_photo_added, Posts post) {
		super();
		this.post_photo_url = post_photo_url;
		this.post_photo_added = post_photo_added;
		this.post = post;
	}

	public PostsPhotos(int post_photo_id, String post_photo_url, Timestamp post_photo_added, Posts post) {
		super();
		this.post_photo_id = post_photo_id;
		this.post_photo_url = post_photo_url;
		this.post_photo_added = post_photo_added;
		this.post = post;
	}

	public int getPost_photo_id() {
		return post_photo_id;
	}

	public void setPost_photo_id(int post_photo_id) {
		this.post_photo_id = post_photo_id;
	}

	public String getPost_photo_url() {
		return post_photo_url;
	}

	public void setPost_photo_url(String post_photo_url) {
		this.post_photo_url = post_photo_url;
	}

	public Timestamp getPost_photo_added() {
		return post_photo_added;
	}

	public void setPost_photo_added(Timestamp post_photo_added) {
		this.post_photo_added = post_photo_added;
	}

	public Posts getPost() {
		return post;
	}

	public void setPost(Posts post) {
		this.post = post;
	}

	@Override
	public String toString() {
		return "PostsPhotos [post_photo_id=" + post_photo_id + ", post_photo_url=" + post_photo_url
				+ ", post_photo_added=" + post_photo_added + ", post=" + post + "]";
	}
	
	
}
