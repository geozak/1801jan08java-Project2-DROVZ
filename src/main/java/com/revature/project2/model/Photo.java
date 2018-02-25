package com.revature.project2.model;

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
@Table(name = "photo")
public class Photo {

	@Id
	@Column(name = "photo_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@Column(name = "photo_url", nullable = false, unique=true)
	private String url;

	@Column(name = "photo_added", nullable = false)
	private Timestamp added;

	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "trainer_id", nullable = false)
	private Trainer creator;

	public Photo() {
		super();
	}
	
	

	public Photo(String url, Trainer creator) {
		super();
		this.url = url;
		this.added = new Timestamp(System.currentTimeMillis());
		this.creator = creator;
	}



	public Photo(String url, Timestamp added, Trainer creator) {
		super();
		this.url = url;
		this.added = added;
		this.creator = creator;
	}

	public Photo(int id, String url, Timestamp added, Trainer creator) {
		super();
		this.id = id;
		this.url = url;
		this.added = added;
		this.creator = creator;
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

	@Override
	public String toString() {
		return "Photo [id=" + id + ", url=" + url + ", added=" + added + ", creator=" + creator + "]";
	}

	public String toStringTwo() {
		return "Photo [id=" + id + ", url=" + url + ", added=" + added + "]";
	}

}
