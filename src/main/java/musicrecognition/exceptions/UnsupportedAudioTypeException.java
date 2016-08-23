package musicrecognition.exceptions;

import musicrecognition.util.Global;


public class UnsupportedAudioTypeException extends RuntimeException {
    String message = Global.Message.UNSUPPORTED_TYPE.getMessage();
    
    @Override
    public String getMessage() {
        return message;
    }
}
