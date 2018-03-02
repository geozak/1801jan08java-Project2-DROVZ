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
import com.revature.project2.service.ForgotPasswordService;

@RestController("forgotController")
@CrossOrigin(origins = "*")
public class ForgotPasswordController {
	@Autowired
	private ForgotPasswordService forgotPasswordService;
	
	@PostMapping("/reset")
	public @ResponseBody ResponseEntity<MessageJSON> forgotRequest(@RequestParam("email") String userEmail) {
		boolean foundEmail = forgotPasswordService.findEmail(userEmail);
		
		String token = UUID.randomUUID().toString();
		
		return new ResponseEntity<MessageJSON>(new MessageJSON("success"), HttpStatus.OK);
	}
}
