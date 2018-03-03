package com.revature.project2.controler;

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
import org.springframework.web.multipart.MultipartFile;

import com.revature.project2.JSON.TrainerJSON;
import com.revature.project2.model.Trainer;
import com.revature.project2.service.PhotoStorageService;
import com.revature.project2.service.TrainerService;
import com.revature.project2.service.PhotoStorageService.PhotoStorageResponse;
import com.revature.project2.session.SessionVariables;

import ch.qos.logback.core.net.SyslogOutputStream;

@RestController("trainerController")
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*")
public class TrainerController {
	
	@Autowired
	TrainerService trainerService;
	
	@Autowired
	PhotoStorageService storageService;
	
	@Autowired
	SessionVariables sessionVariables;
	
	@GetMapping("/getAllTrainers")
	public @ResponseBody ResponseEntity<List<TrainerJSON>> getAllTrainers(){
		List<Trainer> trainers = trainerService.getAllTrainers();
		List<TrainerJSON> output = new ArrayList<>();
		trainers.forEach(trainer -> output.add(new TrainerJSON(trainer)));
		return new ResponseEntity<>(output, HttpStatus.OK);
	}
	
	
	@PostMapping("/getTrainerByUrl")
	public @ResponseBody ResponseEntity<TrainerJSON> getTrainerByUrl(@RequestParam("url") String url){
		System.out.println("URL: "+ url);
		Trainer t=trainerService.getTrainerByUrl(url);
		return new ResponseEntity<TrainerJSON>(new TrainerJSON(t, true), HttpStatus.OK);
		
	}
	
	@PostMapping("/postPhoto")
	public ResponseEntity<String> handleFileUpload(
			@RequestParam("file") MultipartFile file) {

		Trainer trainer = sessionVariables.getTrainer();
		if(trainer == null) {
			System.out.println("FAIL to upload not logged in");
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("FAIL to upload not logged in");
		}
		
		PhotoStorageResponse photoStorageResponse = storageService.storePhoto(file, trainer);
		if(photoStorageResponse.isSuccess()) {
			trainerService.updateTrainerPhoto(trainer, photoStorageResponse.getPhoto());
			return ResponseEntity.ok(photoStorageResponse.getMessage());
		}

		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(photoStorageResponse.getMessage());
	}
	


}
