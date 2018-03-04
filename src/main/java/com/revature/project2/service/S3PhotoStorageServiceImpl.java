package com.revature.project2.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.revature.project2.model.Photo;
import com.revature.project2.model.Trainer;
import com.revature.project2.repository.PhotoRepository;

@Service("photoStorageService")
public class S3PhotoStorageServiceImpl implements PhotoStorageService {

	AmazonS3Client s3Client;
	@Autowired
	PhotoRepository photoRepo;

	@Value("${s3.bucket-name}")
	private String bucketName;

	@Autowired
	public S3PhotoStorageServiceImpl(@Value("${s3.region}") final String region,
			@Value("${s3.access-key-id}") final String accessKeyId,
			@Value("${s3.secret-access-key}") final String secretAccessKey) {

		System.out.println("Connecting to AWS S3");
		// System.out.println(region);
		// System.out.println(accessKeyId);
		// System.out.println(secretAccessKey);

		s3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard().withRegion(region)
				.withCredentials(
						new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKeyId, secretAccessKey)))
				.build();
	}

	@Override
	public PhotoStorageResponse storePhoto(MultipartFile file, Trainer creator) {

		// PutObjectRequest putRequest = new PutObjectRequest(bucketName, objectName,
		// photo).withCannedAcl(CannedAccessControlList.PublicRead);
		// PutObjectResult response = s3Client.putObject(putRequest);
		File newFile = null;
		try {
			newFile = File.createTempFile(System.currentTimeMillis() + file.getOriginalFilename(), null);
			try (FileOutputStream fos = new FileOutputStream(newFile)) {
				fos.write(file.getBytes());
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
				return new PhotoStorageResponse(false, "FAIL to upload file!", null);
			} catch (IOException e1) {
				e1.printStackTrace();
				return new PhotoStorageResponse(false, "FAIL to upload file!", null);
			}

			String url;
			try {
				s3Client.putObject(new PutObjectRequest(bucketName, newFile.getName(), newFile)
						.withCannedAcl(CannedAccessControlList.PublicRead));

				url = s3Client.getResourceUrl(bucketName, newFile.getName());
				System.out.println("Uploaded object url: " + url);

			} catch (AmazonServiceException e) {
				e.printStackTrace();
				return new PhotoStorageResponse(false, "FAIL to upload bc of amazon!", null);
			} catch (SdkClientException e) {
				e.printStackTrace();
				return new PhotoStorageResponse(false, "FAIL to upload file!", null);
			}

			Photo photo = new Photo(url, creator);
			photoRepo.save(photo);
			return new PhotoStorageResponse(true, url, photo);
		} catch (IOException e) {
			e.printStackTrace();
			return new PhotoStorageResponse(false, "FAIL to upload file!", null);
		} finally {
			if (newFile != null)
				newFile.delete();
		}

	}

//	@Override
//	public void deleteFile(String url) {
//		try {
//			s3Client.deleteObject(bucketName, url);
//		} catch (AmazonServiceException e) {
//			e.printStackTrace();
//		} catch (SdkClientException e) {
//			e.printStackTrace();
//		}
//	}

}
