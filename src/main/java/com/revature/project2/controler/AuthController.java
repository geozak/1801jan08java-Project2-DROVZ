package com.revature.project2.controler;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
	public @ResponseBody ResponseEntity<String> register(@RequestBody Trainer trainer) {
		AuthService.RegisterReturn registerReturn = authService.register(trainer);

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

		return new ResponseEntity<String>(message, HttpStatus.OK);
	}

	@PostMapping("/login")
	public @ResponseBody ResponseEntity<TrainerJSON> login(@RequestParam("email") String email, @RequestParam("password") String password, HttpSession httpSession) {
		httpSession.removeAttribute("trainer");
		
		Trainer trainer = authService.login(email, password);
		
		if (trainer == null) {
			return new ResponseEntity<TrainerJSON>((TrainerJSON) null, HttpStatus.OK);
		}
		
		httpSession.setAttribute("trainer", trainer);
		return new ResponseEntity<TrainerJSON>(new TrainerJSON(trainer), HttpStatus.OK);
	}
	
	
}

// @PostMapping("/registerHero.app")
// public @ResponseBody ResponseEntity<Message> registerHero(@RequestBody Hero
// hero){
// heroService.registerHero(hero);
// return new ResponseEntity<>(new Message("HERO REGISTERED SUCCESSFULLY."),
// HttpStatus.OK);
// }
//
// @GetMapping("/getAllHeroes.app")
// public @ResponseBody ResponseEntity<List<Hero>> getAllHeroes(){
// return new ResponseEntity<>(heroService.getAllHeroes(), HttpStatus.OK);
// }
//
// @PostMapping("/getHero.app")
// public @ResponseBody ResponseEntity<Hero> getHero(@RequestBody Hero hero){
// return new ResponseEntity<>(heroService.findHero(hero), HttpStatus.OK);
// }