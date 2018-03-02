package com.revature.project2.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.revature.project2.model.Photo;
import com.revature.project2.repository.PhotoRepository;

@Service("photoStorageService")
public class S3PhotoStorageServiceImpl implements PhotoStorageService {
	
	public class PhotoStorageResponse{
		public final boolean success;
		public final String message;
		public PhotoStorageResponse(boolean success, String message) {
			super();
			this.success = success;
			this.message = message;
		}
		
	}

	AmazonS3Client s3Client;
	@Autowired
	PhotoRepository photoRepo;
	
	@Value("${s3.bucket-name}")
	private String bucketName;

	@Autowired
	public S3PhotoStorageServiceImpl(
			@Value("${s3.region}") final String region,
			@Value("${s3.access-key-id}") final String accessKeyId,
			@Value("${s3.secret-access-key}") final String secretAccessKey) {
		
		System.out.println("Connecting to AWS S3");
//		System.out.println(region);
//		System.out.println(accessKeyId);
//		System.out.println(secretAccessKey);
		
		s3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard()
				.withRegion(region)
				.withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKeyId, secretAccessKey)))
				.build();
	}

	@Override
	public PhotoStorageResponse storePhoto(MultipartFile file, Photo photo) {
		
		//PutObjectRequest putRequest = new PutObjectRequest(bucketName, objectName, photo).withCannedAcl(CannedAccessControlList.PublicRead);
		//PutObjectResult response = s3Client.putObject(putRequest);
		File newFile = new File(System.currentTimeMillis() + file.getOriginalFilename());
		try {
			try(FileOutputStream fos = new FileOutputStream(newFile)) {
				fos.write(file.getBytes());
				//file.transferTo(newFile); // why this doesnt work... i dont know
			}  catch (FileNotFoundException e1) {
				e1.printStackTrace();
				return new PhotoStorageResponse(false, "FAIL to upload file!");
			} catch (IOException e1) {
				e1.printStackTrace();
				return new PhotoStorageResponse(false, "FAIL to upload file!");
			}
			try {
				s3Client.putObject(
						new PutObjectRequest(bucketName, newFile.getName(), newFile)
						.withCannedAcl(CannedAccessControlList.PublicRead));
		        
		        String url = s3Client.getResourceUrl(bucketName, newFile.getName());
		        System.out.println("Uploaded object url: " + url);
		        
		        photo.setUrl(url);
		        photoRepo.save(photo);
		        return new PhotoStorageResponse(true, url);
			} catch (AmazonServiceException e) {
				e.printStackTrace();
				return new PhotoStorageResponse(false, "FAIL to upload bc of amazon!");
			}catch (SdkClientException e) {
				e.printStackTrace();
				return new PhotoStorageResponse(false, "FAIL to upload file!");
			}
		} finally {
			newFile.delete();
		}
		
		
        
	}
	
	@Override
	public void deleteFile(String url) {
		try {
			s3Client.deleteObject(bucketName, url);
		} catch (AmazonServiceException e) {
			e.printStackTrace();
		}catch (SdkClientException e) {
			e.printStackTrace();
		}
	}

}
