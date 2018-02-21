package com.revature.project2.models;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="POSTS")
public class Posts {
	
	@Id
	@Column(name="post_id", unique=true, nullable=false)
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int post_id;
	
	@Column(name="post_desc", nullable=false)
	private String post_desc;
	
	@Column(name="post_added", nullable=false)
	private Timestamp post_added;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name="trainer_id", nullable=false)
	private Trainer trainer;
	
	public Posts() {
		// TODO Auto-generated constructor stub
	}
	
	public Posts(String post_desc, Timestamp post_added, Trainer trainer) {
		super();
		this.post_desc = post_desc;
		this.post_added = post_added;
		this.trainer = trainer;
	}

	public Posts(int post_id, String post_desc, Timestamp post_added, Trainer trainer) {
		super();
		this.post_id = post_id;
		this.post_desc = post_desc;
		this.post_added = post_added;
		this.trainer = trainer;
	}

	public int getPost_id() {
		return post_id;
	}

	public void setPost_id(int post_id) {
		this.post_id = post_id;
	}

	public String getPost_desc() {
		return post_desc;
	}

	public void setPost_desc(String post_desc) {
		this.post_desc = post_desc;
	}

	public Timestamp getPost_added() {
		return post_added;
	}

	public void setPost_added(Timestamp post_added) {
		this.post_added = post_added;
	}

	public Trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}

	@Override
	public String toString() {
		return "Posts [post_id=" + post_id + ", post_desc=" + post_desc + ", post_added=" + post_added + ", trainer="
				+ trainer + "]";
	}
	
	
}
