package musicrecognition.dto.mappers;

import musicrecognition.dao.interfaces.TrackDao;
import musicrecognition.dto.TrackMatchDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service
public class TrackMapper {
    @Autowired
    TrackDao trackDao;
    
    public TrackMatchDto toDto(Map<String, Integer> trackMatchMap) {
        TrackMatchDto trackMatchDto = new TrackMatchDto();
    
        trackMatchDto.setTrack(trackDao.getById(trackMatchMap.get("trackId")));
        trackMatchDto.setMatchCount(trackMatchMap.get("matchCount"));
    
        return trackMatchDto;
    }
}
