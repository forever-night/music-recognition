package musicrecognition.services;

import musicrecognition.dto.TrackMatch;
import musicrecognition.entities.Track;
import musicrecognition.util.audio.audiotypes.AudioType;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;


public interface IdentificationService {
    List<TrackMatch> identify(File file);
    
    List<TrackMatch> identify(InputStream inputStream, AudioType.Type type) throws IOException;
}
