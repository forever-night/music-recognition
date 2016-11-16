package musicrecognition.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jndi.JndiTemplate;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.naming.NamingException;
import java.util.Properties;


@Configuration
@Import({DataConfig.class, JdbcConfig.class, WebConfig.class})
@ComponentScan("musicrecognition")
public class AppConfig {
    @Bean
    CommonsMultipartResolver filterMultipartResolver() {
        return new CommonsMultipartResolver();
    }
    
    @Bean
    public Session mailSession() {
        JndiTemplate jndi = new JndiTemplate();
        Session session = null;
        
        try {
            session = (Session) jndi.lookup("java:jboss/mail/GmailMusicRecognition");
        } catch (NamingException e) {
            e.printStackTrace();
        }
        
        if (session == null) {
            final String username = "placeholder@gmail.com";
            final String password = "placeholder";
    
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", "smtp.gmail.com");
            props.put("mail.smtp.port", "587");
    
            session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
            });
        }
        
        return session;
    }
    
    @Bean
    public MailSender mailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setSession(mailSession());
        return mailSender;
    }
}
