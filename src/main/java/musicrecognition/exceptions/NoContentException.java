package musicrecognition.exceptions;

import musicrecognition.util.Global;


public class NoContentException extends RuntimeException {
    String message = Global.Message.NO_CONTENT.getMessage();
    
    @Override
    public String getMessage() {
        return message;
    }
}
