package musicrecognition.controllers.json;

import musicrecognition.dto.TrackMatch;
import musicrecognition.entities.Track;
import musicrecognition.services.interfaces.FingerprintService;
import musicrecognition.services.interfaces.IdentificationService;
import musicrecognition.services.interfaces.TrackService;
import musicrecognition.util.audio.audiotypes.AudioType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping(value = "/rest/upload")
public class JsonFileUploadController {
    private static final Logger LOGGER = LogManager.getLogger(JsonFileUploadController.class);
    
    @Autowired
    IdentificationService identificationService;
    
    @Autowired
    TrackService trackService;
    
    @Autowired
    FingerprintService fingerprintService;
    
    
    @RequestMapping(method = RequestMethod.POST, params = "identify", consumes = {"multipart/form-data"})
    public ResponseEntity<List<TrackMatch>> identify(@RequestParam MultipartFile file) {
        if (file != null) {
            AudioType.Type type = getAudioType(file);
    
            if (type == null) {
                LOGGER.error("unacceptable file format: " + file.getContentType());
                return new ResponseEntity<>(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
            }
    
    
            File tempFile;
            
            try {
                tempFile = multipartToFile(file, type);
            } catch (IOException e) {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
            
            
            List<TrackMatch> matches = identificationService.identify(tempFile, type);
            
            if (!tempFile.delete())
                tempFile.deleteOnExit();
            
            return new ResponseEntity<>(matches, HttpStatus.OK);
        } else
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @RequestMapping(method = RequestMethod.POST, params = "add", consumes = {"multipart/form-data"})
    public ResponseEntity add(@RequestPart(value = "track") Track track,
                              @RequestPart(value = "file") MultipartFile file) {
        if (track == null || file == null ||
                track.getTitle().isEmpty() || track.getArtist().isEmpty() ||
                track.getYear() == 0 || track.getAlbumTitle().isEmpty())
            return new ResponseEntity(HttpStatus.NO_CONTENT);
            
        
        AudioType.Type type = getAudioType(file);
    
        if (type == null) {
            LOGGER.error("unacceptable file format: " + file.getContentType());
            return new ResponseEntity(HttpStatus.UNSUPPORTED_MEDIA_TYPE);
        }
    
    
        File tempFile;
        
        try {
            tempFile = multipartToFile(file, type);
        } catch (IOException e) {
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        
        Set<Integer> fingerprints = fingerprintService.createFingerprints(tempFile, type);
        
        if (!tempFile.delete())
            tempFile.deleteOnExit();
        
        if (fingerprints != null && !fingerprints.isEmpty()) {
            track.setFingerprints(fingerprints);
            Integer id = trackService.insert(track);

            if (id != null)
                return new ResponseEntity(HttpStatus.CREATED);
            else
                return new ResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY);
        }
    
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    private AudioType.Type getAudioType(MultipartFile file) {
        String contentType = file.getContentType();
        String fileName = file.getOriginalFilename().toLowerCase();
        
        if (contentType.equals("audio/mp3") || fileName.endsWith(".mp3"))
            return AudioType.Type.MP3;
        else if (contentType.equals("audio/wav") || fileName.endsWith(".wav"))
            return AudioType.Type.WAV;
        else
            return null;
    }
    
    private File multipartToFile(MultipartFile multipartFile, AudioType.Type type) throws IOException {
        File tempFile = File.createTempFile("tmp", "." + type.toString());
        
        InputStream inputStream = null;
        OutputStream outputStream = null;
        
        try {
            inputStream = new BufferedInputStream(multipartFile.getInputStream());
            outputStream = new FileOutputStream(tempFile);
            
            int read = 0;
            byte[] bytes = new byte[1024];
            
            while ((read = inputStream.read(bytes)) != -1)
                outputStream.write(bytes, 0, read);
        } catch (IOException e) {
            tempFile.delete();
        } finally {
            if (inputStream != null)
                inputStream.close();
            
            if (outputStream != null)
                outputStream.close();
        }
        
        return tempFile;
    }
}
