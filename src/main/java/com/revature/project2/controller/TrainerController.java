package com.revature.project2.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.project2.JSON.PostJSON;
import com.revature.project2.JSON.TrainerJSON;
import com.revature.project2.model.Trainer;
import com.revature.project2.service.TrainerService;
import com.revature.project2.session.SessionVariables;

@RestController("trainerController")
@CrossOrigin(origins = "http://localhost:4200")
//@CrossOrigin(origins = "*")
public class TrainerController {
	
	@Autowired
	TrainerService trainerService;
	
	@Autowired
	SessionVariables sessionVariables;
	
	@GetMapping("/getAllTrainers")
	public @ResponseBody ResponseEntity<List<TrainerJSON>> getAllTrainers(){
		
		if(sessionVariables.getTrainer() == null) {
			System.out.println("FAIL to get trainers not logged in");
			return new ResponseEntity<List<TrainerJSON>>(HttpStatus.UNAUTHORIZED);
		}
		List<Trainer> trainers = trainerService.getAllTrainers();
		List<TrainerJSON> output = new ArrayList<>();
		trainers.forEach(trainer -> output.add(new TrainerJSON(trainer)));
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
	
	
	@PostMapping("/getTrainerByUrl")
	public @ResponseBody ResponseEntity<TrainerJSON> getTrainerByUrl(@RequestParam("url") String url){
		System.out.println("URL: "+ url);
		if(sessionVariables.getTrainer() == null) {
			System.out.println("FAIL to get trainer not logged in");
			return new ResponseEntity<TrainerJSON>(HttpStatus.UNAUTHORIZED);
		}
		Trainer t=trainerService.getTrainerByUrl(url);
		return new ResponseEntity<TrainerJSON>(new TrainerJSON(t, true), HttpStatus.OK);
		
	}
	


}
