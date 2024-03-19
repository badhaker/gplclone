package com.alchemy.serviceInterface;

import java.io.IOException;

import javax.mail.MessagingException;

import org.springframework.web.multipart.MultipartFile;

import com.alchemy.entities.UserTrackEntity;


public interface EmailInterface {

	public String sendMail(String emailTo, String subject, String text);
	
	public String sendEnrollmentMain(String status, UserTrackEntity userTrack)throws MessagingException;

	public int generateOTP();

	public void sendSimpleMessage(String email, String string, String url) throws MessagingException;

	MultipartFile htmlToPdf(String processedHtml, String originalFileName) throws Exception;
	
	public MultipartFile generateImageFromPDFf(String filename,String originalFileName ,String extension) throws IOException;
}
