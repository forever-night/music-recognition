package musicrecognition.controllers.json;

import musicrecognition.dto.TrackMatch;
import musicrecognition.services.IdentificationService;
import musicrecognition.util.audio.audiotypes.AudioType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Part;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;


@Controller
@RequestMapping(value = "/upload")
public class JsonFileUploadController {
    private static final Logger LOGGER = LogManager.getLogger(JsonFileUploadController.class);
    
    @Autowired
    IdentificationService identificationService;
    
    @RequestMapping(method = RequestMethod.POST, params = "identify")
    @ResponseBody
    public ResponseEntity<List<TrackMatch>> identify(@RequestParam Part file)
            throws IOException {
        String fileName = file.getSubmittedFileName();
        AudioType.Type type;
        
        if (fileName.endsWith(".mp3"))
            type = AudioType.Type.MP3;
        else if(fileName.endsWith(".wav"))
            type = AudioType.Type.WAV;
        else {
            LOGGER.error("unacceptable file format: " + fileName);
            return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
        
        
        InputStream inputStream = null;
        List<TrackMatch> matches = null;
        
        try {
            inputStream = new BufferedInputStream(file.getInputStream());       // must support mark/reset
            matches = identificationService.identify(inputStream, type);
        } catch (IOException e) {
            LOGGER.error("error in reading input stream");
            
            if (inputStream != null)
                inputStream.close();
            
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } finally {
            if (inputStream != null)
                inputStream.close();
        }
        
        
        return new ResponseEntity<>(matches, HttpStatus.OK);
    }
}
