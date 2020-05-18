package com.allscontractingdc.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.NoSuchElementException;
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

public class MailService {

	private static final String HOST = "smtp.gmail.com";
	private static final String GMAIL_PASSWORD = "7765@Amsr";
	private static final String GMAIL_USER = "6975.usa@gmail.com";

	private static final String TO = "anselmo.sr@gmail.com";
	private static final String SUBJECT = "Email de teste";
	private static final String PDF_FOLDER = "C:\\Users\\Anselmo.asr\\Google Drive\\All's Contracting\\proposals";
	private static final String TEXT = "Dear {}\r\n" + "\r\n"
			+ "Thank you for contacting us for your business improvement needs.\r\n" + "Please see proposal #1 attached.\r\n"
			+ "\r\n"
			+ "If you agree with this proposal's terms and condition, just sign it and send it back to us and we will put your job on our schedule.\r\n"
			+ "\r\n"
			+ "A reply to this email will be appreciated so we make sure the estimate reached the right customer.\r\n"
			+ "\r\n" + "Feel free to contact us if you have any question.\r\n" + "\r\n" + "Hope to hear from you soon";

	public FileSystemResource findPdfFile(String clientName) throws IOException {
		File file = Files.list(Paths.get(PDF_FOLDER))
				.filter(f -> f.getFileName().toString().startsWith(clientName) && f.getFileName().toString().endsWith("pdf"))
				.sorted(Comparator.reverseOrder()).findFirst().orElseThrow(() -> new NoSuchElementException()).toFile();
		return new FileSystemResource(file);
	}

	public void sendProposalEmail(String clientName) throws IOException {
		ExecutorService emailExecutor = Executors.newSingleThreadExecutor();
		FileSystemResource file = this.findPdfFile(clientName);
		emailExecutor.execute(() -> {
			try {
				MimeMessage message = emailSender().createMimeMessage();
				MimeMessageHelper helper = new MimeMessageHelper(message, true);
				helper.setTo(TO);
				helper.setSubject(SUBJECT);
				helper.setText(TEXT.replace("{}", clientName));
				helper.addAttachment(file.getFilename(), file);
				emailSender().send(message);
			} catch (MailException | MessagingException e) {
				e.printStackTrace();
			}
		});
		emailExecutor.shutdown();
	}

	public JavaMailSender emailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(HOST);
		mailSender.setPort(465);
		mailSender.setUsername(GMAIL_USER);
		mailSender.setPassword(GMAIL_PASSWORD);
		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.ssl.enable", "true");
		return mailSender;
	}

}
