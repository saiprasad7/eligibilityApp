package com.eligibility.benefit.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.eligibility.benefit.controller.UserController;
import com.fasterxml.jackson.databind.JsonNode;

@Service
public class MailServices {

	protected Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired(required = true)
	JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	String mailId;

	public MailServices(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}

	public void sendMail(JsonNode userNode) {
		try {
			LoggerUtil.infoLog(logger,"Email is ready "+mailId);
			LoggerUtil.infoLog(logger,"sendMail method is start ");

			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setTo(userNode.get("to").asText());
			mailMessage.setSubject(userNode.get("subject").asText());
			mailMessage.setText(userNode.get("message").asText());
			mailMessage.setFrom(mailId);
			javaMailSender.send(mailMessage);
			LoggerUtil.infoLog(logger,"Mail sent successfully ");

		} catch (MailAuthenticationException e) {
			e.printStackTrace();
			LoggerUtil.infoLog(logger,"sendMail method is error ");
		} catch (Exception e) {
			e.printStackTrace();
			LoggerUtil.infoLog(logger,"sendMail method is error ");
		}
	}
}
