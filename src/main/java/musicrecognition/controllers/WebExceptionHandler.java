package musicrecognition.controllers;

import musicrecognition.exceptions.FingerprintingException;
import musicrecognition.exceptions.NoContentException;
import musicrecognition.exceptions.UnsupportedAudioTypeException;
import musicrecognition.util.Global;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;


@ControllerAdvice(basePackages = "musicrecognition.controllers")
public class WebExceptionHandler {
    private static final Logger LOGGER = LogManager.getLogger(WebExceptionHandler.class);
    
    @ExceptionHandler(UnsupportedAudioTypeException.class)
    @ResponseBody
    ResponseEntity handleUnsupportedAudioTypeException(Throwable e) {
        LOGGER.error(e + " -- " + e.getMessage());
        Map<String, String> headers =
                Collections.singletonMap("message", Global.Message.UNSUPPORTED_TYPE.getMessage());
        
        return new ResponseEntity(headers, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
    }
    
    @ExceptionHandler(IOException.class)
    @ResponseBody
    ResponseEntity handleIOException(Throwable e) {
        LOGGER.error(e + " -- " + e.getMessage());
        Map<String, String> headers =
                Collections.singletonMap("message", Global.Message.IO_EXCEPTION.getMessage());
        
        return new ResponseEntity(headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(NoContentException.class)
    @ResponseBody
    ResponseEntity handleNoContentException(Exception e) {
        LOGGER.error(e + " -- " + e.getMessage());
        Map<String, String> headers =
                Collections.singletonMap("message", Global.Message.NO_CONTENT.getMessage());
        
        return new ResponseEntity(headers, HttpStatus.NO_CONTENT);
    }
    
    @ExceptionHandler(FingerprintingException.class)
    @ResponseBody
    ResponseEntity handleFingerprintingException(Throwable e) {
        LOGGER.error(e + " -- " + e.getMessage());
        Map<String, String> headers =
                Collections.singletonMap("message", Global.Message.CANNOT_FINGERPRINT.getMessage());
        
        return new ResponseEntity(headers, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    ResponseEntity handleDuplicateKeyException(Throwable e) {
        LOGGER.error(e + " -- " + e.getMessage());
        Map<String, String> headers =
                Collections.singletonMap("message", Global.Message.DUPLICATE.getMessage());
        
        return new ResponseEntity(headers, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
