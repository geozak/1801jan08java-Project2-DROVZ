package com.revature.project2.controler;

import javax.servlet.http.HttpSession;
import javax.swing.plaf.synth.SynthSeparatorUI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project2.JSON.MessageJSON;
import com.revature.project2.JSON.TrainerJSON;
import com.revature.project2.model.Trainer;
import com.revature.project2.service.AuthService;

@RestController("authController")
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*")
public class AuthController {
	
	@Autowired
	private AuthService authService;

	@PostMapping("/register")
	//firstName: string, lastName: string, email: string, password: string
	public @ResponseBody ResponseEntity<MessageJSON> register(
			@RequestParam("firstName") String firstName, 
			@RequestParam("lastName") String lastName,
			@RequestParam("email") String email, 
			@RequestParam("password") String password) {
		
		System.out.println("registering user: " + firstName + " " + lastName);
		System.out.println("email: " + email);
		System.out.println("Password: " + password);
		AuthService.RegisterReturn registerReturn = authService.register(new Trainer(null, firstName, lastName, null, password, email));

		String message;
		// SUCCESS, INPUTSINVALID, EMAILEXISTS, URLEXISTS, OTHERFAILURE
		switch (registerReturn.status) {
		case SUCCESS:
			message = "success";
			break;
		case INPUTSINVALID:
			message = "inputs";
			break;
		case URLEXISTS:
			message = "url";
			break;
		case EMAILEXISTS:
			message = "email";
			break;
		case OTHERFAILURE:
		default:
			message = "other";
			break;
		}
		System.out.println("Status: " + message);
		return new ResponseEntity<MessageJSON>(new MessageJSON(message), HttpStatus.OK);
	}

	@PostMapping("/login")
	public @ResponseBody ResponseEntity<TrainerJSON> login(
			@RequestParam("email") String email, 
			@RequestParam("password") String password, 
			HttpSession httpSession) {
		System.out.println("Login Session ID: " + httpSession.getId());
		httpSession.removeAttribute("trainer");
		
		Trainer trainer = authService.login(email, password);
		
		if (trainer == null) {
			return new ResponseEntity<TrainerJSON>((TrainerJSON) null, HttpStatus.OK);
		}
		
		httpSession.setAttribute("trainer", trainer);
		return new ResponseEntity<TrainerJSON>(new TrainerJSON(trainer, true), HttpStatus.OK);
	}
	
}
