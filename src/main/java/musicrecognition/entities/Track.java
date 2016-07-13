package musicrecognition.entities;

import java.util.List;
import java.util.Objects;
import java.util.Set;


public class Track {
    private Integer id;
    private String title;
    private String albumTitle;
    private String artist;
    private Integer year;
    private String genre;
    private Set<Integer> fingerprints;
    
    
    public Track() {}
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getAlbumTitle() {
        return albumTitle;
    }
    
    public void setAlbumTitle(String albumTitle) {
        this.albumTitle = albumTitle;
    }
    
    public String getArtist() {
        return artist;
    }
    
    public void setArtist(String artist) {
        this.artist = artist;
    }
    
    public Integer getYear() {
        return year;
    }
    
    public void setYear(Integer year) {
        this.year = year;
    }
    
    public String getGenre() {
        return genre;
    }
    
    public void setGenre(String genre) {
        this.genre = genre;
    }
    
    public Set<Integer> getFingerprints() {
        return fingerprints;
    }
    
    public void setFingerprints(Set<Integer> fingerprints) {
        this.fingerprints = fingerprints;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Track track = (Track) o;
        return Objects.equals(title, track.title) &&
                Objects.equals(albumTitle, track.albumTitle) &&
                Objects.equals(artist, track.artist) &&
                Objects.equals(year, track.year);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(title, albumTitle, artist, year);
    }
    
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Track{");
        sb.append("title='").append(title).append('\'');
        sb.append(", albumTitle='").append(albumTitle).append('\'');
        sb.append(", artist='").append(artist).append('\'');
        sb.append(", year=").append(year);
        sb.append(", genre='").append(genre).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
