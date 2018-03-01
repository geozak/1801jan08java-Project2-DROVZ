package com.revature.project2.service;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;

@Service("photoStorageService")
public class S3PhotoStorageServiceImpl implements PhotoStorageService {

	AmazonS3Client s3Client;
	
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
	public String storePhoto(File photo, String fileName) {
		String objectName = System.currentTimeMillis() + fileName;
		//PutObjectRequest putRequest = new PutObjectRequest(bucketName, objectName, photo).withCannedAcl(CannedAccessControlList.PublicRead);
		//PutObjectResult response = s3Client.putObject(putRequest);
		s3Client.putObject(
				new PutObjectRequest(bucketName, objectName, photo)
				.withCannedAcl(CannedAccessControlList.PublicRead));
        
        String url = s3Client.getResourceUrl(bucketName, objectName);
        System.out.println("Uploaded object url: " + url);
        
        return url;
	}

}
