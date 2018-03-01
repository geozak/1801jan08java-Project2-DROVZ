package com.revature.project2.controler;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

import com.revature.project2.service.PhotoStorageService;


@RestController("photoController")
//@CrossOrigin(origins = "http://localhost:4200")
@CrossOrigin(origins = "*")
public class PhotoController {
	
	@Autowired
	PhotoStorageService storageService;

	List<String> files = new ArrayList<String>();

	@PostMapping("/postPhoto.app")
	public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
		String message = "";
		try {
			
			//storageService.storeFile(file);
			files.add(file.getOriginalFilename());

			message = "You successfully uploaded " + file.getOriginalFilename() + "!";
			File newFile = new File(file.getOriginalFilename());
			file.transferTo(newFile);
			String url = storageService.storePhoto(newFile, file.getOriginalFilename());
			System.out.println(url);
			
			return ResponseEntity.status(HttpStatus.OK).body(message);
		} catch (Exception e) {
			message = "FAIL to upload " + file.getOriginalFilename() + "!";
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(message);
		}
	}

	@GetMapping("/getallfiles")
	public ResponseEntity<List<String>> getListFiles(Model model) {
		List<String> fileNames = files
				.stream().map(fileName -> MvcUriComponentsBuilder
						.fromMethodName(PhotoController.class, "getFile", fileName).build().toString())
				.collect(Collectors.toList());

		return ResponseEntity.ok().body(fileNames);
	}

	@GetMapping("/files/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		Resource file = new Resource() {
			
			@Override
			public InputStream getInputStream() throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public long lastModified() throws IOException {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public boolean isReadable() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isOpen() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public URL getURL() throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public URI getURI() throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getFilename() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public File getFile() throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getDescription() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean exists() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Resource createRelative(String relativePath) throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public long contentLength() throws IOException {
				// TODO Auto-generated method stub
				return 0;
			}
		}; //storageService.loadFile(filename);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}
	@PostMapping("/getPoke.app")
	public @ResponseBody ResponseEntity<Boolean> getPoke(@RequestBody int id){
		return ResponseEntity.ok().body(true);
	}
}