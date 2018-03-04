package com.revature.project2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project2.model.Trainer;
import com.revature.project2.model.validator.TrainerValidator;
import com.revature.project2.repository.PasswordResetRepository;
import com.revature.project2.repository.TrainerRepository;
import com.revature.project2.service.AuthService.RegisterReturn;
import com.revature.project2.service.AuthService.RegisterReturn.Status;
import com.revature.project2.util.PasswordHashing;

@Service("authService")
public class AuthServiceImpl implements AuthService {

	@Autowired
	private TrainerRepository trainerRepository;

	public AuthServiceImpl() {
	}

	@Override
	public Trainer login(String email, String password) {
		System.out.println("attempting to login");
		System.out.println("email: " + email);
		//System.out.println("password: " + password);
		
		Trainer trainer = trainerRepository.findByEmail(email);
		System.out.println("Found: " + trainer);
		if (
			trainer != null &&
			trainer.getPassword().equals(
				PasswordHashing.hashPassword(trainer.getSalt(), password))) {
			return trainer;
		}
		return null;
	}
	
	public String updatePassword(Trainer trainer, String oldPassword, String newPassword, String confirmPassword) {
		
		if (!newPassword.equals(confirmPassword)) {
			return "mismatch";
		} else {		
			if (trainer.getPassword().equals(PasswordHashing.hashPassword(trainer.getSalt(), oldPassword))) {
				trainer.setSalt(PasswordHashing.generateSalt());
				trainer.setPassword(PasswordHashing.hashPassword(trainer.getSalt(), newPassword));
				
				Trainer t = trainerRepository.save(trainer);
				if (t != null) {
					return "success";
				} else {
					return "error";
				}
			} else {
				return "invalid";
			}
		}
	}
	
	public RegisterReturn edit(Trainer trainer, String firstName, String lastName, String email, String url) {
		if (!trainer.getFirstName().equals(firstName)) {
			trainer.setFirstName(firstName);
		}
		
		if (!trainer.getLastName().equals(lastName)) {
			trainer.setLastName(lastName);
		}
		
		if (!trainer.getEmail().equals(email)) {
			trainer.setEmail(email);
			System.out.println(email);
			System.out.println(trainer.getEmail());
			
			if (trainerRepository.existsByEmail(trainer.getEmail())) {
				return new RegisterReturn(Status.EMAILEXISTS, null);
			}
		}
		
		if (!trainer.getUrl().equals(url)) {
			trainer.setUrl(url);
			
			if (trainer.getUrl() != null && TrainerValidator.isUrlValid(trainer)) {
				if (trainerRepository.existsByUrl(trainer.getUrl())) {
					return new RegisterReturn(Status.URLEXISTS, null);
				}
			}
		}
		
		Trainer t = trainerRepository.save(trainer);
		return new RegisterReturn(t != null ? Status.SUCCESS : Status.OTHERFAILURE, t);
	}

	@Override
	public RegisterReturn register(Trainer trainer) {
		if (!(TrainerValidator.isEmailValid(trainer) && TrainerValidator.isFirstNameValid(trainer)
				&& TrainerValidator.isLastNameValid(trainer) && TrainerValidator.isPasswordValid(trainer))) {
			return new RegisterReturn(Status.INPUTSINVALID, null);
		}
		if (trainerRepository.exists(trainer.getId())) {
			return new RegisterReturn(Status.OTHERFAILURE, null);
		}
		if (trainerRepository.existsByEmail(trainer.getEmail())) {
			return new RegisterReturn(Status.EMAILEXISTS, null);
		}
		if (trainer.getUrl() != null && TrainerValidator.isUrlValid(trainer)) {
			if (trainerRepository.existsByUrl(trainer.getUrl())) {
				return new RegisterReturn(Status.URLEXISTS, null);
			}
		} else {
			String url = new StringBuilder().append(trainer.getFirstName()).append('.').append(trainer.getLastName())
					.toString();
			if (trainerRepository.existsByUrl(url)) {
				int i = 0;
				String nextUrl;
				do {
					i++;
					nextUrl = new StringBuilder().append(url).append('.').append(i).toString();
				} while (trainerRepository.existsByUrl(nextUrl));
				url = nextUrl;
			}
			trainer.setUrl(url);
		}
		
		trainer.setSalt(PasswordHashing.generateSalt());
		trainer.setPassword(PasswordHashing.hashPassword(trainer.getSalt(), trainer.getPassword()));
		
		Trainer t = trainerRepository.save(trainer);
		return new RegisterReturn(t != null ? Status.SUCCESS : Status.OTHERFAILURE, t);
	}
}
