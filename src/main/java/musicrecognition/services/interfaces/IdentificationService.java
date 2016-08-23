package musicrecognition.services.interfaces;

import musicrecognition.dto.TrackMatch;
import musicrecognition.util.audio.audiotypes.AudioType;

import java.io.File;
import java.io.IOException;
import java.util.List;


public interface IdentificationService {
    List<TrackMatch> identify(File file, AudioType.Type type) throws IOException;
}
