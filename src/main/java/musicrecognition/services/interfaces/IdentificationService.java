package musicrecognition.services.interfaces;

import musicrecognition.dto.TrackMatch;
import musicrecognition.util.audio.audiotypes.AudioType;

import java.io.File;
import java.util.List;


public interface IdentificationService {
    List<TrackMatch> identify(File file, AudioType.Type type);
}
