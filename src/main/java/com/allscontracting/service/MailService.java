package com.allscontracting.service;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class MailService {



	public void sendProposalByEmail(String clientName, File proposalPdfFile) throws IOException {
		ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
		FileSystemResource file = new FileSystemResource(proposalPdfFile);
		emailExecutor.execute(() -> {
			try {
				MimeMessage message = emailSender().createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				helper.setTo(TO);
				helper.setSubject(SUBJECT);
				helper.setFrom(GMAIL_USER, "All's Contracting Inc");
				helper.setText(TEXT.replace("{}", clientName));
				helper.addAttachment(file.getFilename(), file);
				System.out.print("Enviando....");
				emailSender().send(message);
				System.out.println("OK");
			} catch (MailException | MessagingException | UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		});
		emailExecutor.shutdown();
	}

	public JavaMailSender emailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(HOST);
		mailSender.setPort(PORT);
		
		mailSender.setUsername(GMAIL_USER);
		mailSender.setPassword(GMAIL_PASSWORD);
		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.enable", "true");
		return mailSender;
	}

	public static void main(String[] args) throws IOException {
		
		new MailService().sendProposalByEmail("Test Name", new File("D:/temp/proposal.pdf"));

	}
}
