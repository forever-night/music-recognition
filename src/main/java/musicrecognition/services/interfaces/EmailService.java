package musicrecognition.services.interfaces;

import musicrecognition.entities.User;


public interface EmailService {
    void sendRegistrationConfirmation(User user);
}
