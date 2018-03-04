package com.revature.project2.controler;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project2.JSON.MessageJSON;
import com.revature.project2.model.PasswordReset;
import com.revature.project2.model.Trainer;
import com.revature.project2.service.ForgotPasswordService;

@RestController("forgotController")
@CrossOrigin(origins = "*")
public class ForgotPasswordController {
	@Autowired
	private ForgotPasswordService forgotPasswordService;

	@PostMapping("/reset")
	public @ResponseBody ResponseEntity<MessageJSON> forgotRequest(@RequestParam("email") String userEmail) {
		Trainer trainer = forgotPasswordService.findEmail(userEmail);

		if (trainer == null)
			return new ResponseEntity<MessageJSON>(new MessageJSON("email"), HttpStatus.OK);
		
		forgotPasswordService.deletePreviousTokens(trainer);

		String token = UUID.randomUUID().toString();

		forgotPasswordService.requestPasswordReset(userEmail, token);

		return new ResponseEntity<MessageJSON>(new MessageJSON("success"), HttpStatus.OK);
	}

	@PostMapping("/enter-token")
	public @ResponseBody ResponseEntity<MessageJSON> verifyToken(@RequestParam("email") String email,
			@RequestParam("token") String token) {
		Trainer trainer = forgotPasswordService.findEmail(email);

		if (trainer == null)
			return new ResponseEntity<MessageJSON>(new MessageJSON("email"), HttpStatus.OK);

		PasswordReset pr = forgotPasswordService.findToken(token);

		if (pr == null)
			return new ResponseEntity<MessageJSON>(new MessageJSON("token"), HttpStatus.OK);

		if (pr.getTrainer().getId() != trainer.getId())
			return new ResponseEntity<MessageJSON>(new MessageJSON("match"), HttpStatus.OK);

		return new ResponseEntity<MessageJSON>(new MessageJSON("success"), HttpStatus.OK);
	}

	@PostMapping("/enter-password")
	public @ResponseBody ResponseEntity<MessageJSON> changePassword(@RequestParam("email") String email,
			@RequestParam("newPassword") String newPassword, @RequestParam("confirmPassword") String confirmPassword, @RequestParam("token") String token) {
		Trainer trainer = forgotPasswordService.findEmail(email);

		if (trainer == null)
			return new ResponseEntity<MessageJSON>(new MessageJSON("email"), HttpStatus.OK);
		
		PasswordReset pr = forgotPasswordService.findToken(token);

		if (pr == null)
			return new ResponseEntity<MessageJSON>(new MessageJSON("token"), HttpStatus.OK);
		
		if (pr.getTrainer().getId() != trainer.getId())
			return new ResponseEntity<MessageJSON>(new MessageJSON("id"), HttpStatus.OK);
		
		if (newPassword.equals(confirmPassword) == false)
			return new ResponseEntity<MessageJSON>(new MessageJSON("match"), HttpStatus.OK);
		
		//passwords match, time to hash and store the new password
		boolean success = forgotPasswordService.resetPassword(trainer, newPassword);
		if (success == false)
			return new ResponseEntity<MessageJSON>(new MessageJSON("password"), HttpStatus.OK);
		
		//also need to delete the token
		boolean deleteToken = forgotPasswordService.deleteToken(token);

		return new ResponseEntity<MessageJSON>(new MessageJSON("success"), HttpStatus.OK);
	}
}
