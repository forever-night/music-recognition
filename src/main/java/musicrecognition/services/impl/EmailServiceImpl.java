package musicrecognition.services.impl;

import musicrecognition.entities.User;
import musicrecognition.services.interfaces.EmailService;
import musicrecognition.util.Global;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;


@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    MailSender mailSender;
    
    @Override
    public void sendRegistrationConfirmation(User user) {
        String messageText = "You have been successfully registered at MusicRecognition!";
        messageText += "\nUsername: " + user.getUsername();
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(Global.APP_EMAIL);
        message.setTo(user.getEmail());
        message.setSubject(Global.EmailSubject.REGISTRATION_CONFIRM.getMessage());
        message.setText(messageText);
        
        mailSender.send(message);
    }
}
