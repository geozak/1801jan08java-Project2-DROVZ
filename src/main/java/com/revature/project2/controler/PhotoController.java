package com.revature.project2.controler;

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

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.revature.project2.model.Trainer;
import com.revature.project2.repository.TrainerRepository;
import com.revature.project2.service.PhotoStorageService;
import com.revature.project2.service.S3PhotoStorageServiceImpl.PhotoStorageResponse;


@RestController("photoController")
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*")
public class PhotoController {
	
	@Autowired
	PhotoStorageService storageService;
	
	@Autowired
	TrainerRepository repo;
	

	List<String> files = new ArrayList<String>();

	@PostMapping("/postPhoto.app")
	public ResponseEntity<String> handleFileUpload(
			@RequestParam("file") MultipartFile file
			, @RequestParam("trainer") Trainer trainer
			, HttpSession httpSession) {

		System.out.println("Photo Upload Session ID: " + httpSession.getId());
		
		Object to = httpSession.getAttribute("trainer");
		if(to == null) {
			System.out.println("not logged in");
		}
		else if (to instanceof Trainer ) {
			Trainer tt = (Trainer) to;
			System.out.println(tt);
			
		}
		else {
			System.out.println("user object not of type trainer");
		}
		
		PhotoStorageResponse temp = storageService.storePhoto(file, trainer.getProfilePicture());
		if(temp.success) {
			// if user or post already has a photo
			// delete photo from s3
			// store the temp.body in the photo
			
			// update the photo on the profile or post
			//storageService.deleteFile(temp.message);
			return ResponseEntity.ok(temp.message);
		}

		return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(temp.message);
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