package musicrecognition.exceptions;

import musicrecognition.util.Global;


public class FingerprintingException extends RuntimeException {
    String message = Global.Message.CANNOT_FINGERPRINT.getMessage();
    
    @Override
    public String getMessage() {
        return message;
    }
}
