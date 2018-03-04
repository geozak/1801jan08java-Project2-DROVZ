package com.revature.project2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.project2.model.PasswordReset;
import com.revature.project2.model.Trainer;
import com.revature.project2.repository.PasswordResetRepository;
import com.revature.project2.repository.TrainerRepository;
import com.revature.project2.util.PasswordHashing;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpSession;

@Service("forgotPasswordService")
public class ForgotPasswordServiceImpl implements ForgotPasswordService {
	@Autowired
	private PasswordResetRepository passwordResetRepository;
	
	@Autowired
	private TrainerRepository trainerRepository;
	
	@Override
	public Trainer findEmail(String email) {
		Trainer trainer = trainerRepository.findByEmail(email);
		return trainer;
	}
	

	@Override
	public PasswordReset findToken(String token) {
		PasswordReset pr = passwordResetRepository.findByToken(token);
		return pr;
	}

	@Override
	public PasswordReset findTokenByTrainerId(int id) {
		PasswordReset pr = passwordResetRepository.findByTrainerId(id);
		return pr;
	}
	
	@Override
	public boolean requestPasswordReset(String email, String token) {
		Trainer trainer = trainerRepository.findByEmail(email);
		PasswordReset prToken = new PasswordReset(trainer, token);
		if (passwordResetRepository.save(prToken) == null)
			return false;
		
		Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        
        Session session = Session.getInstance(properties, new javax.mail.Authenticator() {
        	protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("team.drovz@gmail.com", "revature");
            }
        });
        
        try {
            Message message = new MimeMessage(session);
            message.setRecipients(Message.RecipientType.TO,
                InternetAddress.parse(trainer.getEmail()));
            message.setSubject("Password Reset");
            message.setContent(token, "text/html; charset=utf-8");

            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        
		return true;
	}

	@Override
	public boolean resetPassword(Trainer trainer, String newPassword) {
		trainer.setSalt(PasswordHashing.generateSalt());
		trainer.setPassword(PasswordHashing.hashPassword(trainer.getSalt(), newPassword));
		Trainer newTrainer = trainerRepository.save(trainer);
		if (newTrainer == null)
			return false;
		return true;
	}
	
	@Override
	public boolean deleteToken(String token) {
		PasswordReset pr = passwordResetRepository.findByToken(token);
		passwordResetRepository.delete(pr.getId());
		
		pr =  passwordResetRepository.findByToken(token);
		if (pr == null)
			return true;
		return false;
	}
}
