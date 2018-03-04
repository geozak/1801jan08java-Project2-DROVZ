package com.revature.project2.controler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project2.JSON.MessageJSON;
import com.revature.project2.JSON.TrainerJSON;
import com.revature.project2.model.Trainer;
import com.revature.project2.service.AuthService;
import com.revature.project2.session.SessionVariables;

@RestController("authController")
//@Controller("authController")
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*")
public class AuthController {
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private SessionVariables sessionVariables;
	
	@GetMapping("/")
	public @ResponseBody ResponseEntity<String> rootGET() {
		return new ResponseEntity<String>("Working", HttpStatus.OK);
	}
	
	@PostMapping("/updatePassword")
	//firstName: string, lastName: string, email: string, password: string
	public @ResponseBody ResponseEntity<MessageJSON> updatePassword(
			@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword,
			@RequestParam("confirmPassword") String confirmPassword) {
		
		System.out.println("inside change password");
		
		Trainer trainer = sessionVariables.getTrainer();
		
		if (trainer == null) {
			System.out.println("FAIL to upload not logged in");
			return new ResponseEntity<MessageJSON>(HttpStatus.UNAUTHORIZED);
		}
		
		String returnStatus = authService.updatePassword(trainer, oldPassword, newPassword, confirmPassword);
		
		System.out.println("Status: " + returnStatus);
		return new ResponseEntity<MessageJSON>(new MessageJSON(returnStatus), HttpStatus.OK);
	}
	
	@PostMapping("/update")
	//firstName: string, lastName: string, email: string, password: string
	public @ResponseBody ResponseEntity<MessageJSON> update(
			@RequestParam("firstName") String firstName, 
			@RequestParam("lastName") String lastName,
			@RequestParam("email") String email, 
			@RequestParam("url") String url) {
		
		System.out.println("inside update");
		
		Trainer trainer = sessionVariables.getTrainer();
		if(trainer == null) {
			System.out.println("FAIL to upload not logged in");
			return new ResponseEntity<MessageJSON>(HttpStatus.UNAUTHORIZED);
		}
		
		System.out.println("updating user: " + firstName + " " + lastName);
		System.out.println("email: " + email);
		System.out.println("url: " + url);
		
		AuthService.RegisterReturn registerReturn = authService.edit(trainer, firstName, lastName, email, url);

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
			@RequestParam("password") String password) {
		
		sessionVariables.setTrainer(null);
		
		Trainer trainer = authService.login(email, password);
		
		if (trainer == null) {
			System.out.println("user not logged in");
			return new ResponseEntity<TrainerJSON>((TrainerJSON) null, HttpStatus.OK);
		}
		
//		httpSession.setAttribute("trainer", trainer);
		sessionVariables.setTrainer(trainer);
		return new ResponseEntity<TrainerJSON>(new TrainerJSON(trainer, true), HttpStatus.OK);
	}
	
	
}
