package com.revature.project2.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

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
	private String bucketName;

	public S3PhotoStorageServiceImpl() {
		Properties s3Properties = new Properties();

		try {
			s3Properties.load(new FileInputStream(getClass().getClassLoader().getResource("s3.properties").getFile()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		s3Client = (AmazonS3Client) AmazonS3ClientBuilder.standard()
				.withRegion(s3Properties.getProperty("region"))
				.withCredentials(
					new AWSStaticCredentialsProvider(
							new BasicAWSCredentials(
									s3Properties.getProperty("access-key-id"), 
									s3Properties.getProperty("secret-access-key")
				))).build();
		bucketName = s3Properties.getProperty("bucket-name");
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
