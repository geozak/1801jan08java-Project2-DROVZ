package com.revature.project2.controller;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.boot.TempTableDdlTransactionHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.revature.project2.model.Photo;
import com.revature.project2.model.Trainer;
import com.revature.project2.repository.TrainerRepository;
import com.revature.project2.service.PhotoStorageService;
import com.revature.project2.service.PhotoStorageService.PhotoStorageResponse;
import com.revature.project2.session.SessionVariables;


@RestController("photoController")
//@Controller("photoController")
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*")
public class PhotoController {
	
	@Autowired
	PhotoStorageService storageService;
	
	@Autowired
	SessionVariables sessionVariables;
	
	@Autowired
	TrainerRepository repo;
	

	List<String> files = new ArrayList<String>();

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
			trainer.setProfilePicture(photoStorageResponse.getPhoto());
			repo.save(trainer);
			return ResponseEntity.ok(photoStorageResponse.getMessage());
		}

		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(photoStorageResponse.getMessage());
	}

	@GetMapping("/getallfiles")
	public ResponseEntity<List<String>> getListFiles(Model model) {
		List<String> fileNames = files
				.stream().map(fileName -> MvcUriComponentsBuilder
						.fromMethodName(PhotoController.class, "getFile", fileName).build().toString())
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(fileNames);
	}
}