package musicrecognition.dto;

import musicrecognition.entities.Track;

import java.io.Serializable;
import java.util.Objects;


public class TrackMatch {
    private Track track;
    private Integer matchCount;
    private Integer fingerprintCount;
    
    public Track getTrack() {
        return track;
    }
    
    public void setTrack(Track track) {
        this.track = track;
    }
    
    public Integer getMatchCount() {
        return matchCount;
    }
    
    public void setMatchCount(Integer matchCount) {
        this.matchCount = matchCount;
    }
    
    public Integer getFingerprintCount() {
        return fingerprintCount;
    }
    
    public void setFingerprintCount(Integer fingerprintCount) {
        this.fingerprintCount = fingerprintCount;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrackMatch that = (TrackMatch) o;
        return Objects.equals(track, that.track) &&
                Objects.equals(matchCount, that.matchCount) &&
                Objects.equals(fingerprintCount, that.fingerprintCount);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(track, matchCount, fingerprintCount);
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TrackMatch{");
        sb.append("track=").append(track);
        sb.append(", matchCount=").append(matchCount);
        sb.append(", fingerprintCount=").append(fingerprintCount);
        sb.append('}');
        return sb.toString();
    }
}
