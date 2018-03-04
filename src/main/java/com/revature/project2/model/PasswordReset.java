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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "reset")
public class PasswordReset {

	@Id
	@Column(name = "reet_id", unique = true, nullable = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "reset_trainer_id", nullable = false, unique = true)
	private Trainer trainer;

	@Column(name = "reset_token", nullable = false)
	private String token;

	@CreationTimestamp
	@Column(name = "reset_created", nullable = false)
	private Timestamp created;

	@Column(name = "reset_attempts", nullable = false)
	private int attempts;

	public PasswordReset() {
	}

	public PasswordReset(Trainer trainer, String token) {
		super();
		this.trainer = trainer;
		this.token = token;
		this.created = new Timestamp(System.currentTimeMillis());
		this.attempts = 0;
	}

	public PasswordReset(int id, Trainer trainer, String token, Timestamp created, int attempts) {
		super();
		this.id = id;
		this.trainer = trainer;
		this.token = token;
		this.created = created;
		this.attempts = attempts;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Trainer getTrainer() {
		return trainer;
	}

	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Timestamp getCreated() {
		return created;
	}

	public void setCreated(Timestamp created) {
		this.created = created;
	}

	public int getAttempts() {
		return attempts;
	}

	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}

	@Override
	public String toString() {
		return "PasswordReset [trainer=" + trainer + ", token=" + token + ", created=" + created + ", attempts="
				+ attempts + "]";
	}

}
